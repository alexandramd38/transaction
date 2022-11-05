--liquibase formatted sql

--changeset liquibase:2
create table ${schema}.account
(
    id                uuid   default random_uuid() primary key,
    account_id        uuid   not null,
    customer_id       uuid   not null,
    balance           bigint not null,
    blocked_amount    bigint default 0,
    currency_code     text,
    status            text,
    account_type      text,
    name              text,
    account_code      text,
    creation_date     timestamp with time zone,
    date_last_updated timestamp with time zone,
    foreign key (customer_id) references ${schema}.customer(id)
);
--rollback drop table ${schema}.account;