create database utezz;
use utezz;
create table if not exists position
(
id bigint auto_increment
primary key,
position varchar(50) not null,
description varchar(50) null
);

create table if not exists personal
(
id bigint auto_increment
primary key,
name varchar(40) not null,
surname varchar(40) not null,
lastname varchar(40) null,
salary double not null,
birthday date not null,
position_id bigint not null,
constraint fk_personal_position
foreign key (position_id) references position (id)
);

create table if not exists users
(
id bigint auto_increment
primary key,
email varchar(50) not null,
password varchar(100) not null,
role varchar(10) not null,
status tinyint not null,
created_at datetime default CURRENT_TIMESTAMP not null,
personal_id bigint not null,
constraint email
unique (email),
constraint fk_personal_users
foreign key (personal_id) references personal (id)
);