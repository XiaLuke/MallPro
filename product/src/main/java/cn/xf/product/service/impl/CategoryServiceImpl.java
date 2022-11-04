package cn.xf.product.service.impl;

import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;
import cn.xf.product.dao.CategoryDao;
import cn.xf.product.entity.CategoryEntity;
import cn.xf.product.service.CategoryBrandRelationService;
import cn.xf.product.service.CategoryService;
import cn.xf.product.vo.Catelog2Vo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有分类并组装成为树形结构
     *
     * @return
     */
    @Override
    public List<CategoryEntity> treeList() {
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        // 一级分类
        List<CategoryEntity> parent = categoryEntityList.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map(item -> {
            item.setChildrenList(getChildren(item, categoryEntityList));
            return item;
        }).sorted((item, itemNext) -> {
            return (item.getSort() == null ? 0 : item.getSort()) - (itemNext.getSort() == null ? 0 : itemNext.getSort());
        }).collect(Collectors.toList());
        return parent;
    }

    @Override
    public void removeItemByIds(List<Long> idList) {
        //TODO 检查当前删除数据是否在其他地方使用
        // 逻辑删除
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * 查询主要分类
     *
     * @return {@link List}<{@link CategoryEntity}>
     */
    @Cacheable(value = {"category"},key = "#root.method.name",sync = true)
    @Override
    public List<CategoryEntity> queryLevelOneClassification() {
        System.out.println("queryLevelOneClassification start........");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        System.out.println("消耗时间：" + (System.currentTimeMillis() - l));
        return categoryEntities;
    }


    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        // 获取缓存中的数据
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            // 如果缓存中为空，查询数据库，这里需要加锁，若不加锁，多数据进行造成击穿
            // 本地锁
//            Map<String, List<Catelog2Vo>> jsonFromDb = getCatalogJsonFromDb();
            // 分布式锁
            Map<String, List<Catelog2Vo>> jsonFromDb = getCatalogJsonFromDbWithRedisLockFour();
            // 将查询出来的数据再放入缓存中,,,如果在这里写入缓存，在此过程中可能会出现其他线程重新执行以上操作
//            String jsonString = JSON.toJSONString(jsonFromDb);
//            redisTemplate.opsForValue().set("catalogJson",jsonString);
            return jsonFromDb;
        }
        // 将查出的数据转为指定对象
        Map<String, List<Catelog2Vo>> listMap = JSON.parseObject(
                catalogJson,
                new TypeReference<Map<String, List<Catelog2Vo>>>() {
                });
        return listMap;
    }

    /**
     * 从数据库中获取二级三级菜单json数据
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDb() {
        // 这里只用同一把锁就能锁住，synchronized(this),springboot中所有组件都是单例的
        synchronized (this) {
            // 拿到锁后需要再去缓存中确定一次
            String catalogJson = redisTemplate.opsForValue().get("catalogJson");
            if (!StringUtil.isEmpty(catalogJson)) {
                //缓存不为空直接返回
                Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
                });
                return result;
            }
            System.out.println("查询数据库");
            //将数据库的多次查询变为一次
            List<CategoryEntity> selectList = this.baseMapper.selectList(null);

            //1、查出所有分类
            //1、1）查出所有一级分类
            List<CategoryEntity> levelOneCategoryList = getParentCategoryId(selectList, 0L);

            //封装数据
            Map<String, List<Catelog2Vo>> parentCid = levelOneCategoryList.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                //1、每一个的一级分类,查到这个一级分类的二级分类
                List<CategoryEntity> categoryEntities = getParentCategoryId(selectList, v.getCatId());

                //2、封装上面的结果
                List<Catelog2Vo> catelog2Vos = null;
                if (categoryEntities != null) {
                    catelog2Vos = categoryEntities.stream().map(l2 -> {
                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                        //1、找当前二级分类的三级分类封装成vo
                        List<CategoryEntity> level3Catelog = getParentCategoryId(selectList, l2.getCatId());

                        if (level3Catelog != null) {
                            List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                                //2、封装成指定格式
                                Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                                return category3Vo;
                            }).collect(Collectors.toList());
                            catelog2Vo.setCatalog3List(category3Vos);
                        }

                        return catelog2Vo;
                    }).collect(Collectors.toList());
                }
                return catelog2Vos;
            }));
            // 查询到数据就放入缓存中
            String jsonString = JSON.toJSONString(parentCid);
            redisTemplate.opsForValue().set("catalogJson", jsonString, 1, TimeUnit.HOURS);
            return parentCid;
        }
    }

    /*-------------------------------------使用Redisson实现分布式锁 START-------------------------------------*/

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        //1、占分布式锁。去redis占坑
        //（锁的粒度，越细越快:具体缓存的是某个数据，11号商品） product-11-lock
        //RLock catalogJsonLock = redissonClient.getLock("catalogJson-lock");
        //创建读锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("catalogJson-lock");
        RLock rLock = readWriteLock.readLock();
        Map<String, List<Catelog2Vo>> dataFromDb = null;
        try {
            rLock.lock();
            //加锁成功...执行业务
            dataFromDb = getDataFromDb();
        } finally {
            rLock.unlock();
        }
        return dataFromDb;

    }

    /*--------------------------------------使用Redisson实现分布式锁 END--------------------------------------*/



    /*-----------------------------------------手动实现分布式锁 START-----------------------------------------*/

    /**
     * 分布式锁基本原理
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLockBase() {
        // 1、占分布式锁
        Boolean status = redisTemplate.opsForValue().setIfAbsent("lock", "lock");
        if (status) {
            // 加锁成功，执行业务
            Map<String, List<Catelog2Vo>> resultMap = getDataFromDb();
            // 业务执行成功后，删除锁
            redisTemplate.delete("lock");
            return resultMap;
        } else {
            // 加锁失败....重试
            return getDataFromDb();
        }
    }

    /**
     * 分布式锁问题一
     * 在执行业务的过程中，出现程序宕机，造成后续删除的死锁
     * <p>
     * 解决：设置过期时间
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLockOne() {
        // 1、占分布式锁
        Boolean status = redisTemplate.opsForValue().setIfAbsent("lock", "lock");
        if (status) {
            // 3.给redis设置过期时间
            redisTemplate.expire("lock", 20, TimeUnit.SECONDS);
            // 1.加锁成功，执行业务，如果此时宕机，造成死锁
            Map<String, List<Catelog2Vo>> resultMap = getDataFromDb();
            // 2.业务执行成功后，删除锁
            redisTemplate.delete("lock");
            return resultMap;
        } else {
            // 加锁失败....重试
            return getDataFromDb();
        }
    }

    /**
     * 分布式锁问题二
     * 占到了分布式锁，在设置过期时间的过程中宕机，死锁
     * <p>
     * 解决：将设置过期时间与占位保证为原子性（set lock lockName EX TimeSeconds NX）
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLockTwo() {
        // 1、占锁和设置过期时间在一起，
        Boolean status = redisTemplate.opsForValue().setIfAbsent("lock", "lock", 30, TimeUnit.SECONDS);
        if (status) {
            // 1.加锁成功，执行业务，如果此时宕机，造成死锁
            Map<String, List<Catelog2Vo>> resultMap = getDataFromDb();
            // 2.业务执行成功后，删除锁
            redisTemplate.delete("lock");
            return resultMap;
        } else {
            // 加锁失败....重试
            return getDataFromDb();
        }
    }

    /**
     * 分布式锁问题三
     * 当设置的锁过期时间 < 执行业务时间，会将其他进程的锁删除
     * <p>
     * 解决：在占锁时值设置为uuid，删除锁时拿到uuid与当前进程中的进行比较是否一致再删除
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLockThree() {
        // 设置uuid，匹配是自己的锁
        String uuid = UUID.randomUUID().toString();
        // 1、占锁和设置过期时间在一起，
        Boolean status = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        if (status) {
            // 1.加锁成功，执行业务，如果此时宕机，造成死锁
            Map<String, List<Catelog2Vo>> resultMap = getDataFromDb();
            // 2.业务执行成功后，先查询锁中的uuid是不是自己的
            if (uuid.equals(redisTemplate.opsForValue().get("lock"))) {
                redisTemplate.delete("lock");
            }
            return resultMap;
        } else {
            // 加锁失败....重试
            return getDataFromDb();
        }
    }

    /**
     * 分布式锁问题四---最终版本
     * 在判断redis中是否为自己的锁过程中，此时时间过期，其他线程占有了该进程，redis中存储的value将更新，
     * 而当上一个线程拿到锁删除时，不能保证就是自己的锁
     * <p>
     * 解决：删除锁时也保证原子操作（redis+lua脚本）
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLockFour() {
        // 设置uuid，匹配是自己的锁
        String uuid = UUID.randomUUID().toString();
        // 1、占锁和设置过期时间在一起，
        Boolean status = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        if (status) {
            Map<String, List<Catelog2Vo>> resultMap;
            try {
                // 1.加锁成功，执行业务
                resultMap = getDataFromDb();
            } finally {
                // 2.业务执行成功后，删除锁保证原子性(redis+lua脚本)
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            // 1.加锁成功，执行业务
            return resultMap;
        } else {
            // 加锁失败....重试
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return getDataFromDb();
        }
    }

    /*-----------------------------------------手动实现分布式锁 END-----------------------------------------*/

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtil.isEmpty(catalogJson)) {
            //缓存不为空直接返回
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return result;
        }
        System.out.println("查询数据库");
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //1、查出所有分类
        //1、1）查出所有一级分类
        List<CategoryEntity> levelOneCategoryList = getParentCategoryId(selectList, 0L);

        //封装数据
        Map<String, List<Catelog2Vo>> parentCid = levelOneCategoryList.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParentCategoryId(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catelog = getParentCategoryId(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        // 查询到数据就放入缓存中
        String jsonString = JSON.toJSONString(parentCid);
        redisTemplate.opsForValue().set("catalogJson", jsonString, 1, TimeUnit.HOURS);
        return parentCid;
    }

    /**
     * 得到父类类id
     *
     * @param selectList 选择列表
     * @param parentCid  父母cid
     * @return {@link List}<{@link CategoryEntity}>
     */
    private List<CategoryEntity> getParentCategoryId(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> categoryEntities = selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return categoryEntities;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //1、收集当前节点id
        paths.add(catelogId);
        // 当前分类
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    private List<CategoryEntity> getChildren(CategoryEntity category, List<CategoryEntity> categoryEntityList) {
        List<CategoryEntity> children = categoryEntityList.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(category.getCatId());
        }).map(categoryEntity -> {
            // 找子菜单
            categoryEntity.setChildrenList(getChildren(categoryEntity, categoryEntityList));
            return categoryEntity;
        }).sorted((item, itemNext) -> {
            // 排序
            return (item.getSort() == null ? 0 : item.getSort()) - (itemNext.getSort() == null ? 0 : itemNext.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}