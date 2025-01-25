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
