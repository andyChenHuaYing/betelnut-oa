#!/bin/bash

# Install software which system is required.
# This shell script just for Ubuntu Linux System.
# Fedora, CentOS or other "yum" Linux System, you should install software by yourself.
# In document folder, we provide Linux User-Guide.

# Step 1: Install JDK
mkdir ~/software/
mkdir ~/software/mysql
cp ./support/mysql/* ~/software/mysql
BETELNUT_PATH=`pwd`
echo "Please input your mysql password:"
read MYSQL_PASSWORD
if [ -z "$JAVA_HOME" ]; then
    cd ~/software
    echo "Begin install JDK to you computer..."
    SYSTEM_TYPE=`uname -m`
    if [ "$SYSTEM_TYPE" = "x86_64" ]; then
        echo "Begin install x64 JDK to your computer ..."
        wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u55-b13/jdk-7u55-linux-x64.tar.gz
        tar -xzf jdk-7u55-linux-x64.tar.gz
    else
        echo "Begin install x32 JDK to your computer ..."
        wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u55-b13/jdk-7u55-linux-i586.tar.gz
        tar -xzf jdk-7u55-linux-i586.tar.gz
    fi
    cd jdk1.7.0_55
    echo export JAVA_HOME=`pwd` >> ~/.bashrc
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
    echo 'export CLASS_PHAT=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar' >> ~/.bashrc
    # Change directory to application.
    cd $BETELNUT_PATH
fi

# Step 2: Install MySQL Database
MYSQL_INSTALL=`which mysql`
if [ -z "$MYSQL_INSTALL" ]; then
    echo "Begin install MySQL Database to your computer ..."
    # Install mysql 5.5 version.
    sudo apt-get install mysql-server-5.5 mysql-client-5.5
    sudo rm -f /etc/mysql/my.cnf
    # Copy the application mysql configuration file to local computer.
    sudo cp ~/software/mysql/my.cnf /etc/mysql/my.cnf
    sudo /etc/init.d/mysql restart
    # Check in the begin data to local MySQL Database.
    mysql -uroot -p$MYSQL_PASSWORD betelnut < ~/software/mysql/betelnut_data.sql
fi

# Step 3: Install Maven to local computer.
MVN_INSTALL=`which mvn`
if [ -z "$MVN_INSTALL" ]; then
    cd ~/software
    echo "Begin install Maven to your computer ..."
    wget http://apache.fayea.com/apache-mirror/maven/maven-3/3.2.1/binaries/apache-maven-3.2.1-bin.tar.gz
    tar -xzf apache-maven-3.2.1-bin.tar.gz
    cd ~/software/apache-maven-3.2.1/
    echo export MVN_HOME=`pwd` >> ~/.bashrc
    # Append Maven home configuration information after $PATH line.
    sed -i -e '/export PATH=/ /s/$/:$MVN_HOME\/bin/' ~/.bashrc
    cd $BETELNUT_PATH
fi

# Step 4: Install MongoDB to local computer.
MONGO_INSTALL=`which mongod`
if [ -z "$MONGO_INSTALL" ]; then
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
    echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
    echo "Update the Ubuntu apt-get source list, it will spend serval minutes, please wait, do not close the terminal."
    sudo apt-get update
    # Now install MongoDB 2.6.1 version.
    apt-get install mongodb-org=2.6.1 mongodb-org-server=2.6.1 mongodb-org-shell=2.6.1 mongodb-org-mongos=2.6.1 mongodb-org-tools=2.6.1
    sudo /etc/init.d/mongod start
fi