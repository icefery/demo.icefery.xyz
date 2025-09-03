-- @formatter:off
drop database demo;

create database demo;

use demo;

create table t_user(
    id       bigint      not null auto_increment,
    username varchar(64) not null,
    primary key(id)
);
-- @formatter:on
