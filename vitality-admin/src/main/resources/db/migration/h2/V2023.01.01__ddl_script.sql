DROP TABLE IF EXISTS VTL_NOTICE;
CREATE TABLE VTL_NOTICE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  AVATAR                        varchar(255) DEFAULT NULL COMMENT '图像链接url',
  TITLE                         varchar(255) DEFAULT NULL COMMENT '标题',
  DESCRIPTION                   varchar(20000) DEFAULT NULL COMMENT '内容',
  RELEASED                      int(4) DEFAULT 0 COMMENT '是否已发布。{0=否, 1=是}',
  RECEIVED_BY                   bigint NOT NULL COMMENT '消息接收人',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT = '通知记录表';

DROP TABLE IF EXISTS VTL_ANNOUNCEMENT;
CREATE TABLE VTL_ANNOUNCEMENT (
  ID                            bigint NOT NULL COMMENT '主键ID',
  AVATAR                        varchar(255) DEFAULT NULL COMMENT '图像链接url',
  TITLE                         varchar(255) DEFAULT NULL COMMENT '标题',
  DESCRIPTION                   varchar(20000) DEFAULT NULL COMMENT '内容',
  RELEASED                      int(4) DEFAULT 0 COMMENT '是否已发布。{0=否, 1=是}',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT = '公告管理表';


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
  PRIMARY KEY (id),
  UNIQUE KEY VTL_CONFIG_UNIQUE_INDEX_CODE (CODE) USING BTREE
) COMMENT = '配置管理表';


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
) COMMENT = '系统错误日志记录表';


DROP TABLE IF EXISTS VTL_LOG_OPERATION;
CREATE TABLE VTL_LOG_OPERATION (
  ID                            bigint(20) NOT NULL COMMENT '主键ID',
  URL                           varchar(255) DEFAULT NULL COMMENT '请求url',
  HTTP_METHOD                   varchar(10) DEFAULT NULL COMMENT 'http 请求方式',
  METHOD_NAME                   varchar(255) DEFAULT NULL COMMENT '请求方法名称',
  IP                            varchar(46) DEFAULT NULL COMMENT '操作IP地址',
  IP_LOCATION                   varchar(128) DEFAULT NULL COMMENT 'IP所属位置',
  SUCCEEDED                     int(4) DEFAULT 0 COMMENT '操作是否成功。{0=失败, 1=成功}',
  ERROR_INFO                    varchar(1000) DEFAULT NULL COMMENT '错误消息',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT = '系统操作日志表';


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
) COMMENT = '系统文件表';


DROP TABLE IF EXISTS VTL_ROLE;
CREATE TABLE VTL_ROLE (
  ID                            bigint NOT NULL COMMENT '主键ID',
  NAME 		                    varchar(50) NOT NULL COMMENT '角色名称',
  CODE 		                    varchar(50) NOT NULL COMMENT '角色编码',
  SEQ 		                    int(4) DEFAULT 0 COMMENT '展示顺序',
  DISABLED                      int(4) DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY VTL_ROLE_UNIQUE_INDEX_CODE (CODE) USING BTREE
) COMMENT = '角色管理表';


DROP TABLE IF EXISTS VTL_POST;
CREATE TABLE VTL_POST (
  ID                            bigint NOT NULL COMMENT '主键ID',
  NAME 		                    varchar(50) NOT NULL COMMENT '岗位名称',
  CODE 		                    varchar(50) NOT NULL COMMENT '岗位编码',
  SEQ 		                    int(4) DEFAULT 0 COMMENT '展示顺序',
  DISABLED                      int(4) DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY VTL_POST_UNIQUE_INDEX_CODE (CODE) USING BTREE
) COMMENT = '岗位管理表';


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
  PRIMARY KEY (id),
  UNIQUE KEY VTL_DICT_TYPE_UNIQUE_INDEX_TYPE_CODE (TYPE_CODE) USING BTREE
) COMMENT = '字典类型表';

