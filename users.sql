drop table users;
drop sequence seq_users_no;

create table users(
             no number,
             id VARCHAR(20) unique not null,
             password VARCHAR2(20) not null,
             name VARCHAR2(20),
             gender VARCHAR2(10),
             primary key(no)
);

create sequence seq_users_no
increment by 1
start with 1
nocache;

insert into users
values(seq_users_no.nextval,'aaa','123','김영관','male');

select * from users;

delete from users;
