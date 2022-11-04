### 配置虚拟机

#### docker(Ubuntu)

1.卸载旧版本
sudo apt-get remove docker \
docker-engine \
docker.io

2.使用apt进行安装
1）添加使用HTTPS传输的软件包以及CA证书
$ sudo apt-get update
$ sudo apt-get install \
apt-transport-https \
ca-certificates \
curl \
gnupg \
lsb-release

2）添加软件源的GPG密钥（国内）
$ curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

3）向 sources.list 中添加 Docker 软件源
$ echo \
"deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://mirrors.aliyun.com/docker-ce/linux/ubuntu \
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

4）安装 Docker
$ sudo apt-get update
$ sudo apt-get install docker-ce docker-ce-cli containerd.io

3.自启动

systemctl enable docker



##### 4.镜像加速

```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://tnntgzif.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### docker安装Mysql

1.docker安装mysql

    docker pull mysql:指定版本

2.配置运行容器

docker run -d -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD="987654" mysql:8.0.28 --lower_case_table_names=1


2）进入容器

docker exec -it mysql bash

3）查看容器内mysql配置文件路径

mysql --help | grep my.cnf

4）复制配置文件到宿主机目录（退出docker后）

docker cp mysql:/etc/mysql/my.cnf /usr/local/docker/mysql/conf

5）重新运行容器

```
docker run --restart=always --privileged=true -d -v /usr/local/docker/mysql/data/:/var/lib/mysql -v /usr/local/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD="987654" mysql:8.0.28 --lower_case_table_names=1
```

3.配置远程访问
```
use mysql

UPDATE user SET `Host` = '%' WHERE `User` = 'root' LIMIT 1;

flush privileges;
```

### docker安装redis

1.docker安装redis
docker pull redis


2.将redis的配置文件等数据挂载到本地
1）挂载地址

example:

    mkdir -p /usr/local/docker/redis/conf
    mkdir -p /usr/local/docker/redis/data

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

### npm 设置淘宝镜像
`npm config set registry http://registry.npm.taobao.org`

### Product

1.整合MybatisPlus
```
1）、导入依赖，所有板块都是用，放在common中
    <dependency>
           <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-boot-starter</artifactId>
           <version>3.2.0</version>
    </dependency>
    
2）、配置
    1、配置数据源；
        驱动放在common中
        1）、导入数据库的驱动。https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-versions.html
        在各个模块中配置
        2）、在application.yml配置数据源相关信息
    2、配置MyBatis-Plus；
        1）、在启动类中使用@MapperScan扫描mapper接口
        2）、在配置文件中配置，告诉MyBatis-Plus，sql映射文件位置
```

### 注册中心，配置中心，网关

1、Nacos 做注册中心
```
<dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.2.6.RELEASE</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```
 
配置服务注册与发现

`@EnableDiscoveryClient` 

服务之间的远程调用：OpenFeign

```
1.依赖
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

2.编写远程调用接口
    声明接口中的方法为远程服务的某个方法

3.开启远程调用功能
    @EnableFeignClients(basePackages = "cn.xf.product.feign")
```

2、Nacos做配置中心
```
1.引入依赖
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>

2.创建.properties文件
    properties优先于yml文件
    
3.给配置中心添加数据集，应用名.properties
4.动态获取数据：
    @RefreshScope，动态刷新
    @Value("${}")，获取数据
5.当前配置文件与配置中心有相同配置，优先使用配置中心中的数据
```

```
命名空间：做配置隔离（可用来区分开发环境）
    默认为public（保留空间），默认新增的所有配置都在public空间
    1、开发，测试，生产，利用命名空间做环境隔离
        在bootstrap.properties：配置中，需要使用那个命名空间的配置为：`spring.cloud.nacos.config.namespace=nacos配置列表中每个环境的id`
    2、基于每个微服务之间相互隔离，每个微服务使用自己的命名空间，只加载自己的命名空间下的配置

配置集：
    一个配置文件中所有配置的集合
    
配置集ID：
    类似于配置文件名字
    在nacos中为配置的Data ID

配置分组：
    nacos中的GROUP；默认的配置集都属于DEFAULT_GROUP
    `指定使用分组配置文件：spring.cloud.nacos.config.group=`

```

3.网关 GateWay
```
1.开启服务的注册与发现
```

### 项目中网关配置

1.依赖common
2.@EnableDiscoveryClient


### 网关统一配置跨域