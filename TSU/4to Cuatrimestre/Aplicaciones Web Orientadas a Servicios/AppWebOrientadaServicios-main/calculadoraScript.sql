create database calculadora;
use calculadora;

create table operation (
	id bigint primary key auto_increment,
	`type` varchar (15) not null,
	first_number double not null,
	second_number  double not null default 0.0,
	result double not null,
	created_at datetime not null default now()
);

select * from operation;
drop table operation;