DROP TABLE IF EXISTS VTL_DICT_DATA;
CREATE TABLE VTL_DICT_DATA (
  ID                            bigint NOT NULL COMMENT '主键ID',
  TYPE_CODE                     varchar(50) NOT NULL COMMENT '字典类型编码。',
  DATA_CODE 		            varchar(50) NOT NULL COMMENT '字典数据编码。',
  LABEL 		                varchar(50) NOT NULL COMMENT '字典数据标签名称',
  SEQ 		                    int(4) DEFAULT 0 COMMENT '展示顺序',
  DEFAULT_SELECTED              int(4) DEFAULT 0 COMMENT '是否设置为默认选择项。{ 0：否；1：是；}',
  DISABLED                      int(4) DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  KEY VTL_DICT_DATA_INDEX_TYPE_CODE (TYPE_CODE) USING BTREE
) COMMENT = '字典数据表';


DROP TABLE IF EXISTS VTL_MENU;
CREATE TABLE VTL_MENU (
  ID                            bigint NOT NULL COMMENT '主键ID',
  PARENT_ID              		bigint DEFAULT 0 COMMENT '父菜单ID',
  TITLE 		                varchar(50) NOT NULL COMMENT '菜单标题',
  TYPE 		                    varchar(5) DEFAULT 'BTN' COMMENT '菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮; OTHER=其它 }',
  PERMISSION 	                varchar(64) NOT NULL COMMENT '权限标识,唯一性约束。',
  SEQ 		                    int(4) DEFAULT 0 COMMENT '展示顺序',
  ICON 				            varchar(64) COMMENT '菜单图标',
  URL 				            varchar(256) COMMENT '菜单请求链接地址。当 type 为 1 时生效。',
  OPEN_TYPE 				    varchar(7) DEFAULT NULL COMMENT '菜单打开类型。当 type 为 1 时，openType 生效，{ _iframe：正常打开；_blank：新建浏览器标签页 }',
  SYSTEM_DEFAULT 				int(4) DEFAULT 0 COMMENT '是否系统内置菜单。{ 0：否；1：是；}',
  DISABLED                      int(4) DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  REMARK 	                    varchar(500) COMMENT '备注',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY VTL_MENU_UNIQUE_INDEX_PERMISSION (PERMISSION) USING BTREE
) COMMENT = '菜单及权限配置表';


DROP TABLE IF EXISTS VTL_USER;
CREATE TABLE VTL_USER (
  ID                            bigint NOT NULL COMMENT '主键ID',
  USERNAME                      varchar(64) NOT NULL COMMENT '用户登录名（字母数字下划线）',
  PASSWORD                      varchar(64) NOT NULL COMMENT '登录密码',
  NICKNAME                      varchar(64) NOT NULL COMMENT '用户昵称',
  GENDER                        varchar(6) DEFAULT NULL COMMENT '性别。关联数据字典：user_gender',
  EMAIL                         varchar(128) DEFAULT NULL COMMENT '电子邮箱',
  MOBILE_PHONE                  varchar(15) DEFAULT NULL COMMENT '移动电话',
  SECRET_KEY_2FA                varchar(16) DEFAULT NULL COMMENT '2FA（two-factor authentication）双重身份验证密钥',
  DISABLED                      int(4) DEFAULT 0 NOT NULL COMMENT '是否已禁用。{ 0：正常；1：禁用；}',
  DELETED                       int(4) DEFAULT 0 NOT NULL COMMENT '逻辑删除',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY VTL_USER_UNIQUE_INDEX_USERNAME (USERNAME) USING BTREE
) COMMENT = '用户表';


DROP TABLE IF EXISTS VTL_USER_PROFILE;
CREATE TABLE VTL_USER_PROFILE (
  ID                            bigint NOT NULL COMMENT '主键ID，也是用户ID',
  PROFILE_PICTURE               longtext DEFAULT NULL COMMENT '用户头像，以 Base64 文本存储的大字段。',
  CREATE_BY                     bigint DEFAULT NULL COMMENT '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL COMMENT '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT = '用户头像存储表';

