--liquibase formatted sql
--changeset admin:1 splitStatements:true

-- 这个数据库脚本在：logback-classic/src/main/java/ch/qos/logback/classic/db/script/h2.sql
-- 并且默认的 event_id IDENTITY NOT NULL 的写法是不正确的，需要调整。并且修改为自定义ID，而非自增ID。

DROP TABLE logging_event_exception IF EXISTS;
DROP TABLE logging_event_property IF EXISTS;
DROP TABLE logging_event IF EXISTS;

CREATE TABLE logging_event (
  timestmp BIGINT NOT NULL,
  formatted_message LONGVARCHAR NOT NULL,
  logger_name VARCHAR(256) NOT NULL,
  level_string VARCHAR(256) NOT NULL,
  thread_name VARCHAR(256),
  reference_flag SMALLINT,
  arg0 VARCHAR(256),
  arg1 VARCHAR(256),
  arg2 VARCHAR(256),
  arg3 VARCHAR(256),
  caller_filename VARCHAR(256), 
  caller_class VARCHAR(256), 
  caller_method VARCHAR(256), 
  caller_line VARCHAR(4),
  event_id BIGINT NOT NULL PRIMARY KEY);


CREATE TABLE logging_event_property (
  event_id BIGINT NOT NULL,
  mapped_key  VARCHAR(254) NOT NULL,
  mapped_value LONGVARCHAR,
  PRIMARY KEY(event_id, mapped_key),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id));


CREATE TABLE logging_event_exception (
  event_id BIGINT NOT NULL,
  i SMALLINT NOT NULL,
  trace_line VARCHAR(256) NOT NULL,
  PRIMARY KEY(event_id, i),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id));
