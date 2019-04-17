Create database ShoppingDB
Use ShoppingDB

Create table Users
(
	id int primary key identity not null,
	name varchar(50) not null,
	email varchar(50) unique,
	password varchar(100) not null,
	role int not null,
	isDeleted bit not null
)
Drop table Users

Insert Into Users(name, email, password, role, isDeleted) Values('Nguyen Phuc Hau', 'hauloan@gmail.com', '123151', '1', 0)

Create table Role
(
	id int primary key identity not null,
	name varchar(50) not null
)
Select * From Users Where role = 2
Select * From Users Where role = 1 order by id ASC offset 10 rows fetch first 5 rows only
SELECT COUNT(*) FROM Users Where role = 1
delete from Users where email = 'loanconuong7@gmail.com'

Select * From Role
