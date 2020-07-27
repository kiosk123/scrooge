--권한 테이블
create table T_AUTHORITY(
	NO int auto_increment primary key,
	ROLE varchar(30) unique		
);

-----------------------------------------------------------------------------------------------------------------

--권한 테이블 데이터 삽입
insert into T_AUTHORITY(ROLE) values('ROLE_ADMIN');
insert into T_AUTHORITY(ROLE) values('ROLE_USER');

--권한 테이블 데이터 확인
--select * from T_AUTHORITY;


-----------------------------------------------------------------------------------------------------------------

--유저 테이블 
create table T_USER(
	NO int auto_increment primary key,
	ID varchar(30) unique,
	PASSWORD varchar(100) not null,
	EMAIL varchar(50) unique,
	ENABLED boolean default 'true',
	AUTHORITY int default '0',
	foreign key(AUTHORITY) references T_AUTHORITY(NO)
);

--유저 테이블 데이터 삽입 --테스트 비밀번호는 둘다 123123123
insert into T_USER(ID,PASSWORD,email,ENABLED,AUTHORITY) 
values('niceguy123','$2a$11$B9Vbg5E8iaZJPBM0Z3qPcuTWYNqHtz39cU7HlaCcWNNc.HOs34UB2','gogothing001@yopmail.com','true','1');

insert into T_USER(ID,PASSWORD,email,ENABLED,AUTHORITY) 
values('gogothing001','$2a$11$B9Vbg5E8iaZJPBM0Z3qPcuTWYNqHtz39cU7HlaCcWNNc.HOs34UB2','gogothing002@yopmail.com','true','2');



--유저 테이블 데이터확인
--select * from T_USER;

-----------------------------------------------------------------------------------------------------------------


--시큐리티 쿼리 테스트
--select ID AS username,password,enabled from T_USER where id = 'niceguy123';
--select u.ID AS username, a.ROLE AS authority from T_USER u,T_AUTHORITY a where u.AUTHORITY=a.NO and u.ID='niceguy123';
--select username, authority from authorities where username = ?


-----------------------------------------------------------------------------------------------------------------

--카테고리 테이블
create table T_CATEGORY(
	NO int auto_increment primary key,
	NAME varchar(20) unique
);

--카테고리 테이블 데이터 삽입
insert into T_CATEGORY(NAME) values('지출');
insert into T_CATEGORY(NAME) values('수입');

--카테고리 테이블 데이터 확인
--select * from T_CATEGORY;


-----------------------------------------------------------------------------------------------------------------

--지출 항목 테이블 --일반사용자는 선택만 가능하고 운영자만 항목 수정및 삭제할 수 있음
create table T_SPEND(
	NO int primary key auto_increment,
	NAME varchar(20) unique	
);



--지출 항목 테이블 테이터 삽입
insert into T_SPEND(NAME) values('기타');
insert into T_SPEND(NAME) values('가공식품');
insert into T_SPEND(NAME) values('생식품');
insert into T_SPEND(NAME) values('생활용품');
insert into T_SPEND(NAME) values('전자제품');
insert into T_SPEND(NAME) values('보험');
insert into T_SPEND(NAME) values('외식');

--지출 항목 테이블 데이터 확인
--select * from T_SPEND;

-----------------------------------------------------------------------------------------------------------------
--수입 항목 테이블 --일반사용자는 선택만 가능하고 운영자만 항목 수정및 삭제할 수 있음
create table T_INCOME(
	NO int primary key auto_increment,
	NAME varchar(20) unique	
);



--지출 항목 테이블 테이터 삽입
insert into T_INCOME(NAME) values('기타');
insert into T_INCOME(NAME) values('아르바이트');
insert into T_INCOME(NAME) values('월급');
insert into T_INCOME(NAME) values('용돈');
insert into T_INCOME(NAME) values('복권');

--지출 항목 테이블 데이터 확인
--select * from T_INCOME;


-----------------------------------------------------------------------------------------------------------------

