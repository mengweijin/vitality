--liquibase formatted sql
--changeset admin:202304 splitStatements:true context:dev

update VT_MENU set IFRAME_SRC='http://124.70.184.112:8002/doc.html' where ID=10081002 and TITLE='接口文档';
