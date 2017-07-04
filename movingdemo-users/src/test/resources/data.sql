kkkkkkkkk;
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

insert into users values('Jeri', '111111', true);
insert into users values('Sunny', '222', true);

insert into authorities values ('Jeri', 'ADMIN');
insert into authorities values ('Jeri', 'USER');
insert into authorities values ('Sunny', 'USER');


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

insert into companyusers (id, company, username, password, enabled) values (NULL, 'Learn', 'Wendy', '111', true);
insert into companyusers (id, company, username, password, enabled) values (NULL, 'Learn', 'Jin', '111', true);

create table if not exists companyauthorities (
	id integer IDENTITY,
	authority varchar(50) not null,
    userId int not null,
    foreign key (userId) references companyusers (id),
    primary key (id)
);

insert into companyauthorities (id, authority, userId) values (NULL, 'USER', 0);
insert into companyauthorities (id, authority, userId) values (NULL, 'USER', 1);
insert into companyauthorities (id, authority, userId) values (NULL, 'ADMIN', 1);