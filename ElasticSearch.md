### _cat

查看es的健康状态：`_cat/health`

查看es主节点信息：`_cat/master`

查看es所有索引：`_cat/indices`

### put请求

保存数据：保存在那个索引的什么类型下，指定用那个唯一标识

如果发送多次则为保存操作

如：/customer/external/1
```
{
  "_index": "customer", // 索引
  "_type": "external", // 类型
  "_id": "1", // 唯一标识
  "_version": 1, // 版本号，
  "result": "created",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 0,
  "_primary_term": 1 // 分片
}
```

**乐观锁**

在更新请求末尾添加`?if_seq_no=指定版本&if_primary_term=指定版本`


### post请求

post在发送请求时可以不指定唯一标识（id），会自动生成一个唯一id
```
{
  "_index": "customer",
  "_type": "external",
  "_id": "8noc0IIBdkJ0_MtRI7zl",
  "_version": 1,
  "result": "created",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 4,
  "_primary_term": 1
}
```

### _update操作

使用post或put时在末尾添加_update

如果是使用post更新数据，当数据一致时，所有信息不会改变

### delete请求

可删除索引，数据；不能删除类型

### 批量导入数据 _bulk

请求必须为post

如：post /bank/account/_bulk

### 查询

get bank/_search?q=*&sort=account_number:asc

查询银行下的所有并以account_number进行升序

get bank/_search
{
    "query":{
        "match_all":{} // 匹配所有
    }
    "sort":{ // 查询条件
        
    }
}

### 聚合

```
使用聚合查询年龄分布，平均年龄，平均薪资
GET bank/_search
{
  "query": {
    "match": {
      "address": "mill"
    }
  },
  "aggs": {
    "aggType": {
      "terms": {
        "field": "age",
        "size": 10
      }
    },
    "aggAVG":{
      "avg": {
        "field": "age"
      }
    },
    "balanceAvg":{
      "avg": {
        "field": "balance"
      }
    }
  }
}
```

### 映射

创建新的索引，并将数据进行迁移

```
PUT /newbank
{
  "mappings": {
    "properties": {
       "account_number" : {
          "type" : "long"
        },
        "address" : {
          "type" : "text"
        },
        "age" : {
          "type" : "integer"
        },
        "balance" : {
          "type" : "long"
        },
        "city" : {
          "type" : "keyword"
        },
        "email" : {
          "type" : "keyword"
        },
        "employer" : {
          "type" : "keyword"
        },
        "firstname" : {
          "type" : "text"
        },
        "gender" : {
          "type" : "keyword"
        },
        "lastname" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "state" : {
          "type" : "text"
        }
    }
  }
}
```

数据迁移

```
POST _reindex
{
  "source": {
    "index": "bank",
    "type":"account"
  }
  , "dest": {
    "index": "newbank"
  }
}
```