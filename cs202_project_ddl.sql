create database cs202_project;
use cs202_project;

create table Users(
userId int primary key,
userType varchar(50) not null,
username varchar(255) unique,
nameSurname varchar(255) not null,
userPassword varchar(255) not null,
dateOfBirth date,
gender char(1),
phoneNumber varchar(50),
address	varchar(50)
);

create table Patient(
userId int primary key,
bloodType char(3),
foreign key (userId) references Users(userId)
);

create table Doctor(
userId int primary key,
expertise varchar(100),
foreign key (userId) references Users(userId)
);

create table UnavailabilityDuration(
durationId int primary key,
userId int,
unavailableFrom datetime not null,
unavailableTo datetime not null,
foreign key (userId) references Doctor(userId)
);

create table Nurse(
userId int primary key,
foreign key (userId) references Users(userId)
);

create table Manager(
userId int primary key,
foreign key (userId) references Users(userId)
);

create table Room(
roomId int primary key,
roomType varchar(50) not null,
isAvailable bool,
nurseId int,
foreign key (nurseId) references Nurse(userId)
);

create table Appointment(
appId int primary key,
appDate datetime,
patientId int,
doctorId int,
roomId int,
foreign key (patientId) references Patient(userId),
foreign key (doctorId) references Doctor(userId),
foreign key (roomId) references Room(roomId)
);

