1.卸载旧版本

```
 sudo yum remove docker \
	docker-clident \
    docker-client-latest \
    docker-common \
    docker-latest \
    docker-latest-logrotate \
    docker-logrotate \
    docker-engine
```

2.安装docker使用的相应插件

```
sudo yum install -y yum-utils \
	device-mapper-persistent-date \
	lvm2
```

3.安装docker镜像

```
sudo yum-config-manager \
	--add-repo \
	https://download.docker.com/linux/centos/docker-ce.repo
```

4.启动docker

```
sudo systemctl start docker
```

5.检查docker版本

```
docker -v
```

6.检查docker中的镜像

```
sudo docker images
```

7.docker开机自启

```
sudo systemctl enable docker
```

8.docker镜像加速

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

9.安装mysql

```
sudo docker pull mysql:版本
```

10.创建实例（需要root权限 "su root  vagrant"）

```
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=987654 \
-v /usr/local/mysql/data:/var/lib/mysql:rw \
-v /usr/local/mysql/mysql-files:/var/lib/mysql-files:rw \
-v /usr/local/mysql/log:/var/log/mysql:rw \
-v /usr/local/mysql/config:/etc/mysql:rw \
-v /etc/localtime:/etc/localtime:ro --restart=always \
-d mysql:8.0.28

-p 3306:3306：将容器的3306端口映射搭配主机的3306端口
-v /mydata/mysql/log:/var/log/mysql：将配置文件挂载到主机
-v /mydata/mysql/data:/var/lib/mysql：将日志文件挂载到主机
-v /mydata/mysql/mysql-files：当指定了外部配置文件与外部存储路径时，也需要指定外部目录
-v /mydata/mysql/conf:/etc/mysql：将配置文件挂载到主机
-e MYSQL_ROOT_PASSWORD=987654：初始化root用户密码
```

11.docker运行中的容器

```
docker ps -a
```

12.进入mysql容器

```
docker exec -it mysql bash
```

13.登录mysql查看用户信息

```
mysql -uroot -p密码

select host,user,plugin,authentication_string from mysql.user;
host为 % 表示不限制ip   localhost表示本机使用    plugin如果不是mysql_native_password 则需要修改
ALTER user 'root'@'%' IDENTIFIED WITH mysql_native_password BY '密码';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'

FLUSH PRIVILEGES;  
```





docker安装redis

1.下载镜像

```
docker pull redis
```

2.创建redis管理目录

```
mkdir /data/redis
mkdir /data/redis/data
```

3.启动redis

```
//官方
docker run --name 名字 -d redis

//完整
docker run -p 6379:6379 --name redis \
-v /usr/local/redis/data/redis.conf:/etc/redis/redis.conf  \
-v /usr/local/redis/data:/data \
-d redis redis-server /etc/redis/redis.conf --appendonly yes

-p 6379:6379 #把容器内的6379端口映射到宿主机6379端口
-v /data/redis/redis.conf:/etc/redis/redis.conf#把宿主机配置好的redis.conf放到容器内的这个位置中
-v /data/redis/data:/data#把redis持久化的数据在宿主机内显示，做数据备份
redis-server /etc/redis/redis.conf#这个是关键配置，让redis不是无配置启动，而是按照这个redis.conf的配置启动
–appendonly yes #redis启动后数据持久化
```

4.在/usr/local/redis/data/redis.conf中添加修改配置文件（http://download.redis.io/redis-stable/redis.conf）

```
bind 127.0.0.1 #注释掉这部分，这是限制redis只能本地访问
protected-mode no #默认yes，开启保护模式，限制为本地访问
daemonize no#默认no，改为yes意为以守护进程方式启动，可后台运行，除非kill进程（可选），改为yes会使配置文件方式启动redis失败
dir  ./ #输入本地redis数据库存放文件夹（可选）
appendonly yes #redis持久化（可选）
设置密码
```

5.进入redis容器

```
docker exec -it redis /bin/bash
```

