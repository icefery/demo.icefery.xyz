create table blacklist
(
    user varchar(64),
    primary key (user)
);

create table user_ad_count
(
    date  varchar(64),
    user  varchar(64),
    ad    varchar(64),
    count bigint,
    primary key (date, user, ad)
);
