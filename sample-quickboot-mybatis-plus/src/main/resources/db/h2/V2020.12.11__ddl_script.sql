DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  id            bigint NOT NULL COMMENT '主键ID',
  name          varchar(64) NOT NULL COMMENT '名称',
  http_method   varchar(64) NOT NULL COMMENT 'http method',
  deleted       int(4) DEFAULT 0 NOT NULL COMMENT '逻辑删除',
  create_by     varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by 	varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time 	datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY sys_user_key_name_index (name) USING BTREE
) COMMENT='用户表';

insert into sys_user values(1, '张三', 'GET', 0, 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP());