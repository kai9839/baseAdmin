-- 创建数据库
CREATE DATABASE baseAdmin WITH ENCODING 'UTF8';

-- 切换到baseAdmin数据库
\c baseAdmin;

-- 设置时区
SET TIMEZONE='Asia/Shanghai'; 

-- 用户表
CREATE TABLE sys_user (
    user_id         BIGSERIAL PRIMARY KEY,
    username        VARCHAR(30) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    nick_name       VARCHAR(30) NOT NULL,
    email          VARCHAR(50),
    phone          VARCHAR(11),
    sex            CHAR(1) DEFAULT '0',
    avatar         VARCHAR(100),
    status         CHAR(1) DEFAULT '0',
    del_flag       CHAR(1) DEFAULT '0',
    login_ip       VARCHAR(128),
    login_date     TIMESTAMP,
    create_by      VARCHAR(64),
    create_time    TIMESTAMP,
    update_by      VARCHAR(64),
    update_time    TIMESTAMP,
    remark         VARCHAR(500)
);

-- 角色表
CREATE TABLE sys_role (
    role_id         BIGSERIAL PRIMARY KEY,
    role_name       VARCHAR(30) NOT NULL,
    role_key        VARCHAR(100) NOT NULL,
    role_sort       INT,
    status          CHAR(1) DEFAULT '0',
    del_flag        CHAR(1) DEFAULT '0',
    create_by       VARCHAR(64),
    create_time     TIMESTAMP,
    update_by       VARCHAR(64),
    update_time     TIMESTAMP,
    remark          VARCHAR(500)
);

-- 用户和角色关联表
CREATE TABLE sys_user_role (
    user_id    BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- 操作日志表
CREATE TABLE sys_oper_log (
    oper_id         BIGSERIAL PRIMARY KEY,
    title           VARCHAR(50),
    action          VARCHAR(100),
    method          VARCHAR(100),
    request_method  VARCHAR(10),
    oper_name       VARCHAR(50),
    oper_url        VARCHAR(255),
    oper_ip         VARCHAR(128),
    oper_param      TEXT,
    json_result     TEXT,
    status          INTEGER,
    error_msg       TEXT,
    oper_time       TIMESTAMP
);

-- 初始化管理员账号
INSERT INTO sys_user (username, password, nick_name, status, create_time, create_by, remark)
VALUES ('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', '0', NOW(), 'admin', '管理员');

-- 初始化角色
INSERT INTO sys_role (role_name, role_key, role_sort, status, create_time, create_by, remark)
VALUES ('管理员', 'admin', 1, '0', NOW(), 'admin', '管理员');

-- 初始化用户和角色关联关系
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1); 