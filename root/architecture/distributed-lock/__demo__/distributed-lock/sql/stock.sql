create database if not exists demo;

create table if not exists demo.stock(
    id             bigint      not null primary key auto_increment,
    product_code   varchar(64) not null comment '商品编码',
    warehouse_code varchar(64) not null comment '仓库编码',
    stock          bigint      not null comment '库存'
)
comment '库存表';

insert into demo.stock(id, product_code, warehouse_code, stock) values(1, '1001', '001', 5000);