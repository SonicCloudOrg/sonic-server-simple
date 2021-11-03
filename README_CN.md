<p align="center">
  <img src="https://raw.githubusercontent.com/ZhouYixun/sonic-server/main/logo.png">
</p>
<p align="center">🎉Sonic云真机测试平台</p>
<p align="center">
  <a href="https://github.com/ZhouYixun/sonic-server/blob/main/README.md">  
    English
  </a>
  <span>| 简体中文</span>
</p>
<p align="center">
  <a href="#">  
    <img src="https://img.shields.io/badge/release-v1.1.0-orange">
  </a>
  <a href="https://hub.docker.com/repository/docker/zhouyixun/sonic-server-simple">  
    <img src="https://img.shields.io/docker/pulls/zhouyixun/sonic-server-simple">
  </a>
  <a href="https://github.com/ZhouYixun/sonic-server/blob/main/LICENSE">  
    <img src="https://img.shields.io/github/license/ZhouYiXun/sonic-server?color=green&label=license&logo=license&logoColor=green">
  </a>
</p>

### 官方网站
 [Sonic Official Website](http://zhouyixun.gitee.io/sonic-official-website)
## 背景

#### 什么是Sonic？

> 如今，自动化测试、远程控制等技术已经逐渐成熟。其中 [Appium](https://github.com/appium/appium) 在自动化领域可以说是领头者，[STF](https://github.com/openstf/stf) 则是远程控制的始祖。很久前就开始有了一个想法，是否可以在一个平台上，提供解决所有客户端（Android、iOS、Windows、Mac、Web应用）的测试方案，于是，Sonic云真机测试平台由此诞生。

#### 愿景

> Sonic当前的愿景是能帮助中小型企业解决在客户端自动化或远控方面缺少工具和测试手段的问题。
>
>  如果你想参与其中，欢迎加入！💪
>
> 如果你想支持，可以给我一个star。⭐

#### Sonic能做什么？

+ 0编码实现自动化测试
+ 充分利用您的设备（24小时）
+ 远程控制您的设备（安卓、iOS甚至鸿蒙）
+ 在设备执行UI自动化、稳定性和遍历测试
+ 连接CI/CD平台（例如Jenkins）
+ 可视化报表
+ 更多...

## 打包方式

```
mvn package 
```

## 部署方式

```
docker-compose up -d
```
|  ENV Name   | Description  |
|  ----  | ----  |
| RABBIT_HOST  | RabbitMQ service host,default **localhost** |
| RABBIT_PORT  | RabbitMQ service port,default **5672** |
| RABBIT_USERNAME  | RabbitMQ service username,default **sonic** |
| RABBIT_PASSWORD  | RabbitMQ service password,default **sonic** |
| RABBIT_VHOST  | RabbitMQ service virtual-host,default **sonic** |
| REDIS_DATABASE  | redis database,default **0** |
| REDIS_HOST  | redis host,default **localhost** |
| REDIS_PORT  | redis port,default **6379** |
| MYSQL_HOST  | mysql host,default **localhost** |
| MYSQL_DATABASE  | mysql database,default **sonic** |
| MYSQL_USERNAME  | mysql username,default **root** |
| MYSQL_PASSWORD  | mysql password,default **Sonic!@#123** |
## 开源许可协议

[MIT License](LICENSE)