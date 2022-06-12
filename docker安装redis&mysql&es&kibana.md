1.docker安装redis
    docker pull redis


2.将redis的配置文件等数据挂载到本地
1）挂载地址
example:
    mkdir -p docker/redis/conf
    mkdir -p docker/redis/data

2）redis配置文件
    cd docker/redis/conf
    touch redis.conf
    vim redis.conf

    #bind 127.0.0.1      将bind 127.0.0.1注释掉，保证可以从远程访问到该Redis，不单单是从本地
    protected-mode no
    appendonly yes       appendonly：开启数据持久化到磁盘，由于开启了磁盘映射，数据最终将落到/usr/local/docker/redis/data目录下
    requirepass 123456   设置访问密码为123456

3.创建redis容器并启动
docker run --name myredis -p 6379:6379 -v /usr/local/docker/redis/data:/data -v /usr/local/docker/redis/conf/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf

    docker run表示运行的意思
    --name myredis表示运行容器的名字叫myredis
    -p 6379:6379表示将服务器的6379（冒号前的6379）端口映射到docker的6379（冒号后的6379）端口，这样就可以通过服务器的端口访问到docker容器的端口了
    -d 表示以后台服务的形式运行redis
    -v /Users/louxiujun/docker/redis/data:/data表示将服务器上的/Users/louxiujun/docker/redis/data映射为docker容器上的/data ，这样/data中产生的数据就可以持久化到本地的目录下了
    -v /Users/louxiujun/docker/redis/conf/redis.conf:/etc/redis/redis.conf表示将本地/Users/louxiujun/docker/redis/conf/redis.conf映射为docker容器上的/etc/redis/redis.conf，这样再配合指令末尾的redis redis-server /etc/redis/redis.conf实现让docker容器运行时使用本地配置的Redis配置文件的功能了。
    redis redis-server /etc/redis/redis.conf表示运行redis服务器程序，并且指定运行时的配置文件


# Mysql
1.docker安装mysql
    docker pull mysql:指定版本

2.配置运行容器
docker run -d -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD="987654" mysql:8.0.28

2）进入容器
docker exec -it mysql bash

3）查看容器内mysql配置文件路径
mysql --help | grep my.cnf

4）复制配置文件到宿主机目录（退出docker后）
docker cp mysql:/etc/mysql/my.cnf /usr/local/docker/mysql/conf

5）重新运行容器
docker run --restart=always --privileged=true -d -v /user/local/docker/mysql/data/:/var/lib/mysql -v /usr/local/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD="987654" mysql:8.0.28

3.配置远程访问
use mysql

UPDATE user SET `Host` = '%' WHERE `User` = 'root' LIMIT 1;

flush privileges;


# Elasticsearch
1.docker安装elasticsearch
    docker pull elasticsearch:7.4.2

2.创建挂载目录
    mkdir -p /usr/local/docker/elasticsearch/data
    mkdir -p /usr/local/docker/elasticsearch/conf

3.添加配置文件，让所有ip都能访问
    echo "http.host:0.0.0.0">>/usr/local/docker/elasticsearch/config/elasticsearch.yml

4.运行容器
    docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
        -e "discovery.type=single-node" \
        -e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
        -v /usr/local/docker/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
        -v /usr/local/docker/elasticsearch/data:/usr/share/elasticsearch/data \
        -v /usr/local/docker/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
        -d elasticsearch:7.4.2

5.运行报错-docker logs elasticsearch
    "Caused by: java.nio.file.AccessDeniedException: /usr/share/elasticsearch/data/nodes",

    解决：chmod -R 777 /usr/local/docker/elasticsearch

# Kibana-（elasticsearch可视化工具）
     docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.56.1:9200 -p 5601:5601 -d kibana:7.4.2
     版本需要与elasticsearch对应




