--liquibase formatted sql
--changeset admin:1 splitStatements:true

-- 与 MySQL 区别
-- 1、h2 中，创建表最后面不能添加 COMMENT = '表注释'；
-- 2、h2 中，int/bigint 类型不能限制位数，比如：int(4) 会报错，需要去掉；

drop table IF EXISTS VTL_NOTICE;
create TABLE VTL_NOTICE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) DEFAULT NULL comment '名称',
  DESCRIPTION                   text DEFAULT NULL comment '内容',
  RELEASED                      char(1) DEFAULT 'N' comment '是否已发布。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_MESSAGE;
create TABLE VTL_MESSAGE (
  ID                            bigint NOT NULL comment '主键ID',
  CATEGORY                      varchar(50) DEFAULT NULL comment '消息分类。{@link EMessageCategory}',
  TITLE                         varchar(255) DEFAULT NULL comment '标题',
  CONTENT                       varchar(4000) DEFAULT NULL comment '内容',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_MESSAGE_RECEIVER;
create TABLE VTL_MESSAGE_RECEIVER (
  ID                            bigint NOT NULL comment '主键ID',
  MESSAGE_ID                    bigint NOT NULL comment '消息ID',
  USER_ID                       bigint DEFAULT NULL comment '消息接收者用户ID',
  VIEWED                        char(1) DEFAULT 'N' NOT NULL comment '是否已查看。[Y, N]',
  VIEWED_TIME                   datetime NULL comment '查看时间',
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
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_CONFIG_CODE on VTL_CONFIG(CODE);
create unique index UIDX_VTL_CONFIG_NAME on VTL_CONFIG(NAME);


drop table IF EXISTS VTL_CATEGORY;
create TABLE VTL_CATEGORY (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID                     bigint NOT NULL DEFAULT 0 comment 'PARENT ID',
  CODE                          varchar(500) NOT NULL comment '编码',
  NAME                          varchar(255) NOT NULL comment '名称',
  REMARK 	                    varchar(500) comment '备注',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否已禁用。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_CATEGORY_CODE on VTL_CATEGORY(CODE);


drop table IF EXISTS VTL_DICT_TYPE;
create TABLE VTL_DICT_TYPE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(100) NOT NULL comment '字典名称',
  CODE 		                    varchar(100) NOT NULL comment '字典类型编码',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_DICT_TYPE_CODE on VTL_DICT_TYPE(CODE);


drop table IF EXISTS VTL_DICT_DATA;
create TABLE VTL_DICT_DATA (
  ID                            bigint NOT NULL comment '主键ID',
  CODE 		                    varchar(100) NOT NULL comment '字典类型编码',
  VAL 		                    varchar(100) NOT NULL comment '字典数据值',
  LABEL 		                varchar(100) NOT NULL comment '字典数据标签名称',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否已禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_DICT_DATA_CODE_VAL on VTL_DICT_DATA(CODE, VAL);


drop table IF EXISTS VTL_LOG_ALERT;
create TABLE VTL_LOG_ALERT (
  ID                            bigint NOT NULL comment '主键ID',
  LOG_LEVEL                     varchar(25) DEFAULT NULL comment '日志级别',
  MESSAGE                       varchar(3000) DEFAULT NULL comment '日志信息',
  CLASS_NAME                    varchar(255) DEFAULT NULL comment '类名称',
  METHOD_NAME                   varchar(255) DEFAULT NULL comment '方法名称',
  EXCEPTION_NAME                varchar(255) DEFAULT NULL comment '异常类型',
  STACK_TRACE                   text DEFAULT NULL comment '异常堆栈信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_LOG_OPERATION;
create TABLE VTL_LOG_OPERATION (
  ID                            bigint NOT NULL comment '主键ID',
  TITLE                         varchar(255) DEFAULT NULL comment '操作日志模块标题',
  OPERATION_TYPE                varchar(10) DEFAULT NULL comment '操作类型枚举：EOperationType.java',
  HTTP_METHOD                   varchar(10) DEFAULT NULL comment 'http 请求方式',
  URL                           varchar(255) DEFAULT NULL comment '请求url',
  METHOD_NAME                   varchar(255) DEFAULT NULL comment '请求方法名称',
  REQUEST_DATA                  varchar(3000) DEFAULT NULL comment '请求数据',
  RESPONSE_DATA                 varchar(3000) DEFAULT NULL comment '响应数据',
  COST_TIME                     bigint NOT NULL DEFAULT 0 comment '执行消耗时间（毫秒）',
  SUCCESS                       char(1) DEFAULT 'Y' comment '操作是否成功。[Y, N]',
  ERROR_MSG                     varchar(3000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
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
  ERROR_MSG                     varchar(3000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_OSS;
create TABLE VTL_OSS (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) NOT NULL comment '原始文件名称',
  SUFFIX                        varchar(10) comment '文件后缀',
  STORAGE_PATH                  varchar(500) NOT NULL comment '文件存储路径',
  MD5                           varchar(128) NOT NULL comment 'MD5 码',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create index IDX_VTL_OSS_MD5 on VTL_OSS(MD5);

drop table IF EXISTS VTL_MENU;
create TABLE VTL_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT 0 comment '父菜单ID',
  TYPE 		                    varchar(10) NOT NULL DEFAULT 'BTN' comment '菜单类型。{ MENU=菜单; BTN=按钮; IFRAME=内嵌页面；URL=外链页面；}。前端对应：（0代表菜单、1代表iframe、2代表外链、3代表按钮）',
  TITLE 		                varchar(50) NOT NULL comment '菜单标题。兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加',
  ROUTER_NAME                   varchar(100) comment '路由名称。必须唯一并且和当前路由component字段对应的页面里用defineOptions包起来的name保持一致',
  ROUTER_PATH                   varchar(100) comment '路由路径。如：/vitality/system/user/index',
  COMPONENT_PATH                varchar(100) comment '组件路径。传component组件路径，那么path可以随便写，如果不传，component组件路径会跟path保持一致',
  SEQ 		                    int DEFAULT 1 comment '排序。平台规定只有`home`路由的`rank`才能为`0`',
  REDIRECT                      varchar(100) comment '路由重定向。（默认跳转地址）',
  ICON 				            varchar(100) comment '图标',
  EXTRA_ICON 				    varchar(100) comment '菜单名称右侧的额外图标',
  ENTER_TRANSITION              varchar(100) comment '进场动画（页面加载动画）',
  LEAVE_TRANSITION              varchar(100) comment '离场动画（页面加载动画）',
  ACTIVE_PATH                   varchar(100) comment '菜单激活（将某个菜单激活，主要用于通过query或params传参的路由，当它们通过配置showLink: false后不在菜单中显示，就不会有任何菜单高亮，而通过设置activePath指定激活菜单即可获得高亮，activePath为指定激活菜单的path）',
  PERMISSION 	                varchar(100) comment '权限。[*:*:*]',
  IFRAME_SRC 		            varchar(255) comment '配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面。',
  IFRAME_LOADING 		        char(1) DEFAULT 'N' NOT NULL comment '[Y, N]。加载动画（内嵌的iframe页面是否开启首次加载动画）。',
  KEEP_ALIVE 		            char(1) DEFAULT 'N' NOT NULL comment '[Y, N]。缓存页面（是否缓存该路由页面，开启后会保存该页面的整体状态，刷新后会清空状态）。',
  HIDDEN_TAG 		            char(1) DEFAULT 'N' NOT NULL comment '[Y, N]。标签页（当前菜单名称或自定义信息禁止添加到标签页）。',
  FIXED_TAG 		            char(1) DEFAULT 'N' NOT NULL comment '[Y, N]。固定标签页（当前菜单名称是否固定显示在标签页且不可关闭）。',
  SHOW_LINK 				    char(1) DEFAULT 'Y' NOT NULL comment '是否在菜单中显示。[Y, N]',
  SHOW_PARENT 				    char(1) DEFAULT 'Y' NOT NULL comment '是否显示父级菜单。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_DEPT;
create TABLE VTL_DEPT (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT 0 comment '父部门ID',
  NAME 		                    varchar(50) NOT NULL comment '部门名称',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


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
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_ROLE_CODE on VTL_ROLE(CODE);


drop table IF EXISTS VTL_POST;
create TABLE VTL_POST (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(50) NOT NULL comment '岗位名称',
  SEQ 		                    int DEFAULT 0 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);


drop table IF EXISTS VTL_USER;
create TABLE VTL_USER (
  ID                            bigint NOT NULL comment '主键ID',
  DEPT_ID                       bigint NOT NULL comment '部门ID',
  USERNAME                      varchar(64) NOT NULL comment '用户登录名（字母数字下划线）',
  NICKNAME                      varchar(64) NOT NULL comment '用户昵称',
  PASSWORD                      varchar(64) NOT NULL comment '登录密码',
  PASSWORD_LEVEL                varchar(30) DEFAULT 'MEDIUM' NOT NULL comment '密码强度。PasswdStrength.java',
  PASSWORD_CHANGE_TIME          datetime NULL DEFAULT CURRENT_TIMESTAMP comment '密码修改时间',
  ID_CARD                       varchar(20) DEFAULT NULL comment '身份证号',
  GENDER                        varchar(6) DEFAULT NULL comment '性别。关联数据字典：user_gender',
  EMAIL                         varchar(128) DEFAULT NULL comment '电子邮箱',
  MOBILE                        varchar(15) DEFAULT NULL comment '移动电话',
  TOTP                           varchar(16) DEFAULT NULL comment 'TOTP 动态口令验证密钥',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  DELETED                       char(1) DEFAULT 'N' NOT NULL comment '逻辑删除。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_USER_USERNAME on VTL_USER(USERNAME);


drop table IF EXISTS VTL_USER_AVATAR;
create TABLE VTL_USER_AVATAR (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  AVATAR                        clob DEFAULT NULL comment '用户头像，以 Base64 文本存储的大字段。',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_USER_AVATAR_UID on VTL_USER_AVATAR(USER_ID);


drop table IF EXISTS VTL_USER_PREFERENCE;
create TABLE VTL_USER_PREFERENCE (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  USER_MESSAGE                  char(1) DEFAULT 'Y' NOT NULL comment '其他用户的消息是否将以站内信的形式通知。[Y, N]',
  SYSTEM_MESSAGE                char(1) DEFAULT 'Y' NOT NULL comment '系统消息是否将以站内信的形式通知。[Y, N]',
  TODO_TASK                     char(1) DEFAULT 'Y' NOT NULL comment '待办任务是否将以站内信的形式通知。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_USER_PREFERENCE_UID on VTL_USER_PREFERENCE(USER_ID);


drop table IF EXISTS VTL_USER_POST;
create TABLE VTL_USER_POST (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  POST_ID                       bigint NOT NULL comment '岗位ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_USER_POST_UPID on VTL_USER_POST(USER_ID, POST_ID);


drop table IF EXISTS VTL_USER_ROLE;
create TABLE VTL_USER_ROLE (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_USER_ROLE_URID on VTL_USER_ROLE(USER_ID, ROLE_ID);


drop table IF EXISTS VTL_ROLE_MENU;
create TABLE VTL_ROLE_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  MENU_ID                       bigint NOT NULL comment '菜单ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
create unique index UIDX_VTL_ROLE_MENU_RMID on VTL_ROLE_MENU(ROLE_ID, MENU_ID);

