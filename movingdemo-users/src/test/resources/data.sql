insert into users values('Jeri', '111111', true);
insert into users values('Sunny', '222', true);

insert into authorities values ('Jeri', 'ADMIN');
insert into authorities values ('Jeri', 'USER');
insert into authorities values ('Sunny', 'USER');

insert into companyusers (id, company, username, password, enabled) values (NULL, 'Learn', 'Wendy', '111', true);
insert into companyusers (id, company, username, password, enabled) values (NULL, 'Learn', 'Jin', '111', true);

insert into companyauthorities (id, authority, userId) values (NULL, 'USER', 0);
insert into companyauthorities (id, authority, userId) values (NULL, 'USER', 1);
insert into companyauthorities (id, authority, userId) values (NULL, 'ADMIN', 1);