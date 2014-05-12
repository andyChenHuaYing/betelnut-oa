betelnut-oa
===========

#中文说明:
##1. 准备环境
- 安装配置JDK 1.6 + 环境。[*下载地址*](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- 安装配置Maven 3.0+ 环境。[*下载地址*](http://maven.apache.org/download.cgi)
- 安装MySQL数据库,以Ubuntu&Debian为例:`sudo apt-get install mysql-server mysql-client`。
- 安装Memcached,以Ubuntu&Debian为例:`sudo apt-get install memcached`。
- 安装Mongodb,以Ubuntu&Debian为例:[*官方Manual*](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

##2. install jar包至本地repository
1. clone repository: `git clone https://github.com/JameChou/betelnut-oa.git`
2. 进入根目录Linux: `sh install.sh`, Windows: `install.bat`<br />
   或者手动install modules: 进入modules目录 `mvn clean install`

#English Specification
##1. Pre-Requirement
- Install JDK 1.6+.[*Download*](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- Install Maven 3.0+.[*Download*](http://maven.apache.org/download.cgi)
- Install MySQL Database, etc. Ubuntu&Debian: `sudo apt-get install mysql-server mysql-client`.
- Install Memcached, etc. Ubuntu&Debian: `sudo apt-get install memcached`.
- Install Mongodb, etc. Ubuntu&Debian:[*Official Manual*](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

##2. Install Betelnut Modules to local repository
1. Clone repository: `git clone https://github.com/JameChou/betelnut-oa.git`
2. Change directory to betelnut-oa
    `cd ~/betelnut-oa`
    `sh install.sh`
<br/>or you can do it by yourself
    `cd ~/betelnut-oa/modules`
    `mvn clean install`