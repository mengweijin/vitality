--liquibase formatted sql
--changeset admin:202304 splitStatements:true context:dev

update VT_CONFIG set VAL='false' where CODE='vt_captcha_enabled';
