-- 与 MySQL 区别
-- 1、h2 中，创建表最后面不能添加 COMMENT = '表注释'；
-- 2、int/bigint 类型不能限制位数，比如：int(4) 会报错，需要去掉；

drop table IF EXISTS VTL_MESSAGE;
create TABLE VTL_MESSAGE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) DEFAULT NULL comment '名称',
  DESCRIPTION                   varchar(2000) DEFAULT NULL comment '内容',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);

drop table IF EXISTS VTL_NOTICE;
create TABLE VTL_NOTICE (
  ID                            bigint NOT NULL comment '主键ID',
  AVATAR                        varchar(255) DEFAULT NULL comment '图像链接url',
  NAME                          varchar(255) DEFAULT NULL comment '名称',
  DESCRIPTION                   text DEFAULT NULL comment '内容',
  RELEASED                      char(1) DEFAULT 'N' comment '是否已发布。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_CONFIG;
create TABLE VTL_CONFIG (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) NOT NULL comment '名称',
  CODE                          varchar(100) NOT NULL comment '编码',
  VAL                           varchar(255) NOT NULL comment '值',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_CONFIG_1 on VTL_CONFIG(CODE);
create unique index UNIQUE_INDEX_VTL_CONFIG_2 on VTL_CONFIG(NAME);


drop table IF EXISTS VTL_DICT_TYPE;
create TABLE VTL_DICT_TYPE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(50) NOT NULL comment '字典名称',
  TYPE_CODE 		            varchar(50) NOT NULL comment '字典类型编码。',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_DICT_TYPE_1 on VTL_DICT_TYPE(TYPE_CODE);


drop table IF EXISTS VTL_DICT_DATA;
create TABLE VTL_DICT_DATA (
  ID                            bigint NOT NULL comment '主键ID',
  TYPE_CODE                     varchar(50) NOT NULL comment '字典类型编码。',
  DATA_CODE 		            varchar(50) NOT NULL comment '字典数据编码。',
  LABEL 		                varchar(50) NOT NULL comment '字典数据标签名称',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  SELECTED                      char(1) DEFAULT 'N' NOT NULL comment '是否设置为默认选择项。[Y, N]',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否已禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_DICT_DATA_1 on VTL_DICT_DATA(TYPE_CODE, DATA_CODE);


drop table IF EXISTS VTL_LOG_ERROR;
create TABLE VTL_LOG_ERROR (
  ID                            bigint NOT NULL comment '主键ID',
  CLASS_NAME                    varchar(255) DEFAULT NULL comment '类名称',
  METHOD_NAME                   varchar(255) DEFAULT NULL comment '方法名称',
  EXCEPTION_NAME                varchar(255) DEFAULT NULL comment '异常类型',
  ERROR_MSG                     varchar(500) DEFAULT NULL comment '异常信息',
  STACK_TRACE                   text DEFAULT NULL comment '异常堆栈信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


drop table IF EXISTS VTL_LOG_OPERATION;
create TABLE VTL_LOG_OPERATION (
  ID                            bigint NOT NULL comment '主键ID',
  URL                           varchar(255) DEFAULT NULL comment '请求url',
  REQUEST_ARGS                  varchar(65535) DEFAULT NULL comment '请求参数',
  REQUEST_BODY                  varchar(65535) DEFAULT NULL comment '请求体 request body',
  HTTP_METHOD                   varchar(10) DEFAULT NULL comment 'http 请求方式',
  METHOD_NAME                   varchar(255) DEFAULT NULL comment '请求方法名称',
  SUCCESS                       char(1) DEFAULT 'Y' comment '操作是否成功。[Y, N]',
  FAILINFO                      varchar(2000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


drop table IF EXISTS VTL_LOG_LOGIN;
create TABLE VTL_LOG_LOGIN (
  ID                            bigint NOT NULL comment '主键ID',
  USERNAME                      varchar(50) DEFAULT NULL comment '登录账号',
  LOGIN_TYPE                    varchar(50) DEFAULT NULL comment '登录类型。枚举类 ELoginType.java',
  IP                            varchar(46) DEFAULT NULL comment '登录IP地址',
  IP_LOCATION                   varchar(128) DEFAULT NULL comment 'IP所属位置',
  BROWSER                       varchar(255) DEFAULT NULL comment '浏览器',
  PLATFORM                      varchar(50) DEFAULT NULL comment '设备平台类型',
  OS                            varchar(255) DEFAULT NULL comment '操作系统',
  SUCCESS                       char(1) DEFAULT 'Y' comment '登录是否成功。[Y, N]',
  FAILINFO                      varchar(2000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


drop table IF EXISTS VTL_FILE;
create TABLE VTL_FILE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) NOT NULL comment '原始文件名称',
  SUFFIX                        varchar(10) comment '文件后缀',
  STORAGE_PATH                  varchar(500) NOT NULL comment '文件存储路径',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


drop table IF EXISTS VTL_MENU;
create TABLE VTL_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT 0 comment '父菜单ID',
  TITLE 		                varchar(50) NOT NULL comment '菜单标题',
  TYPE 		                    varchar(4) DEFAULT 'BTN' comment '菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮 }',
  PERMISSION 	                varchar(64) NOT NULL comment '权限标识',
  SEQ 		                    int DEFAULT 0 comment '展示顺序',
  ICON 				            varchar(64) comment '菜单图标',
  URL 				            varchar(256) comment '菜单请求链接地址。当 type 为 MENU 时生效。',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);


drop table IF EXISTS VTL_DEPT;
create TABLE VTL_DEPT (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT 0 comment '父部门ID',
  NAME 		                    varchar(50) NOT NULL comment '部门名称',
  CODE 		                    varchar(50) NOT NULL comment '部门编码',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_DEPT_1 on VTL_DEPT(CODE);


drop table IF EXISTS VTL_ROLE;
create TABLE VTL_ROLE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(50) NOT NULL comment '角色名称',
  CODE 		                    varchar(50) NOT NULL comment '角色编码',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_ROLE_1 on VTL_ROLE(CODE);


drop table IF EXISTS VTL_POST;
create TABLE VTL_POST (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(50) NOT NULL comment '岗位名称',
  CODE 		                    varchar(50) NOT NULL comment '岗位编码',
  SEQ 		                    int DEFAULT 0 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_POST_1 on VTL_POST(CODE);


drop table IF EXISTS VTL_USER;
create TABLE VTL_USER (
  ID                            bigint NOT NULL comment '主键ID',
  USERNAME                      varchar(64) NOT NULL comment '用户登录名（字母数字下划线）',
  NICKNAME                      varchar(64) NOT NULL comment '用户昵称',
  PASSWORD                      varchar(64) NOT NULL comment '登录密码',
  SALT                          varchar(128) NOT NULL comment '加盐',
  ID_CARD                       varchar(20) DEFAULT NULL comment '身份证号',
  GENDER                        varchar(6) DEFAULT NULL comment '性别。关联数据字典：user_gender',
  EMAIL                         varchar(128) DEFAULT NULL comment '电子邮箱',
  MOBILE                        varchar(15) DEFAULT NULL comment '移动电话',
  SECRET_KEY                    varchar(16) DEFAULT NULL comment '2FA（two-factor authentication）双重身份验证密钥',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  DELETED                       char(1) DEFAULT 'N' NOT NULL comment '逻辑删除。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_USER_1 on VTL_USER(USERNAME);


drop table IF EXISTS VTL_USER_PROFILE;
create TABLE VTL_USER_PROFILE (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  PROFILE                       longtext DEFAULT NULL comment '用户头像，以 Base64 文本存储的大字段。',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_USER_PROFILE_1 on VTL_USER_PROFILE(USER_ID);


drop table IF EXISTS VTL_USER_DEPT;
create TABLE VTL_USER_DEPT (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  DEPT_ID                       bigint NOT NULL comment '部门ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_USER_DEPT_1 on VTL_USER_DEPT(USER_ID, DEPT_ID);


drop table IF EXISTS VTL_USER_POST;
create TABLE VTL_USER_POST (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  POST_ID                       bigint NOT NULL comment '岗位ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_USER_POST_1 on VTL_USER_POST(USER_ID, POST_ID);


drop table IF EXISTS VTL_USER_ROLE;
create TABLE VTL_USER_ROLE (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_USER_ROLE_1 on VTL_USER_ROLE(USER_ID, ROLE_ID);


drop table IF EXISTS VTL_ROLE_MENU;
create TABLE VTL_ROLE_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  MENU_ID                       bigint NOT NULL comment '菜单ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
);
create unique index UNIQUE_INDEX_VTL_ROLE_MENU_1 on VTL_ROLE_MENU(ROLE_ID, MENU_ID);

