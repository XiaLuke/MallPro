底层是对lucene的封装

倒排索引

将所有请求都封装成了rest api

put请求：（索引名（'数据库名称'）/类型名('表名称')/几号数据）
    第一次发送数据为新增，之后发送相同数据时为更新操作，版本号为当前版本号+1
    如果不携带id发送数据，请求方式不允许

    更新：
    （索引名/类型名/几号数据）
    {
        "name":"张三",
        "age":18,
        "phone": "123456789"
    }

post请求：（索引/类型/几号数据）
    第一次发送请求为新增，之后发送相同数据时为更新操作，版本号为当前版本号+1
    （索引/类型/）
    每次发送请求都为新增操作，每次有一个唯一id

    更新：
    （索引名/类型名/几号数据/_update）
        {
            "doc": {
                "name": "张三",
                "age": 18,
            }
        }
        当跟新数据与愿数据一致时，所有数据都不会改变（result返回数据为"noop"）

get请求：（索引/类型/几号数据）
    查询指定id的数据
    {
        "_index": "索引名称",
        "_type": "类型名称",
        "_id": "数据id",
        "_version": "版本号",
        "_seq_no": "序列号", // 并发控制字段，跟新时+1，用来做乐观锁
        "_primary_term": "主键", // 做集群中使用，主分片发生变化，该字段变化
        "found": true,
        "_source": {      // 查询到的数据
            "name": "张三",
            "age": 18,
        }
    }

    ** 为了保证数据的安全行，在修改时需要先查询，然后携带序列号与主键再修改，这样可以保证数据的安全性（索引名/类型名/几号数据/?if_seq_no=xx&if_primary_term=xxx） **

delete请求：
    删除指定id数据不存在时：（索引名/类型名/几号数据）
    {
        "found": false,
        "_index": "索引名称",
        "_type": "类型名称",
        "_id": "数据id",
    }

    删除索引：（索引名）

post /_bulk  批量导入数据
https://raw.githubusercontent.com/elastic/elasticsearch/7.4/docs/src/test/resources/accounts.json


### ES的检索方式
```
1、通过使用REST request URI 发送搜索参数（url+检索参数）

2、通过使用REST request body发送（url+请求体）
```

**检索信息**
_search
方式1：`GET bank/_search?q=*&sort=account_number:asc`
方式2：`GET bank/_search{
"query":{
    "match_all":{}
},
"sort":[{
    "account_number":{
        "order":"desc"
    }
}]
}`

**Query DSL（查询对象领域语言）**
1、基本语法
```
GET /bank/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "balance": {
        "order": "desc"
      }
    }
  ],
  "from": 0,
  "size": 20
}
```

2、返回部分字段
`_source:["字段1","字段2"]`

3、match匹配查询
 match中匹配的非字符串字段，则为精确查询，否则为模糊查询
查询出来的结果按照平分进行排序
 ```java
 // 精确查询，非字符串字段
 get /bank/_search
 {
   "query":{
     "match": {
       "balance": 49989
     }
   }
 }

 get /bank/_search
 {
   "query":{
     "match": {
       "address": "Mill"
     }
   }
 }
 ```

 4、match_phrase短句查询
 ```java
 get /bank/_search
  {
    "query":{
      "match": {
        "address": "Mill Road"
      }
    }
  }
```

5、multi_match多字段匹配
```java
get /bank/_search
  {
    "query":{
      "multi_match": {
        "query":"查询的对象"，
        "fields":["查询的字段1","查询的字段2"]
      }
    }
  }
```

### 映射
指定字段的类型


### 为什么通过9200给nginx发送请求而不使用9300端口
```text
1、9300端口主要为tcp协议接口，使用不同springboot版本，transport.jar 的版本也不同，不能适配es版本；7.x已经不建议使用，8以后废弃
2、9200端口主要为http协议，有非官方的jestClient发送请求，但是速度慢。
```
**综上所述，使用官方的Elasticsearch-Rest-Client**，这是官方的RestClient，封装了ES操作，API层次分明，上手简单

### 为什么不使用官方的javaScript Client
es主要是用于后台的集群服务，并且端口不一定会暴露出来；js客户端对es的支持度较低