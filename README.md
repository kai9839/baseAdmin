# OnlyOffice 集成项目

## 项目简介
这是一个基于Spring Boot的OnlyOffice集成项目，实现了在线文档的查看、编辑、保存等功能。

## 系统要求
- JDK 1.8+
- Maven 3.6+
- OnlyOffice Document Server
- PostgreSQL 数据库

## 项目结构 

```
admin-system/
├── common/
│ ├── common-core/ # 核心模块
│ ├── common-model/ # 数据模型
│ └── common-security/# 安全模块
└── system/ # 系统模块
```

## 主要配置

### OnlyOffice服务器配置
1. **Docker运行命令**

```
bash
docker run -i -t -d -p 9999:80 \
--restart=always \
-e JWT_SECRET=my_jwt_secret \
onlyoffice/documentserver
```

2. **配置文件位置**
- 主配置：`system/src/main/resources/application.yml`
- 核心配置：`common/common-core/src/main/resources/application.yml`

3. **配置内容**

```
yaml
spring:
    datasource:
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://localhost:5432/baseAdmin
        username: postgres
        password: 123456
```

### 跨域配置
位置：`common/common-core/src/main/java/com/example/common/core/config/WebConfig.java`

```
java
.allowedOrigins("http://192.168.28.87:9999")
```

## 访问路径

### 前端页面
1. 文档管理页面：`http://localhost:8080/`
2. 文档编辑页面：`http://localhost:8080/editor.html?fileName={fileName}&fileType={fileType}&key={key}`

### API接口
1. 文件上传：`POST /onlyoffice/upload`
2. 文件列表：`GET /onlyoffice/list`
3. 文件下载：`GET /onlyoffice/download/{key}`
4. 文件删除：`DELETE /onlyoffice/delete/{key}`
5. 获取编辑配置：`GET /onlyoffice/config`
6. 保存回调：`POST /onlyoffice/callback`

## 已实现功能
1. 文件上传（支持拖拽上传）
2. 文件列表显示
3. 文件在线预览
4. 文件在线编辑
5. 文件下载
6. 文件删除
7. JWT token验证
8. 跨域支持

## 文件类型支持
- 文档：doc, docx, odt, rtf, txt
- 表格：xls, xlsx, ods
- 演示：ppt, pptx, odp

## 目录说明

```
admin-system/
├── common/common-core/src/main/
│ ├── java/com/example/common/core/
│ │ ├── config/
│ │ │ ├── OnlyOfficeConfig.java # OnlyOffice配置类
│ │ │ └── WebConfig.java # Web配置（跨域等）
│ │ ├── controller/
│ │ │ └── OnlyOfficeController.java # 主要控制器
│ │ └── domain/
│ │ └── dto/
│ │ └── FileModel.java # 文件模型
│ └── resources/
│ └── application.yml # 配置文件
└── system/src/main/
└── resources/
├── static/
│ ├── index.html # 文件管理页面
│ └── editor.html # 编辑器页面
└── application.yml # 系统配置
```

## 注意事项
1. 确保文件存储目录有正确的读写权限
2. 确保OnlyOffice服务器可以访问到您的回调地址
3. 配置正确的IP地址（不要使用localhost或者127.0.0.1）
   不然会出现提示：这份文件无法保存。请检查连接设置或联系你的管理员。当你点击ok按钮，系统将提示你下载文档。
4. JWT密钥需要与OnlyOffice服务器配置一致(不然会出现提示：文档安全令牌格式不正确)
