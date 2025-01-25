--liquibase formatted sql
--changeset admin:3 splitStatements:true context:dev

update VTL_MENU set IFRAME_SRC='http://124.70.184.112:8002/doc.html' where ID=10081002 and TITLE='接口文档';
