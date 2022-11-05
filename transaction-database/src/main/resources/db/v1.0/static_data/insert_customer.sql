--liquibase formatted sql

--changeset liquibase:4
insert into ${schema}.customer (id, first_name, middle_name, last_name, document_id)
values ('5ec7087b-06e3-41af-b80d-3acd618d9182', 'Alexandra', 'M', 'Dan', 'CJ123456');

insert into ${schema}.customer (id, first_name, middle_name, last_name, document_id)
values ('719c7079-c742-4663-84ae-fa516a24837f', 'Joe', 'R', 'Doe', 'TEST212');

insert into ${schema}.customer (id, first_name, middle_name, last_name, document_id)
values ('f6350389-8092-4a4b-8a53-89293d05cd8d', 'Jane', 'D', 'Doe', '2142212');