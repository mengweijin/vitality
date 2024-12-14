--liquibase formatted sql
--changeset admin:3 splitStatements:true context:test

INSERT INTO VTL_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1,      0,   'vtl_address',                 '地址区域分类','地址区域分类',1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1001,   1,   'vtl_address_shaanxi',         '陕西省',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1001001,1001,'vtl_address_shaanxi_xian',    '西安市',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1001002,1001,'vtl_address_shaanxi_xianyang','咸阳市',      NULL,         2,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1001003,1001,'vtl_address_shaanxi_hanzhong','汉中市',      NULL,         3,'Y',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1002,1,      'vtl_address_sichuan',         '四川省',      NULL,         2,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP()),
	 (1002001,1002,'vtl_address_sichuan_chengdou','成都市',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());


insert into VTL_MENU (ID,PARENT_ID,TYPE,TITLE,ROUTER_NAME,ROUTER_PATH,COMPONENT_PATH,SEQ,REDIRECT,ICON,EXTRA_ICON,ENTER_TRANSITION,LEAVE_TRANSITION,ACTIVE_PATH,PERMISSION,IFRAME_SRC,IFRAME_LOADING,KEEP_ALIVE,HIDDEN_TAG,FIXED_TAG,SHOW_LINK,SHOW_PARENT,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2001001,10081,'MENU','测试菜单','TestMenu','/vitality/system/tpl/index',null,1,null,'ep:grid',null,null,null,null,'system:test:view',null,'N','N','N','N','Y','Y',1,current_timestamp(),1,current_timestamp());
