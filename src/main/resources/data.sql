create table usertable(
    name varchar2(30) not null,
    id varchar2(30) primary key,
    pwd varchar2(30) not null,
    age number default(0) not null,
    gender varchar2(30) not null,
    email varchar2(30) not null,
    tel varchar2(30) not null,
    addr varchar2(100) not null,
    logtime date not null,
    role varchar2(20) not null
);
select * from usertable;
delete usertable where id='eun';
-- abcdefg9876 gil
commit;
drop table usertable purge;