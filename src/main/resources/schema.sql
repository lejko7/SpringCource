DROP TABLE IF EXISTS books;
drop TABLE if exists files;

create table books(
id INT AUTO_INCREMENT primary key,
author varchar(256) not null,
title varchar(256) not null,
size int default NULL
);

create table files(
id INT AUTO_INCREMENT primary key,
fileName varchar(256) not null,
fileType varchar(256) not null,
filePath varchar(256) not null
);
