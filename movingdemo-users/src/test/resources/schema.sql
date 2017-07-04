drop table if exists authorities;
drop table if exists users;

create table if not exists users (
	username varchar(50) not null primary key,
	password varchar(50) not null,
	enabled boolean not null
);

create table if not exists authorities  (
	username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username,authority);


drop table if exists companyauthorities;
drop table if exists companyusers;

create table if not exists companyusers(
	id integer identity,
	company varchar(50) not null,
 	username varchar(50) not null,
	password varchar(50) not null,
	enabled boolean not null,
    CONSTRAINT index_company_username UNIQUE (company, username),
    primary key (id)
);

create table if not exists companyauthorities (
	id integer IDENTITY,
	authority varchar(50) not null,
    userId int not null,
    foreign key (userId) references companyusers (id),
    primary key (id)
);
