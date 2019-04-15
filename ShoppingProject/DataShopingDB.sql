Create database ShoppingDB
Use ShoppingDB

Create table Users
(
	id int primary key identity not null,
	name varchar(50),
	email varchar(50),
	password varchar(100),
	role int not null,
	isDeleted bit not null
)
Drop table Users

Create table Role
(
	id int primary key identity not null,
	name varchar(50) not null
)