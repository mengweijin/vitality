DROP TABLE IF EXISTS SYS_ERROR_LOG;
CREATE TABLE SYS_ERROR_LOG (
  ID                    bigint NOT NULL COMMENT '主键ID',
  CLASS_NAME            varchar(255) DEFAULT NULL COMMENT '类名称',
  METHOD_NAME           varchar(255) DEFAULT NULL COMMENT '方法名称',
  EXCEPTION_NAME        varchar(255) DEFAULT NULL COMMENT '异常类型',
  ERROR_MSG             varchar(500) DEFAULT NULL COMMENT '异常信息',
  STACK_TRACE           text DEFAULT NULL COMMENT '异常堆栈信息',
  CREATE_BY             bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME           datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	        bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	        datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT='系统错误日志记录表';


DROP TABLE IF EXISTS SYS_FILE;
CREATE TABLE SYS_FILE (
  ID                    bigint NOT NULL COMMENT '主键ID',
  FILE_NAME             varchar(255) NOT NULL COMMENT '原始文件名称',
  FILE_PATH             varchar(255) NOT NULL COMMENT 'minio 文件存储全路径',
  DOMAIN_URL            varchar(135) NOT NULL COMMENT 'minio 主机 URL 地址',
  BUCKET                varchar(50) NOT NULL COMMENT 'minio bucket name',
  CREATE_BY             bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME           datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	        bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	        datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT='系统文件表';


DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER (
  ID                    bigint NOT NULL COMMENT '主键ID',
  LOGIN_NAME            varchar(64) NOT NULL COMMENT '用户登录名',
  PASSWORD              varchar(64) NOT NULL COMMENT '登录密码',
  NICK_NAME             varchar(64) NOT NULL COMMENT '用户昵称',
  GENDER                varchar(6) DEFAULT NULL COMMENT '性别 { MALE: 男；FEMALE：女；}',
  EMAIL                 varchar(128) DEFAULT NULL COMMENT '电子邮箱',
  MOBILE_PHONE          varchar(15) DEFAULT NULL COMMENT '移动电话',
  STATUS                int(4) DEFAULT 0 NOT NULL COMMENT '状态{ 0：正常；1：停用；}',
  DELETED               int(4) DEFAULT 0 NOT NULL COMMENT '逻辑删除',
  CREATE_BY             bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME           datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	        bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	        datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY SYS_USER_UNIQUE_INDEX_LOGIN_NAME (LOGIN_NAME) USING BTREE
) COMMENT='用户表';
insert into SYS_USER values(1, 'admin', '1qaz2wsx', '管理员', 'MALE', 'mengweijin.work@foxmail.com', NULL, 0, 0, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());




