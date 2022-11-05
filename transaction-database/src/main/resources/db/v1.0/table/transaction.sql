--liquibase formatted sql

--changeset liquibase:3
create table ${schema}.transfer
(
    id                     uuid default random_uuid() primary key,
    reference              text   not null,
    transfer_reference     uuid   not null,
    source_account_id      uuid   not null,
    beneficiary_account_id uuid   not null,
    amount                 bigint not null,
    status                 text,
    currency_code          text,
    submission_timestamp   text,
    creation_timestamp     text
);
--rollback drop table ${schema}.transfer;