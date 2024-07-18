drop table users;
drop table accounts;
drop table transactions;
drop type account_enum;
drop type transaction_enum;

create table users(
	user_id integer primary key check (user_id > 0),
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	email varchar(40) unique not null,
	password varchar(20) not null check (LENGTH(password) > 7)
);

insert into users (user_id, first_name, last_name, email, password) values 
	(7437, 'Valentia', 'Josefs', 'vjosefs0@google.ca', 'yE4*8IBZ?V\'),
	(83986, 'Wileen', 'Knappitt', 'wknappitt1@qq.com', 'qT6+Ls=VX'),
	(53108, 'Dido', 'Marquand', 'dmarquand2@geocities.com', 'zR1#4x`&$%8J_7G'),
	(65536, 'Guillema', 'Kendle', 'gkendle3@bing.com', 'wU8#5YHV@X'),
	(51468, 'Tansy', 'Bogges', 'tbogges4@phoca.cz', 'wZ3&ny2\Rce~'),
	(79055, 'Griffin', 'Tooker', 'gtooker5@elegantthemes.com', 'jM2(xuGAt'),
	(24900, 'Agnes', 'Lindegard', 'alindegard6@earthlink.net', 'fR9_mGIN6g'),
	(64894, 'Bernette', 'McGilben', 'bmcgilben7@mapy.cz', 'sI3}YU"$YjXGs+`U'),
	(39452, 'Sansone', 'Caunce', 'scaunce8@phpbb.com', 'kP0+<''?o9*REWf'),
	(73738, 'Elaina', 'Willment', 'ewillment9@weebly.com', 'uD3%b\`a7"M'),
	(51685, 'Ania', 'Musso', 'amussoa@uiuc.edu', 'iJ0_4kVhR'),
	(62302, 'Chloette', 'Lope', 'clopeb@about.me', 'iF2#U5q%z1fn'),
	(63846, 'Loreen', 'Glynn', 'lglynnc@artisteer.com', 'lE8`*Rvxu{/''42'),
	(5654, 'Tammara', 'McGuirk', 'tmcguirkd@pagesperso-orange.fr', 'pJ1%BxVnU('),
	(51141, 'Raynell', 'Paris', 'rparise@earthlink.net', 'dY4\cj7kIE'),
	(88891, 'Cale', 'McMichell', 'cmcmichellf@indiatimes.com', 'tY4{)H(V'),
	(23245, 'Jedediah', 'Flook', 'jflookg@feedburner.com', 'tR9)(MPk'),
	(78790, 'Jorge', 'McCully', 'jmccullyh@theatlantic.com', 'vZ6/oqkLi|Rp?'),
	(42469, 'Ozzy', 'Peppard', 'opeppardi@fda.gov', 'yN1.IHWPMcz\GL'),
	(34684, 'Yves', 'Carr', 'ycarrj@springer.com', 'lU5`65m+b(FI');

truncate table users;

create type account_enum as enum ('CHECKING', 'SAVINGS');

create table accounts(
	account_id integer primary key check (account_id > 0),
	owner_id integer not null,
	balance numeric default 0.00,
	account_type account_enum default 'CHECKING',
	constraint fk_owner
		foreign key(owner_id)
		references users(user_id)
		on delete cascade
);

insert into accounts (account_id, owner_id, balance, account_type) values
	(9321, 7437, 1232.21, 'CHECKING'),
	(91043, 83986, 24532.90, 'SAVINGS'),
	(51642, 51468, 24532.90, 'CHECKING');

select * from accounts;

truncate table accounts;

create type transaction_enum as enum ('DEPOSIT', 'WITHDRAW');

create table transactions (
	transaction_id integer primary key check (transaction_id > 0),
	account_id integer not null,
	transaction_time timestamp,
	transaction_type transaction_enum,
	balance_change numeric not null,
	constraint fk_account
		foreign key(account_id)
		references accounts(account_id)
);

insert into transactions (transaction_id, account_id, transaction_time, transaction_type, balance_change) values (123462, 9321, '2020-06-22 19:10:25-07', 'DEPOSIT', 30.00);
select * from transactions;


update users set first_name = 'Billie', last_name = 'Eilish' where user_id =  5654;
select * from users;
select * from accounts;



delete from users where user_id = 7437;