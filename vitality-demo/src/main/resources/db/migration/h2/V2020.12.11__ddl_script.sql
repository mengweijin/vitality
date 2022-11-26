DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  id            bigint NOT NULL COMMENT '主键ID',
  name          varchar(64) NOT NULL COMMENT '名称',
  gender        varchar(64) NOT NULL COMMENT '性别',
  deleted       int(4) DEFAULT 0 NOT NULL COMMENT '逻辑删除',
  create_by     varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by 	varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time 	datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY sys_user_key_name_index (name) USING BTREE
) COMMENT='用户表';
insert into sys_user values(1, '张三', 'MALE', 0, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());


DROP TABLE IF EXISTS sys_order;
CREATE TABLE sys_order (
  id            bigint NOT NULL COMMENT '主键ID',
  code          varchar(64) NOT NULL COMMENT '编号',
  create_by     varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by 	varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time 	datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT='订单表';
insert into sys_order values(20220101, '20220101', 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_order values(20220102, '20220102', 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());


DROP TABLE IF EXISTS sys_goods;
CREATE TABLE sys_goods (
  id            bigint NOT NULL COMMENT '主键ID',
  name          varchar(64) NOT NULL COMMENT '名称',
  create_by     varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by 	varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time 	datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id)
) COMMENT='商品表';
insert into sys_goods values(101, '香烟', 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_goods values(102, '瓜子', 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_goods values(103, '火腿肠', 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());


DROP TABLE IF EXISTS sys_order_goods_rlt;
CREATE TABLE sys_order_goods_rlt (
  id            bigint NOT NULL COMMENT '主键ID',
  order_id      bigint NOT NULL COMMENT 'order ID',
  goods_id      bigint NOT NULL COMMENT 'goods ID',
  create_by     varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by 	varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time 	datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY sys_order_goods_rlt_key_order_goods_index (order_id, goods_id) USING BTREE
) COMMENT='订单与商品关系表';
insert into sys_order_goods_rlt values(100001, 20220101, 101, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_order_goods_rlt values(100002, 20220101, 102, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_order_goods_rlt values(100003, 20220102, 102, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
insert into sys_order_goods_rlt values(100004, 20220102, 103, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

