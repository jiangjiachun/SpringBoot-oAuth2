# SpringBoot-oAuth2

## Spring oAuth2例子

- Authorization-Server 认证服务
- Resource-Server 资源服务
- client 客户端后台，前端例子：[oauth2-client](https://github.com/jiangjiachun/oauth2-client)

## 运行说明

1. 修改hosts文件增加：127.0.0.1 www.test.com
2. 新建mysql数据库，Authorization-Server/src/main/resources/application.properties 根据实际情况修改数据库连接、用户名、密码等。Authorization-Server启动时会自动初始化数据，登录用户名默认：admin/123456

