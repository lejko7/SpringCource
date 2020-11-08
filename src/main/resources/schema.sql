DROP TABLE IF EXISTS books;

create table books(
id INT AUTO_INCREMENT primary key,
author varchar(256) not null,
title varchar(256) not null,
size int default NULL
);
