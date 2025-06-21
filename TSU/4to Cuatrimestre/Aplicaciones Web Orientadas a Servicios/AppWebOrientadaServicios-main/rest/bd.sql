create database utez;
use utez;

create table positions(
    id bigint AUTO_INCREMENT PRIMARY KEY not null,
    position varchar(30) not null,
    description text not null
);

create table personal(
    id bigint AUTO_INCREMENT PRIMARY key not null,
    name varchar(30) not null,
    surname varchar(30) not null,
    lastname varchar(30) not null,
    salary double not null,
    position_id bigint not null,
    birthday date not null,
    
    CONSTRAINT psa_pto_positions_fk FOREIGN KEY (position_id) REFERENCES positions(id)
);