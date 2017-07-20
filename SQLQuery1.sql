/*if exists(select name from sys.databases where name='st')
drop database st;
create database st;*/
use st;
 if exists (select name from sys.tables where name='admin')
 drop table admin;
 create table admin(
	uname varchar(20) primary key,
	password varchar(20) not null,
	name varchar(10) not null
 )

 if exists (select name from sys.tables where name='student')
 drop table student;
 create table student(
	sno varchar(50) primary key,
	sname varchar(50) not null,
	major varchar(50) not null
 )
 if exists (select name from sys.tables where name ='course')
 drop table course;
 create table course(
	cno varchar(50) primary key,
	cname varchar(50) not null
 )
 if exists (select name from sys.tables where name='s_c')
 drop table s_c;
 create table s_c(
	cno varchar(50)	primary key,
	sno varchar(50) not null,
	score varchar(10) not null
 )

 insert into admin 
 values('admin','admin','admin')

 insert into student 
 values('201412203501009','ylw','imformation')

 insert into course
 values ('001','math')

 insert into s_c
 values ('001','201412203501009','20')