
drop table if exists users;
drop table if exists employees;
create table users (id int not null, userName varchar(30), password varchar(100), roles varchar(20), isActive int);
create table employees (id int, firstName varchar(30), lastName varchar(100), email varchar(20));