# 属性分组
属性分类为对每个商品设置不同的分组信息

## 属性分组与规格参数关联（product/attrgroup/{attrGroupId}/attr/relation）
一个分组关联多个规格参数

## 删除商品属性关联的规格参数（product/attrgroup/attr/relation/delete）
使用属性id与属性分组id精确查询到中间表中的一条数据

## 属性分组中关联关系新增属性分页信息（product/attrgroup/{attrGroupId}/noattr/relation）
1. 获取当前分组的分组id
2. 根据分组id拿到所有的有关分组信息
3. 获取当前分组下的所有规格参数
4. 获取当前分组下的还未关联的规格参数

# 规格参数（根据商品属性中的类型区分为规格参数还是销售属性 attr_type = 1）

规格参数是对每个属性分组设置详细信息，其中额外包含所属分类和所属分组

在查询规格参数详情的分组信息时，使用的是连表查询，在特殊情况下会导致数据量过大，
增加服务器负担，所以分页时在查询分页与分组信息

## 获取规格参数的详情信息（product/attr/info/{attrId}）
1. 查询属性分类，因为是级联菜单，所以需要查询完整路径
2. 查询所属的分组信息

## 更新时提交时需要同时修改额外参数（分类和分组信息）（product/attr/update）
1. 修改基本数据
2. 修改分组关联（根据属性id对应的分组id）

# 销售属性
根据商品属性中的类型区分为规格参数还是销售属性 

销售属性中不存在分组信息

attr_type = 0

## 获取销售属性列表（product/attr/{type}/list/{catelogId}）
当前获取列表的请求与获取规格参数公用一个方法，{type}用来区分是sale：base（销售属性：规格参数）

## 保存（product/attr/save）与更新（product/attr/update）
销售属性不新增中间表信息与修改中间表信息

## 循环依赖
在发布商品查询品牌分类时，注入service出现循环依赖的问题：service注入service出现

## 点击商品品牌时没有数据请求
npm install --save pubsub-js

在main.js中引入适用
import Pubsub from 'pubsub-js'
vue.prototype.PubSub = Pubsub




 
