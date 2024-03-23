create table user_city_ad_count
(
    user  varchar(64),
    date  varchar(64),
    city  varchar(64),
    ad    varchar(64),
    count bigint,
    primary key (user, date, city, ad)
);
