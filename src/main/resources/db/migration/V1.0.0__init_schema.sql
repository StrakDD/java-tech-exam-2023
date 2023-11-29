create table if not exists users
(
    id                  varchar(36) primary key,
    first_name          varchar(255) not null,
    last_name           varchar(255) not null,
    last_login_time_utc timestamp
);
