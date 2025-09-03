drop database demo;
create database demo;
use database demo;

create table t_stock (
   id           bigint primary key auto_increment,
   commodity_id bigint,
   count        int
);
insert into t_stock values(1, 1, 10);


create table t_account (
    id    bigint primary key auto_increment,
    money decimal(10,2)
);
insert into t_account values(1, 1000);


create table t_order (
    id           bigint primary key auto_increment,
    user_id      bigint,
    commodity_id bigint,
    money        decimal(10,2),
    count        int
);

