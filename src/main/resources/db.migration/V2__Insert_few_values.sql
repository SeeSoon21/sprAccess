insert into store (id, name, specialization, INN, address, director_number)
values (1, 'Bebrovka', 'Things', '1234567899', 'Moscow, st. Bobrovina 9', 1);

insert into provider (id, name, address)
values (1, 'Petr', 'Habarovsk, st. Bobrovina 9');

insert into department (id, store_id, name, manager_number)
values (1, 1, 'Socks', 1);

insert into contract (id, provider_id, store_id, date_contract)
values (1, 1, 1, '2008-7-04');

insert into employee (id, name, surname, patronymic, address, gender, marital_status, birthday, department_id, store_id)
values (1, 'Ivan', 'Ivanov', 'Ivanovich', 'Tver, st. Bobrovina 9', 'male', 'not married', '2001-7-04', 1, 1);

insert into product (id, provider_id, department_id, store_id, price, quantity, expiration_date, delivery_date)
values (1, 1, 1, 1, 300, 48, 200, '2008-7-04');