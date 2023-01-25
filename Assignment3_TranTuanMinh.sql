create database Assignment3_TranTuanMinh
go
use Assignment3_TranTuanMinh
go

create table tblRole (
	roleID varchar(10) primary key not null,
	roleName nvarchar(50),
	status bit 
)
create table tblUsers ( 
	email varchar(30) primary key not null,
	fullName nvarchar(100),
	password varchar(100),
	phone varchar(30),
	address nvarchar(150),
	createDate date ,
	roleID varchar(10) foreign key references tblRole(roleID),
	status varchar(30) 
)
create table tblCategorys (
	categoryID varchar(30) primary key not null,
	categoryName nvarchar(50),
	status bit 
)
create table tblCars (
	carID varchar(30) primary key not null,
	categoryID varchar(30) foreign key references tblCategorys(categoryID),
	carName nvarchar(100),
	color varchar(30),
	year varchar(10),
	carPrice float check(carPrice>0),
	quantity int check(quantity>=0),
	linkImg varchar(150),
	status bit 
)
create table tblDiscountCode (
	code varchar(100) primary key not null,
	sale int ,
	expiryDate date,
	status bit 
)
create table tblOrders (
	orderID varchar(30) primary key not null,
	email varchar(30) foreign key references tblUsers(email),
	code varchar(100) foreign key references tblDiscountCode(code),
	totalPrice float ,
	orderDate date ,
	status bit
)
create table tblOrderDetail (
	detailID int IDENTITY (1,1) primary key not null,
	orderID varchar(30) foreign key references tblOrders(orderID),
	carID varchar(30) foreign key references tblCars(carID),
	amount int check(amount>0),
	price float check(price >0) ,
	rentalDate date,
	returnDate date,
	numberRentDay int check(numberRentDay>0),
	rating float,
	status bit 
)


INSERT INTO tblRole
values('AD','Admin',1)
INSERT INTO tblRole
values('US','Student',1)

INSERT INTO tblUsers
values('admin@fpt.edu.vn',N'admin name','123456','0123456789','FPT HCM','3/6/2021','AD','active')
INSERT INTO tblUsers
values('minhttse140690@fpt.edu.vn',N'Tran Tuan Minh','123456','0165464564','Binh Duong','3/6/2021','US','active')

INSERT INTO tblCategorys
values('SM','Small car',1)
INSERT INTO tblCategorys
values('MD','Medium car',1)
INSERT INTO tblCategorys
values('LA','Large car',1)

INSERT INTO tblCars
values('C1','SM','Car name 1','Black','1975',150000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C2','SM','Car name 2','Black','1975',160000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C3','SM','Car name 3','Black','1975',140000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C4','SM','Car name 4','Black','1975',130000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C5','SM','Car name 5','Black','1975',120000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C6','SM','Car name 6','Black','1975',100000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C7','SM','Car name 7','Black','1975',110000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C8','SM','Car name 8','Black','1975',90000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C9','SM','Car name 9','Black','1975',99000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C10','SM','Car name 10','Black','1975',155000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C11','SM','Car name 11','Black','1975',145000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C12','SM','Car name 12','Black','1975',165000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C13','SM','Car name 13','Black','1975',175000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C14','SM','Car name 14','Black','1975',185000,50,'Images/car.jpg',1)

INSERT INTO tblCars
values('C15','MD','Car name 15','Black','1970',250000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C16','MD','Car name 16','Black','1970',260000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C17','MD','Car name 17','Black','1970',270000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C18','MD','Car name 18','Black','1970',280000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C19','MD','Car name 19','Black','1970',290000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C20','MD','Car name 20','Black','1970',240000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C21','MD','Car name 21','Black','1970',255000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C22','MD','Car name 22','Black','1970',265000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C23','MD','Car name 23','Black','1970',275000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C24','MD','Car name 24','Black','1970',285000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C25','MD','Car name 25','Black','1970',295000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C26','MD','Car name 26','Black','1970',245000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C27','MD','Car name 27','Black','1970',235000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C28','MD','Car name 28','Black','1970',225000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C29','MD','Car name 29','Black','1970',215000,50,'Images/car.jpg',1)

INSERT INTO tblCars
values('C30','LA','Car name 30','Black','1980',300000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C31','LA','Car name 31','Black','1980',310000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C32','LA','Car name 32','Black','1980',320000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C33','LA','Car name 33','Black','1980',330000,50,'Images/car.jpg',1)
INSERT INTO tblCars
values('C34','LA','Car name 34','Black','1980',340000,50,'Images/car.jpg',1)

INSERT INTO tblDiscountCode
values('NOCODE',0,'1/1/3000',1)
INSERT INTO tblDiscountCode
values('123456',10,'3/30/2021',1)
INSERT INTO tblDiscountCode
values('654321',15,'3/30/2021',1)
INSERT INTO tblDiscountCode
values('567890',5,'3/30/2021',1)
INSERT INTO tblDiscountCode
values('098765',20,'3/30/2021',1)
INSERT INTO tblDiscountCode
values('456456',10,'3/30/2021',1)

