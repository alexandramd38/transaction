--liquibase formatted sql

--changeset liquibase:5
insert into ${schema}.account(account_id, customer_id, balance, currency_code)
values ('849beade-e6cd-4f9d-8379-072f6e04b649', '5ec7087b-06e3-41af-b80d-3acd618d9182', 200000, 'EUR');

insert into ${schema}.account(account_id, customer_id, balance, currency_code)
values ('30171b6e-5959-4b72-9c85-559575f36fe2', '719c7079-c742-4663-84ae-fa516a24837f', 30000, 'EUR');

insert into ${schema}.account(account_id, customer_id, balance, currency_code)
values ('4ca67fb8-6927-445b-828c-9fa4903df37c', 'f6350389-8092-4a4b-8a53-89293d05cd8d', 30000, 'EUR');
