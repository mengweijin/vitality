drop table IF EXISTS QBG_DB;
create TABLE QBG_DB (
  id bigint NOT NULL COMMENT 'id',
  db_type varchar(20) NOT NULL COMMENT 'database type. mysql/h2/oracle/MongoDB etc. Refer to ${@link com.baomidou.mybatisplus.annotation.DbType}',
  url varchar(100) NOT NULL COMMENT 'jdbc url',
  username varchar(50) NULL COMMENT 'username',
  password varchar(50) NULL COMMENT 'password',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Datasource info';

insert into QBG_DB values (1, 'H2', 'jdbc:h2:file:./h2/quickboot;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL', 'sa', null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());


drop table IF EXISTS QBG_TABLE;
create TABLE QBG_TABLE (
  id bigint NOT NULL COMMENT 'id',
  db_id bigint NOT NULL COMMENT 'QBG_DB id',
  name varchar(20) NOT NULL COMMENT 'table name',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QBG_TABLE';

