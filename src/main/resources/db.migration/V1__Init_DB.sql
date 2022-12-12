create table if not exists hibernate_sequence
(
    next_val bigint
) engine=InnoDB;
insert into hibernate_sequence(next_val)
values (1);

create table if not exists product
(
    id              bigint not null auto_increment primary key,
    provider_id     bigint not null,
    department_id   bigint not null,
    store_id        bigint not null,
    price           float  not null,
    quantity        bigint not null,
    expiration_date bigint not null,
    delivery_date   date   not null,

    foreign key (provider_id) references provider(id),
    foreign key (department_id) references department(id),
    foreign key (store_id) references store(id)
) engine=InnoDB;

create table if not exists department
(
    id             bigint not null auto_increment primary key,
    store_id       bigint       not null,
    name           varchar(255) not null,
    manager_number bigint       not null,
    foreign key (store_id) references store (id)
) engine=InnoDB;

create table if not exists store
(
    id              bigint not null auto_increment primary key,
    name            varchar(255),
    specialization  varchar(255),
    INN             varchar(255),
    address         varchar(255),
    director_number bigint
) engine=InnoDB;

create table if not exists employee
(
    id             bigint not null auto_increment primary key,
    name           varchar(255),
    surname        varchar(255),
    patronymic     varchar(255),
    address        varchar(255),
    gender         varchar(255),
    marital_status varchar(255),
    birthday       date,
    department_id  bigint not null,
    store_id       bigint not null,

    foreign key (department_id) references department(id),
    foreign key (store_id) references store(id)
) engine=InnoDB;

create table if not exists provider
(
    id      bigint not null auto_increment primary key,
    name    varchar(255),
    address varchar(255)
) engine=InnoDB;

create table if not exists contract
(
    id            bigint not null auto_increment primary key,
    provider_id   bigint not null,
    store_id      bigint not null,
    date_contract date,

    foreign key (provider_id) references provider(id),
    foreign key (store_id) references store(id)
) engine=InnoDB;

# alter table department
#     add constraint department_store_fk
#         foreign key (store_id) references store (id);

# alter table product
#     add constraint product_provider_fk
#         foreign key (provider_id) references provider (id),
#     add constraint product_department_fk
#         foreign key (department_id) references department (id),
#     add constraint product_store_fk
#         foreign key (store_id) references store (id);
#
# alter table contract
#     add constraint contract_provider_fk
#         foreign key (provider_id) references provider (id);
#
# alter table employee
#     add constraint employee_department_fk
#         foreign key (department_id) references department (id),
#     add constraint employee_store_fk
#         foreign key (store_id) references store (id);