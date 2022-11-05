--liquibase formatted sql

--changeset liquibase:1
create table ${schema}.customer
(
    id            uuid default random_uuid() primary key,
    first_name    text not null,
    middle_name   text not null,
    last_name     text not null,
    nationality   text,
    date_of_birth text,
    address       text,
    phone         text,
    email         text,
    document_id   text not null
);
--rollback drop table ${schema}.customer;