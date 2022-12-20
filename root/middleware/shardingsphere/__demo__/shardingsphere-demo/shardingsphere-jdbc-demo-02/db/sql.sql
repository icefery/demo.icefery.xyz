drop database demo;

create database demo;

use demo;

-- t_order
create table t_order0 (
   id         bigint      not null,
   order_code varchar(64) not null,
   primary key(id)
);

create table t_order1 like t_order0;
create table t_order2 like t_order0;

-- t_order_item
create table t_order_item0 (
    id            bigint        not null,
    order_code    varchar(64)   not null,
    product_price decimal(10,2) not null,
    product_count bigint        not null,
    primary key(id)
);

create table t_order_item1 like t_order_item0;
create table t_order_item2 like t_order_item0;

-- t_dict
create table t_dict (
    id         bigint      not null,
    dict_key   varchar(64) not null,
    dict_value varchar(64) not null,
    primary key(id)
);
