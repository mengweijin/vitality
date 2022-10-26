DROP TABLE IF EXISTS QBT_GEN_DATASOURCE;
create TABLE QBT_GEN_DATASOURCE (
  id bigint NOT NULL COMMENT 'id',
  db_type varchar(20) NOT NULL COMMENT 'database type. mysql/h2/oracle/redis/MongoDB etc. Refer to ${@link com.baomidou.mybatisplus.annotation.DbType}',
  url varchar(100) NOT NULL COMMENT 'jdbc url or others(For example: redis=http://host:port). ',
  username varchar(50) NULL COMMENT 'username',
  password varchar(50) NULL COMMENT 'password',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QBT_GEN_DATASOURCE';

insert into QBT_GEN_DATASOURCE values (1576744861023870978, 'H2', 'jdbc:h2:file:./h2/quickboot;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL', 'sa', null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());

