create schema if not exists example;

create table if not exists example.sort_input_to_output (
    id     varchar(32) not null default (replace(uuid(), '-', '')),
    input  json        not null,
    output json        not null,
    cdt    datetime    not null default (current_timestamp()),
    primary key (id)
);
