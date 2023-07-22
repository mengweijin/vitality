-- 与 MySQL 区别
-- 1、h2 中，创建表最后面不能添加 COMMENT = '表注释'；
-- 2、int/bigint 类型不能限制位数，比如：int(4) 会报错，需要去掉；

DROP TABLE IF EXISTS VTL_NOTICE;
CREATE TABLE VTL_NOTICE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  AVATAR                        varchar(255) DEFAULT NULL COMMENT '图像链接url',
  TITLE                         varchar(255) DEFAULT NULL COMMENT '标题',
  DESCRIPTION                   varchar(20000) DEFAULT NULL COMMENT '内容',
  RELEASED                      int DEFAULT 0 COMMENT '是否已发布。{0=否, 1=是}',
  RECEIVED_BY                   bigint NOT NULL COMMENT '消息接收人',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS VTL_ANNOUNCEMENT;
CREATE TABLE VTL_ANNOUNCEMENT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  AVATAR                        varchar(255) DEFAULT NULL COMMENT '图像链接url',
  TITLE                         varchar(255) DEFAULT NULL COMMENT '标题',
  DESCRIPTION                   varchar(20000) DEFAULT NULL COMMENT '内容',
  RELEASED                      int DEFAULT 0 COMMENT '是否已发布。{0=否, 1=是}',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_CONFIG;