--가계부 테이블  -- 나중에 수입항목을 추가해서 관리하려면 INCOME 컬럼도 추가해야될 둣 지금은 필요없으니 일단 이렇게만...
create table T_HOUSEHOLD(
	NO bigint auto_increment primary key,
	MONEY int default '0',
	CONTENT varchar(30),
	CATEGORY int,
	REG_DATE date,
	USER int not null,
	SPEND int,
	INCOME int,
	foreign key(USER) references T_USER(NO) ON DELETE CASCADE,
	foreign key(SPEND) references T_SPEND(NO),
	foreign key(INCOME) references T_INCOME(NO)
);
--select * from T_HOUSEHOLD;
--delete from T_HOUSEHOLD;
--drop table T_HOUSEHOLD;

-----------------------------------------------------------------------------------------------------------------
--현재 사용하지 않는 테이블 혹시 몰라서 냅둠
create table T_AUTHENTICODE(
	NO int auto_increment primary key,
	SESSION_ID varchar(100) not null,
	CODE varchar(50) not null
);

--drop table T_AUTHENTICODE;
-----------------------------------------------------------------------------------------------------------------
--1번 지출 2번 수입--
--가계부 테이블에 테스트데이터 삽입 --나중에 동적쿼리 구현여부 고려 - SPEND 때문
insert into T_HOUSEHOLD(MONEY,CATEGORY,CONTENT,REG_DATE,USER,SPEND,INCOME) values('10000','1','새우젓갈',NOW(),'1','1','1');
insert into T_HOUSEHOLD(MONEY,CATEGORY,CONTENT,REG_DATE,USER,SPEND,INCOME) values('20000','1','불고기 1근',NOW(),'1','1','1');

insert into T_HOUSEHOLD(MONEY,CATEGORY,CONTENT,REG_DATE,USER,SPEND,INCOME) values('10000','2','도박',NOW(),'1','1','1');

----가계부 데이터 확인--1
--select * from T_HOUSEHOLD;
--delete from T_HOUSEHOLD;
--
----가계부 데이터 확인 --2(지출 - 차트확인을 위한 쿼리) --user year month 필요한 파라미터
--select s.NAME AS NAME, sum(t.MONEY) AS MONEY
--from T_HOUSEHOLD t, T_SPEND s
--where t.SPEND = s.NO and t.USER=1 and t.CATEGORY=1
--and year(REG_DATE)=2016 and month(REG_DATE)=12 group by NAME; 
--
--
----가계부 데이터 확인 --3(수입 - 차트확인을 위한 쿼리)
--select i.NAME AS NAME, sum(t.MONEY) AS MONEY
--from T_HOUSEHOLD t, T_INCOME i
--where t.INCOME = i.NO and t.USER=1 and t.CATEGORY=2
--and year(REG_DATE)=2016 and month(REG_DATE)=11 group by NAME; 
--
----수입 목록 데이터 가져오기 --EXCEL vo 수입목록, 지출목록, 수입합계, 지출합계 
--select 
--	i.NAME AS NAME, t.MONEY AS MONEY, CONCAT(month(t.REG_DATE),'월 ',day(t.REG_DATE),'일') AS DATE, t.CONTENT AS CONTENT
--from T_HOUSEHOLD t, T_INCOME i
--where t.INCOME = i.NO and t.USER=1 and t.CATEGORY=2
--and year(REG_DATE)=2016 and month(REG_DATE)=12 order by t.REG_DATE asc; 
--
----지출 목록 데이터 가져오기 --EXCEL vo 수입목록, 지출목록, 수입합계, 지출합계 
--select 
--	s.NAME AS NAME, t.MONEY AS MONEY, CONCAT(month(t.REG_DATE),'월 ',day(t.REG_DATE),'일') AS DATE, t.CONTENT AS CONTENT
--from T_HOUSEHOLD t, T_SPEND s
--where t.SPEND = s.NO and t.USER=1 and t.CATEGORY=1
--and year(REG_DATE)=2016 and month(REG_DATE)=12 order by t.REG_DATE asc; 

