drop schema if exists demo;

create schema demo;

use demo;

create table t_user (
    id       bigint      primary key,
    username varchar(64) unique  key,
    email    varchar(64)
);