CREATE TABLE VTL_CONFIG (
  ID                            bigint NOT NULL COMMENT '主键ID',
  TITLE                         varchar(255) NOT NULL COMMENT '标题',
  CODE                          varchar(100) NOT NULL COMMENT '配置编码',
  VAL                           varchar(255) NOT NULL COMMENT '值',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_CONFIG_UNIQUE_INDEX_CODE ON VTL_CONFIG(CODE);

DROP TABLE IF EXISTS VTL_LOG_ERROR;
CREATE TABLE VTL_LOG_ERROR (
  ID                            bigint NOT NULL COMMENT '主键ID',
  CLASS_NAME                    varchar(255) DEFAULT NULL COMMENT '类名称',
  METHOD_NAME                   varchar(255) DEFAULT NULL COMMENT '方法名称',
  EXCEPTION_NAME                varchar(255) DEFAULT NULL COMMENT '异常类型',
  ERROR_MSG                     varchar(500) DEFAULT NULL COMMENT '异常信息',
  STACK_TRACE                   text DEFAULT NULL COMMENT '异常堆栈信息',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_LOG_OPERATION;
CREATE TABLE VTL_LOG_OPERATION (
  ID                            bigint NOT NULL COMMENT '主键ID',
  URL                           varchar(255) DEFAULT NULL COMMENT '请求url',
  REQUEST_ARGS                  varchar(65535) DEFAULT NULL COMMENT '请求参数',
  REQUEST_BODY                  varchar(65535) DEFAULT NULL COMMENT '请求体 request body',
  HTTP_METHOD                   varchar(10) DEFAULT NULL COMMENT 'http 请求方式',
  METHOD_NAME                   varchar(255) DEFAULT NULL COMMENT '请求方法名称',
  BROWSER                       varchar(255) DEFAULT NULL COMMENT '浏览器',
  OPERATING_SYSTEM              varchar(100) DEFAULT NULL COMMENT '操作系统',
  PLATFORM                      varchar(50) DEFAULT NULL COMMENT '设备平台类型',
  IP                            varchar(46) DEFAULT NULL COMMENT '操作IP地址',
  IP_LOCATION                   varchar(128) DEFAULT NULL COMMENT 'IP所属位置',
  SUCCEEDED                     int DEFAULT 0 COMMENT '操作是否成功。{0=失败, 1=成功}',
  ERROR_INFO                    varchar(1000) DEFAULT NULL COMMENT '错误消息',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_LOG_LOGIN;
CREATE TABLE VTL_LOG_LOGIN (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USERNAME                      varchar(50) DEFAULT NULL COMMENT '登录账号',
  LOGIN_TYPE                    varchar(50) DEFAULT NULL COMMENT '登录类型。枚举类 ELoginType.java',
  IP                            varchar(46) DEFAULT NULL COMMENT '登录IP地址',
  IP_LOCATION                   varchar(128) DEFAULT NULL COMMENT 'IP所属位置',
  BROWSER                       varchar(255) DEFAULT NULL COMMENT '浏览器',
  PLATFORM                      varchar(50) DEFAULT NULL COMMENT '设备平台类型',
  OPERATING_SYSTEM              varchar(255) DEFAULT NULL COMMENT '操作系统',
  SUCCEEDED                     int DEFAULT 0 COMMENT '登录是否成功。{0=失败, 1=成功}',
  ERROR_INFO                    varchar(1000) DEFAULT NULL COMMENT '错误消息',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_FILE;
CREATE TABLE VTL_FILE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  FILE_NAME                     varchar(255) NOT NULL COMMENT '原始文件名称',
  BUCKET                        varchar(50) NOT NULL COMMENT 'minio bucket name',
  FILE_PATH                     varchar(255) NOT NULL COMMENT 'minio 文件存储全路径',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_ROLE;
CREATE TABLE VTL_ROLE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  NAME 		                    varchar(50) NOT NULL COMMENT '角色名称',
  CODE 		                    varchar(50) NOT NULL COMMENT '角色编码',
  SEQ 		                    int DEFAULT 0 COMMENT '展示顺序',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_ROLE_UNIQUE_INDEX_CODE ON VTL_ROLE(CODE);


DROP TABLE IF EXISTS VTL_DEPT;
CREATE TABLE VTL_DEPT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  PARENT_ID              		bigint DEFAULT 0 COMMENT '父部门ID',
  ANCESTORS              		varchar(2048) DEFAULT '0' COMMENT '祖先层级ID，以"/" 分隔。',
  NAME 		                    varchar(50) NOT NULL COMMENT '部门名称',
  CODE 		                    varchar(50) NOT NULL COMMENT '部门编码',
  SEQ 		                    int DEFAULT 0 COMMENT '展示顺序',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_DEPT_UNIQUE_INDEX_CODE ON VTL_DEPT(CODE);


DROP TABLE IF EXISTS VTL_POST;
CREATE TABLE VTL_POST (
  ID                            bigint NOT NULL COMMENT '主键ID',
  NAME 		                    varchar(50) NOT NULL COMMENT '岗位名称',
  CODE 		                    varchar(50) NOT NULL COMMENT '岗位编码',
  SEQ 		                    int DEFAULT 0 COMMENT '展示顺序',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_POST_UNIQUE_INDEX_CODE ON VTL_POST(CODE);


DROP TABLE IF EXISTS VTL_DICT_TYPE;
CREATE TABLE VTL_DICT_TYPE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  NAME 		                    varchar(50) NOT NULL COMMENT '字典名称',
  TYPE_CODE 		            varchar(50) NOT NULL COMMENT '字典类型编码。',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_DICT_TYPE_UNIQUE_INDEX_TYPE_CODE ON VTL_DICT_TYPE(TYPE_CODE);


DROP TABLE IF EXISTS VTL_DICT_DATA;
CREATE TABLE VTL_DICT_DATA (
  ID                            bigint NOT NULL COMMENT '主键ID',
  TYPE_CODE                     varchar(50) NOT NULL COMMENT '字典类型编码。',
  DATA_CODE 		            varchar(50) NOT NULL COMMENT '字典数据编码。',
  LABEL 		                varchar(50) NOT NULL COMMENT '字典数据标签名称',
  SEQ 		                    int DEFAULT 0 COMMENT '展示顺序',
  DEFAULT_SELECTED              int DEFAULT 0 COMMENT '是否设置为默认选择项。{ 0：否；1：是；}',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_DICT_DATA_INDEX_TYPE_CODE ON VTL_DICT_DATA(TYPE_CODE);


DROP TABLE IF EXISTS VTL_MENU;
CREATE TABLE VTL_MENU (
  ID                            bigint NOT NULL COMMENT '主键ID',
  PARENT_ID              		bigint DEFAULT 0 COMMENT '父菜单ID',
  ANCESTORS              		varchar(2048) NOT NULL COMMENT '祖先层级ID，以"/" 分隔。',
  TITLE 		                varchar(50) NOT NULL COMMENT '菜单标题',
  TYPE 		                    varchar(5) DEFAULT 'BTN' COMMENT '菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮; OTHER=其它 }',
  PERMISSION 	                varchar(64) NOT NULL COMMENT '权限标识,唯一性约束。',
  SEQ 		                    int DEFAULT 0 COMMENT '展示顺序',
  ICON 				            varchar(64) COMMENT '菜单图标',
  URL 				            varchar(256) COMMENT '菜单请求链接地址。当 type 为 MENU 时生效。',
  OPEN_TYPE 				    varchar(7) DEFAULT NULL COMMENT '菜单打开类型。当 type 为 MENU 时，openType 生效，{ _iframe：正常打开；_blank：新建浏览器标签页 }',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_MENU_UNIQUE_INDEX_PERMISSION ON VTL_MENU(PERMISSION);


DROP TABLE IF EXISTS VTL_USER;
CREATE TABLE VTL_USER (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USERNAME                      varchar(64) NOT NULL COMMENT '用户登录名（字母数字下划线）',
  PASSWORD                      varchar(64) NOT NULL COMMENT '登录密码',
  PWD_SALT                      varchar(128) NOT NULL COMMENT '加盐',
  NICKNAME                      varchar(64) NOT NULL COMMENT '用户昵称',
  ID_CARD_NUMBER                varchar(20) DEFAULT NULL COMMENT '身份证号',
  GENDER                        varchar(6) DEFAULT NULL COMMENT '性别。关联数据字典：user_gender',
  EMAIL                         varchar(128) DEFAULT NULL COMMENT '电子邮箱',
  MOBILE_PHONE                  varchar(15) DEFAULT NULL COMMENT '移动电话',
  SECRET_KEY_2FA                varchar(16) DEFAULT NULL COMMENT '2FA（two-factor authentication）双重身份验证密钥',
  DISABLED                      int DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  DELETED                       int DEFAULT 0 NOT NULL COMMENT '逻辑删除',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_USER_UNIQUE_INDEX_USERNAME ON VTL_USER(USERNAME);


DROP TABLE IF EXISTS VTL_USER_PROFILE;
CREATE TABLE VTL_USER_PROFILE (
  ID                            bigint NOT NULL COMMENT '主键ID，也是用户ID',
  PROFILE_PICTURE               longtext DEFAULT NULL COMMENT '用户头像，以 Base64 文本存储的大字段。',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS VTL_USER_ROLE_RLT;
CREATE TABLE VTL_USER_ROLE_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USER_ID                       bigint NOT NULL COMMENT '用户ID',
  ROLE_ID                       bigint NOT NULL COMMENT '角色ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_USER_ROLE_RLT_UNIQUE_INDEX_USERID_ROLEID ON VTL_USER_ROLE_RLT(USER_ID, ROLE_ID);


DROP TABLE IF EXISTS VTL_USER_DEPT_RLT;
CREATE TABLE VTL_USER_DEPT_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USER_ID                       bigint NOT NULL COMMENT '用户ID',
  DEPT_ID                       bigint NOT NULL COMMENT '部门ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_USER_DEPT_RLT_UNIQUE_INDEX_USERID_ROLEID ON VTL_USER_DEPT_RLT(USER_ID, DEPT_ID);


DROP TABLE IF EXISTS VTL_USER_POST_RLT;
CREATE TABLE VTL_USER_POST_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USER_ID                       bigint NOT NULL COMMENT '用户ID',
  POST_ID                       bigint NOT NULL COMMENT '岗位ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_USER_POST_RLT_UNIQUE_INDEX_USERID_ROLEID ON VTL_USER_POST_RLT(USER_ID, POST_ID);


DROP TABLE IF EXISTS VTL_MENU_ROLE_RLT;
CREATE TABLE VTL_MENU_ROLE_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  MENU_ID                       bigint NOT NULL COMMENT '菜单ID',
  ROLE_ID                       bigint NOT NULL COMMENT '角色ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_MENU_ROLE_RLT_UNIQUE_INDEX_MENUID_ROLEID ON VTL_MENU_ROLE_RLT(MENU_ID, ROLE_ID);


DROP TABLE IF EXISTS VTL_MENU_DEPT_RLT;
CREATE TABLE VTL_MENU_DEPT_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  MENU_ID                       bigint NOT NULL COMMENT '菜单ID',
  DEPT_ID                       bigint NOT NULL COMMENT '部门ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_MENU_DEPT_RLT_UNIQUE_INDEX_MENUID_DEPTID ON VTL_MENU_DEPT_RLT(MENU_ID, DEPT_ID);


DROP TABLE IF EXISTS VTL_MENU_POST_RLT;
CREATE TABLE VTL_MENU_POST_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  MENU_ID                       bigint NOT NULL COMMENT '菜单ID',
  POST_ID                       bigint NOT NULL COMMENT '岗位ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_MENU_POST_RLT_UNIQUE_INDEX_MENUID_POSTID ON VTL_MENU_POST_RLT(MENU_ID, POST_ID);


DROP TABLE IF EXISTS VTL_MENU_USER_RLT;
CREATE TABLE VTL_MENU_USER_RLT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  MENU_ID                       bigint NOT NULL COMMENT '菜单ID',
  USER_ID                       bigint NOT NULL COMMENT '用户ID',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
CREATE INDEX VTL_MENU_USER_RLT_UNIQUE_INDEX_MENUID_USERID ON VTL_MENU_USER_RLT(MENU_ID, USER_ID);




