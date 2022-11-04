package cn.xf.product;

import cn.xf.product.dao.AttrGroupDao;
import cn.xf.product.dao.SkuSaleAttrValueDao;
import cn.xf.product.entity.BrandEntity;
import cn.xf.product.service.BrandService;
import cn.xf.product.vo.SkuItemSaleAttrVo;
import cn.xf.product.vo.SpuItemAttrGroupVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    private AttrGroupDao attrGroupDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    void queryAttributeListByProduct(){
        List<SkuItemSaleAttrVo> attrVos = skuSaleAttrValueDao.queryAttrInfoByProductId(13L);
        System.out.println(attrVos);
    }

    @Test
    public void test1() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getProductSpecificationsByProductId(13L, 225L);
        System.out.println("heiheihei"+attrGroupWithAttrsBySpuId);
    }

    @Test
    public void testRedisson() {
        System.out.println(redissonClient);
    }

    @Test
    void test(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        //保存
        ops.set("hello","world_" + UUID.randomUUID().toString());

        //查询
        String hello = ops.get("hello");
        System.out.println("之前保存的数据:"+hello);
    }

    @Test
    void fileUpload() throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tB5UAcGJtvCVwSGdNbD";
        String accessKeySecret = "wEjGOiGRWRGsi8uzydRBTjESDYXkqm";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "mallprotest";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "2020041119404981.png";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath = "E:\\File\\Study\\Note\\picture\\jvm\\2020041119404981.png";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream(filePath);
        // 创建PutObject请求。
        ossClient.putObject(bucketName, objectName, inputStream);

        ossClient.shutdown();
        System.out.println("上传完成");

    }

    @Test
    void contextLoads() {
        BrandEntity brand = new BrandEntity();
        brand.setDescript("你叉叉");
        brandService.saveOrUpdate(brand);
        System.out.println("success");
    }

}
