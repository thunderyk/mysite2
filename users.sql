drop table users;
drop table board;
drop sequence seq_users_no;
drop sequence seq_board_no;
create table users(
             no number,
             id VARCHAR(20) unique not null,
             password VARCHAR2(20) not null,
             name VARCHAR2(20),
             gender VARCHAR2(10),
             primary key(no)
);
create table board(
             no number,
             title VARCHAR2(500) not null,
             content VARCHAR2(4000),
             hit number,
             reg_date date not null,
             user_no number not null,
             primary key(no),
constraint fk_user_no foreign key (user_no)
references users(no)
);


create sequence seq_users_no
increment by 1
start with 1
nocache;

create sequence seq_board_no
increment by 1
start with 1
nocache;

insert into users
values(seq_users_no.nextval,'aaa','123','김영관','male');

insert into board
values(seq_board_no.nextval,'하이','으아아아',0,sysdate,9);

select * from users;
select * from board;

delete from users;

select b.no, 
       title, 
       name,
       hit, 
       TO_CHAR(reg_date,'YYYY-MM-DD HH:MM:SS') reg_date 
from board b, users u
where b.user_no = u.no;