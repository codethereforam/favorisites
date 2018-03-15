# favorisites

## 项目介绍

基于Spring + SpringMVC + Mybatis开发的登录、注册项目，该项目前后端分离，使用restful风格，可基于此项目快速开发简单的web系统

### 功能

- 注册
- 登录
    - 找回密码
    - 重置密码

### 业务逻辑

[项目业务逻辑](/project-docs/businessLogicAnalysis.txt)

### 组织结构

```
├── favorisites-common -- ssm框架公共模块
├── favorisites-dao -- 代码生成模块，无需开发
├── favorisites-service -- 服务的接口及实现
├── favorisites-web -- 网站前台
├── favorisites-admin -- 网站后台
├── project-datamodel -- 项目数据库相关文件
└── project-docs -- 项目文档
```

### 技术选型

#### 后端技术

- Spring Framework
- SpringMVC
- MyBatis
- MyBatis Generator
- Apache Shiro
- Velocity
- Swagger2
- FluentValidator
- com.github.penggle.kaptcha
- Log4J2
- Maven
- javax.mail

#### 前端技术

- Bootstrap
- AngularJS

### 模块依赖

```
favorisites-web & favorisites-admin -> favorisites-service -> favorisites-dao -> favorisites-common
```

### 编程规约

后台参照`阿里巴巴Java开发手册`

## 项目运行

1. 下载项目并构建： git clone xxx; mvn clean install

2. 新建数据库: project-datamodel/favorisites.sql

3. 修改数据库配置文件: favorisites-service/src/main/resources/jdbc-config.properties

4. 修改邮件配置文件： favorisites-common/src/main/resources/email.properties.template

5. mvn jetty run

6. 打开页面: http://localhost:9999/login.html & http://localhost:9999/swagger-ui.html

## 项目预览

### 数据模型

```sql
+-------------+---------------------+------+-----+---------+----------------+
| Field       | Type                | Null | Key | Default | Extra          |
+-------------+---------------------+------+-----+---------+----------------+
| user_id     | int(10) unsigned    | NO   | PRI | NULL    | auto_increment |
| username    | varchar(20)         | NO   |     | NULL    |                |
| password    | varchar(32)         | NO   |     | NULL    |                |
| salt        | varchar(32)         | YES  |     | NULL    |                |
| email       | varchar(50)         | NO   |     | NULL    |                |
| sex         | tinyint(3) unsigned | YES  |     | NULL    |                |
| locked      | tinyint(3) unsigned | YES  |     | NULL    |                |
| create_time | datetime            | YES  |     | NULL    |                |
+-------------+---------------------+------+-----+---------+----------------+
```

### 界面

![login.png](/project-docs/images/login.png)
![forget-password.png](/project-docs/images/forget-password.png)
![reset-password.png](/project-docs/images/reset-password.png)
![register.png](/project-docs/images/register.png)

### restful api

![restful.png](/project-docs/images/restful.png)

## 项目分支

### alone

[thinkam](https://github.com/codethereforam)在`master`分支基础上新建的分支，开发一个登录、注册的项目

### master

原计划开发一个收藏网址、管理书签的网站，后由于非技术原因中止

## 开发者

- [thinkam](https://github.com/codethereforam)： 后台
- [Sotyoyo](https://github.com/Sotyoyo)： 前端

## 许可证

[Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0)
