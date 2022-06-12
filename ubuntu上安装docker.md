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

