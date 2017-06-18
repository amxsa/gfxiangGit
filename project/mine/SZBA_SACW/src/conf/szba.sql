--创建t_department表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_DEPARTMENT'; 
    if v_num > 0 then   
    execute immediate 'drop table t_department cascade constraints';   
    end if;
end;
/
create table t_department(
	id number(18,0) primary key,
	name varchar2(50) not null,
	parent_id number(18,0),	
	status char(1) default 'Y' not null,
	create_time  timestamp default sysdate not null,
	update_time timestamp default sysdate,
	levels number(10,0),
	priority number(10,0)
);
alter table t_department add(
	work_priority	number(10,0)
);
--创建t_account表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_ACCOUNT'; 
    if v_num > 0 then   
    execute immediate 'drop table t_account cascade constraints';   
    end if;
end;
/
create table t_account(
	account varchar2(20) primary key,
	workno varchar2(20),
	password varchar2(50) not null,
	status char(1) not null,
	mobile varchar2(20),
	name varchar2(30),
	gender char(1),
	id_type varchar2(20),
	id_num varchar2(30),
	department_id number(18,0),
	create_time timestamp default sysdate not null,
	update_time timestamp default sysdate,
	expire_time timestamp
);

--创建t_civilian表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_CIVILIAN'; 
    if v_num > 0 then   
    execute immediate 'drop table t_civilian cascade constraints';   
    end if;
end;
/
create table t_civilian(
	id number(18,0) primary key,
	name varchar2(60) not null,
	gender char(1),
	id_type varchar2(20),
	id_num varchar2(30),
	type varchar2(2),
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	record_id number(18,0),
	list_id number(18,0),
	elec_record_id number(18,0),
	valid_status char(1) default 'Y',
	priority number(10,0)
);
--创建t_civilian表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_CIVILIAN'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_civilian';   
    end if;
end;
/
create sequence seq_civilian;
--创建t_civilian表id自增触发器
create or replace TRIGGER trigger_civilian
before insert on t_civilian
referencing old as old new as new for each row
declare
begin
select seq_civilian.nextval into :new.id from dual;
end trigger_civilian;
/

--创建t_police表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_POLICE'; 
    if v_num > 0 then   
    execute immediate 'drop table t_police cascade constraints';   
    end if;
end;
/
create table t_police(
	id number(18,0) primary key,
	name varchar2(60) not null,
	department_id varchar2(18),
	gender char(1),
	id_type varchar2(20),
	id_num varchar2(30),
	phone varchar2(20),
	address varchar2(300),
	type varchar2(2),
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	account_id number(18,0),
	record_id number(18,0),
	elec_record_id number(18,0),
	list_id number(18,0),
	valid_status char(1) default 'Y',
	priority number(10,0)
);
--创建t_police表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_POLICE'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_police';   
    end if;
end;
/
create sequence seq_police;
--创建t_police表id自增触发器
create or replace TRIGGER trigger_police
before insert on t_police
referencing old as old new as new for each row
declare
begin
select seq_police.nextval into :new.id from dual;
end trigger_police;
/

--创建t_property表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_PROPERTY'; 
    if v_num > 0 then   
    execute immediate 'drop table t_property cascade constraints';   
    end if;
end;
/
create table t_property(
	id number(18,0) primary key,
	name varchar2(50) not null,
	quantity number(10,3) not null,
	unit varchar2(20),
	owner varchar2(30),
	phone varchar2(20),
	characteristic varchar2(200),
	position varchar2(100),
	method varchar2(100),
	place varchar2(100),
	department_id number(18,0),
	remark varchar2(1000),
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	list_id number(18,0),
	valid_status char(1) default 'Y',
	priority number(18,0)
);

alter table t_property add(
	save_demand	varchar2(200),
	save_method	varchar2(200),
	envi_demand	varchar2(200)
);
alter table t_property add(
	apprai_result clob,
	apprai_situation clob
);

--创建t_property表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_PROPERTY'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_property';   
    end if;
end;
/
create sequence seq_property;
--创建t_property表id自增触发器
create or replace TRIGGER trigger_property
before insert on t_property
referencing old as old new as new for each row
declare
begin
select seq_property.nextval into :new.id from dual;
end trigger_property;
/

--创建t_case表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_CASE'; 
    if v_num > 0 then   
    execute immediate 'drop table t_case cascade constraints';   
    end if;
end;
/
create table t_case(
	case_id varchar2(30) primary key,
	jzcase_id varchar2(100),
	case_type varchar2(2) not null,
	case_name varchar2(100) not null,
	suspect_name varchar2(100),
	case_place varchar2(100),
	register_date timestamp,
	case_status varchar2(2),
	case_nature varchar2(2),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y',
	creator varchar2(20) not null
);
alter table t_case add(
	department_id number(18,0)
);


--创建t_record表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_RECORD'; 
    if v_num > 0 then   
    execute immediate 'drop table t_record cascade constraints';   
    end if;
end;
/
create table t_record(
	record_id number(18,0) primary key,
	type varchar2(2) not null,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	start_time timestamp not null,
	end_time timestamp not null,
	target varchar2(100),
	reason varchar2(100),
	place varchar2(100),
	result CLOB,
	valid_status char(1) default 'Y',
	case_id varchar2(30)
);
--创建t_record表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_RECORD'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_record';   
    end if;
end;
/
create sequence seq_record;
--创建t_record表id自增触发器
create or replace TRIGGER trigger_record
before insert on t_record
referencing old as old new as new for each row
declare
begin
select seq_record.nextval into :new.record_id from dual;
end trigger_record;
/

--创建t_detail_list表
declare v_num number:=0;
begin 
	select count(*) into v_num from tab where tname = 'T_DETAIL_LIST';
	if v_num > 0 then
	execute immediate 'drop table t_detail_list cascade constraints';
	end if;
end;
/
create table t_detail_list(
	list_id number(18,0) primary key,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	type varchar2(2) not null,
	valid_status char(1) default 'Y',
	case_id varchar2(30),
	elec_record_id	number(18,0)
);
--创建t_detail_list表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_DETAIL_LIST'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_detail_list';   
    end if;
end;
/
create sequence seq_detail_list;
--创建t_detail_list表id自增触发器
create or replace TRIGGER trigger_detail_list
before insert on t_detail_list
referencing old as old new as new for each row
declare
begin
select seq_detail_list.nextval into :new.list_id from dual;
end trigger_detail_list;
/

--创建t_picture表
declare v_num number:=0;
begin 
	select count(*) into v_num from tab where tname = 'T_PICTURE';
	if v_num > 0 then
	execute immediate 'drop table t_picture cascade constraints';
	end if;
end;
/
create table t_picture(
	pic_id number(18,0) primary key,
	type varchar2(2) not null,
	thumbnail_url varchar2(100),
	original_url varchar2(100) not null,
	description varchar2(100),
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	case_id number(18,0),
	property_id number(18,0),
	priority number(10,0),
	elec_evidence_id number(18,0),
	is_display char(1) default 'Y'
);
--创建t_picture表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_PICTURE'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_picture';   
    end if;
end;
/
create sequence seq_picture;
--创建t_picture表id自增触发器
create or replace TRIGGER trigger_picture
before insert on t_picture
referencing old as old new as new for each row
declare
begin
select seq_picture.nextval into :new.pic_id from dual;
end trigger_picture;
/


--创建t_picture_temp表
declare v_num number:=0;
begin 
	select count(*) into v_num from tab where tname = 'T_PICTURE_TEMP';
	if v_num > 0 then
	execute immediate 'drop table t_picture_temp cascade constraints';
	end if;
end;
/
create table t_picture_temp(
	pic_id number(18,0) primary key,
	type varchar2(2) not null,
	thumbnail_url varchar2(100),
	original_url varchar2(100) not null,
	description varchar2(100),
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	case_id number(18,0),
	property_id number(18,0),
	priority number(10,0),
	elec_evidence_id number(18,0),
	is_display char(1) default 'Y'
);
--创建t_picture_temp表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_PICTURE_TEMP'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_picture_temp';   
    end if;
end;
/
create sequence seq_picture_temp;
--创建t_picture_temp表id自增触发器
create or replace TRIGGER trigger_picture_temp
before insert on t_picture_temp
referencing old as old new as new for each row
declare
begin
select seq_picture_temp.nextval into :new.pic_id from dual;
end trigger_picture_temp;
/

--创建t_elec_record表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_ELEC_RECORD'; 
    if v_num > 0 then   
    execute immediate 'drop table t_elec_record cascade constraints';   
    end if;
end;
/
create table t_elec_record(
	id number(18,0) primary key,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	start_time timestamp default sysdate not null,
	end_time timestamp not null,
	medium varchar2(60),
	reason varchar2(100),
	save_place varchar2(100),
	extract_place varchar2(100),
	result CLOB,
	valid_status char(1) default 'Y',
	case_id varchar2(30)
);
--创建t_elec_record表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_ELEC_RECORD'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_elec_record';   
    end if;
end;
/
create sequence seq_elec_record;
--创建t_elec_record表id自增触发器
create or replace TRIGGER trigger_elec_record
before insert on t_elec_record
referencing old as old new as new for each row
declare
begin
select seq_elec_record.nextval into :new.id from dual;
end trigger_elec_record;
/

--创建t_elec_evidence表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_ELEC_EVIDENCE'; 
    if v_num > 0 then   
    execute immediate 'drop table t_elec_evidence cascade constraints';   
    end if;
end;
/
create table t_elec_evidence(
	id number(18,0) primary key,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	name varchar2(60) not null,
	quantity number(10,3) not null,
	unit varchar2(20),
	characteristic varchar2(200),
	priority number(10,0),
	valid_status char(1) default 'Y',
	list_id number(18,0)
);
alter t_elec_evidence add(
	remark varchar2(1000),
);
--创建t_elec_evidence表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_ELEC_EVIDENCE'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_elec_evidence';   
    end if;
end;
/
create sequence seq_elec_evidence;
--创建t_elec_evidence表id自增触发器
create or replace TRIGGER trigger_elec_evidence
before insert on t_elec_evidence
referencing old as old new as new for each row
declare
begin
select seq_elec_evidence.nextval into :new.id from dual;
end trigger_elec_evidence;
/

--创建t_category表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_CATEGORY';
    if v_num > 0 then   
    execute immediate 'drop table t_category cascade constraints';   
    end if;
end;
/
create table t_category(
	id number(18,0) primary key,
	name varchar2(200) not null,
  save_demand varchar2(200),
  pack_demand varchar2(300),
  envi_demand varchar2(200),
  create_time timestamp default sysdate,
  update_time timestamp default sysdate,
  levels number(10,0) not null,
  parent_id number(18,0)
);
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_CATEGORY'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_category';   
    end if;
end;
/

--创建t_attachment表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_ATTACHMENT';
    if v_num > 0 then   
    execute immediate 'drop table t_attachment cascade constraints';   
    end if;
end;
/
create table t_attachment(
	id number(18,0) primary key,
  type varchar2(2) not null,
  upload_url varchar2(100) not null,
  create_time timestamp default sysdate,
  creator varchar2(20),
  case_id number(18,0),
  property_id number(18,0),
  elec_evidence_id number(18,0),
  priority number(10,0),
  valid_status char(1) default 'Y'
);
--创建t_attachment表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_ATTACHMENT'; 
    if v_num > 0 then   
      execute immediate 'drop sequence seq_attachment';   
    end if;
end;
/
create sequence seq_attachment;
--创建seq_attachment表id自增触发器
create or replace TRIGGER trigger_attachment
before insert on t_attachment
referencing old as old new as new for each row
declare
begin
select seq_attachment.nextval into :new.id from dual;
end trigger_attachment;
/

--创建t_warehouse表
CREATE TABLE [SZBA].[T_WAREHOUSE] ([ID]             NUMBER(18,0) NOT NULL,
                                   [SERIAL_NUMBER]  VARCHAR2(100 BYTE) NOT NULL,
                                   [NAME]           VARCHAR2(30 BYTE) NOT NULL,
                                   [DEPARTMENT_ID]  NUMBER(18,0) NOT NULL,
                                   [CATEGORY]       VARCHAR2(30 BYTE) NULL,
                                   [VOLUME]         VARCHAR2(30 BYTE) NULL,
                                   [GROSS_QUANTITY] VARCHAR2(30 BYTE) NULL,
                                   [EXIST_QUANTITY] VARCHAR2(30 BYTE) NULL,
                                   [BUILDING]       VARCHAR2(30 BYTE) NULL,
                                   [FLOOR]          VARCHAR2(30 BYTE) NULL,
                                   [ROOM]           VARCHAR2(30 BYTE) NULL,
                                   [CABINET]        VARCHAR2(30 BYTE) NULL,
                                   [ADDRESS]        VARCHAR2(200 BYTE) NULL,
                                   [STATUS]         CHAR(1 BYTE) NULL,
                                   [CREATE_TIME]    DATE NULL,
                                   [CREATOR]        VARCHAR2(20 BYTE) NULL,
                                   [VALID_STATUS]   CHAR(1 BYTE) NULL)
    PCTFREE 10 INITRANS 1 MAXTRANS 255
    TABLESPACE [USERS]
GO


CREATE OR REPLACE TRIGGER trigger_warehouse
before insert on t_warehouse
referencing old as old new as new for each row
declare
begin
select seq_warehouse.nextval into :new.id from dual;
end trigger_warehouse;
GO


--存储过程，将临时图片表中的数据存到正式图片表
create or replace procedure pic_temp_to_formal(picid IN t_picture.pic_id%type, descr IN t_picture.description%type)
as 
BEGIN
	update t_picture_temp set description = descr where pic_id = picid;
	
    insert into t_picture select * from t_picture_temp temp where temp.pic_id = picid;
   	
    DELETE from t_picture_temp where pic_id = picid;
END ;
--存储过程，获取插入图片（还未提交）的id
create or replace procedure pic_get_currval(picid OUT t_picture.pic_id%type, isFormal IN varchar2)
as 
BEGIN
  if(isFormal != 'Y') then
    select SEQ_PICTURE_TEMP.currval into picid from dual;
  else
     select SEQ_PICTURE.currval into picid from dual;
  end if;
END ;

--存储过程，登录验证
create or replace procedure sp_login(
    input_account IN VARCHAR2,
    intput_password IN VARCHAR2, 
    result OUT VARCHAR2, 
    key_logonInfo OUT VARCHAR2,
    resultset_logonInfo OUT SYS_REFCURSOR
)
IS
 selectCount NUMBER := 0;
 myAccount t_account%rowtype;
 temp_cur SYS_REFCURSOR;
BEGIN
  select count(*) into selectCount from t_account a left join t_department dept on a.department_id=dept.id  where a.account= input_account;
  
  if(selectCount > 0) then
    select a.* into myAccount from t_account a left join t_department dept on a.department_id=dept.id  where a.account= input_account;
    
    if (myAccount.status <> 'Y') then
      result := '账号状态失效';
    elsif (NLS_UPPER(myAccount.password) <> NLS_UPPER(intput_password)) then
      result := '密码错误';
    elsif (myAccount.expire_time < sysdate) then
      result := '账号已过期';
    else
      result := '';
      OPEN temp_cur FOR select a.*, dept.name from t_account a left join t_department dept on a.department_id=dept.id  where a.account= input_account;
      key_logonInfo := 'loginForm';
      resultset_logonInfo := temp_cur;
--      close temp_cur;
   end if;   
 else
   result := '账号不存在';
 end if; 
END;


--修改表的操作，给财物表添加字段
alter table t_property add(
  rfid_num varchar2(100),
  warehouse_id number(18,0),
  process_status varchar2(20), 
  seizure_basis varchar2(300),
  category_id number(18,3),
  status varchar2(30),
  nature varchar2(20),
  seizure_place varchar2(200),
  type varchar2(2),
  origin varchar2(30)
);

--修改财物表中的数量字段和添加单位字段
alter table t_property modify quantity null;
update t_property set quantity = '';
alter table t_property modify quantity number(10,3);
update t_property set quantity =3;
alter table t_property modify quantity not null;
alter table t_property add(unit varchar2(20));

--修改电子物证表中的数量字段和添加单位字段
alter table t_elec_evidence modify quantity null;
update t_elec_evidence set quantity = '';
alter table t_elec_evidence modify quantity number(10,3);
update t_elec_evidence set quantity =3;
alter table t_elec_evidence modify quantity not null;
alter table t_elec_evidence add(unit varchar2(20));

--修改表的操作，给普通民众表添加字段
alter table t_civilian add(
  phone varchar2(20),
  address varchar2(300)
);

--给案件表加字段
alter table t_case add(
	leader	varchar2(30)
);
alter table t_case add(
	occur_date	timestamp
);

--创建t_templet表
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_TEMPLET';
    if v_num > 0 then   
    execute immediate 'drop table t_templet cascade constraints';   
    end if;
end;
/
create table t_templet(
	id number(18,0) primary key,
 	name varchar2(30),
 	content clob,
 	type varchar2(2) not null,
 	is_default char(1),
 	account varchar2(20)
);
--创建t_templet表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_TEMPLET'; 
    if v_num > 0 then   
      execute immediate 'drop sequence seq_templet';   
    end if;
end;
/
create sequence seq_templet;
--创建seq_templet表id自增触发器
create or replace TRIGGER trigger_templet
before insert on t_templet
referencing old as old new as new for each row
declare
begin
select seq_templet.nextval into :new.id from dual;
end trigger_templet;
/

alter table t_templet add(
	CREATE_TIME timestamp default sysdate
);
alter table t_picture modify THUMBNAIL_URL varchar2(300);
alter table t_picture modify ORIGINAL_URL varchar2(300);
alter table t_attachment modify UPLOAD_URL varchar2(300);

alter table t_attachment add(
	name varchar2(100)
);

-- 创建t_message表
CREATE TABLE t_message(	
	"ID" NUMBER(18,0), 
	"TITLE" VARCHAR2(30 BYTE), 
	"CONTENT" VARCHAR2(500 BYTE), 
	"TYPE" VARCHAR2(2 BYTE), 
	"READSTATUS" CHAR(1 BYTE), 
	"LREADTIME" TIMESTAMP (6), 
	"CREATE_TIME" TIMESTAMP (6), 
	"IS_RELATIVE" CHAR(1 BYTE), 
	"ACCOUNT" VARCHAR2(50 BYTE), 
	"HREF" VARCHAR2(200 BYTE)
);
-- 创建t_message表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 'SEQ_MESSAGE'; 
    if v_num > 0 then   
    execute immediate 'drop sequence seq_message';   
    end if;
end;
/
create sequence seq_message;
--创建t_messag表id自增触发器
create or replace TRIGGER trigger_message
before insert on t_message
referencing old as old new as new for each row
declare
begin
select seq_message.nextval into :new.id from dual;
end trigger_message;
/

--财物表字段修改
alter table t_property modify type varchar2(10);

alter table t_attachment add(apply_id number(18,0));


--新建t_case_disposal（案结处置信息表）
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_CASE_DISPOSAL';
    if v_num > 0 then   
    execute immediate 'drop table t_case_disposal cascade constraints';   
    end if;
end;
/
create table t_case_disposal(
	id number(18,0) primary key,
  	disposal varchar2(10) not null,
  	disposal_person	varchar2(10) not null,
  	create_time timestamp default sysdate not null,
  	instruction varchar2(300),
  	pro_id number(18,0),
  	attach_id number(18,0),
  	valid_status char(1) default 'Y'
);
--创建t_case_disposal表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_CASE_DISPOSAL'; 
    if v_num > 0 then   
      execute immediate 'drop sequence seq_case_disposal';   
    end if;
end;
/
create sequence seq_case_disposal;
--创建seq_case_disposal表id自增触发器
create or replace TRIGGER trigger_case_disposal
before insert on t_case_disposal
referencing old as old new as new for each row
declare
begin
select seq_case_disposal.nextval into :new.id from dual;
end trigger_case_disposal;
/

alter table t_attachment(
	disposal_id number(18,0)
);

--添加清单和笔录的关联
alter table t_detail_list add(
	record_id number(18,0)
);

alter table t_detail_list add(
	transactor	varchar2(20)
);
alter table t_record add(
	transactor	varchar2(20)
);
alter table t_elec_record add(
	type varchar2(2) default '23'
);

alter table t_property modify status varchar2(30) default 'YDJ'

-------------------下面这部分的修改比较重要
alter table t_civilian drop column list_id;
alter table t_civilian drop column record_id;
alter table t_civilian drop column elec_record_id;
alter table t_civilian add(
	mix_id number(18,0),
	table_type varchar2(10)
	
);

alter table t_police drop column list_id;
alter table t_police drop column record_id;
alter table t_police drop column elec_record_id;
alter table T_police add(
	mix_id number(18,0),
	table_type varchar2(10)
	
);

alter table t_property drop column list_id;
alter table t_property add(
	mix_id number(18,0),
	table_type varchar2(10)
);

create table t_extract_record(
	id number(18,0) primary key,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	case_id varchar2(30),
	transactor varchar2(20),
	valid_status char(1) default 'Y'
);
------------------------------------
drop sequence seq_record;
drop trigger trigger_record;
drop sequence seq_elec_record;
drop trigger trigger_elec_record;
);


--财物和案件信息查询视图

CREATE OR REPLACE FORCE VIEW "SZBA"."VW_PROLIST" ("proId", "proName", "proQuantity", "proOwner", "proSeizureBasis", "proCharacteristic", "proCreateTime", "proStatus", "proType", "caseId", "jzcaseId", "caseName", "caseRegisterDate", "categoryId", "categoryName") 
AS 
  select pro.id, pro.name, pro.quantity,
	pro.owner,
	pro.seizure_basis, pro.characteristic,pro.create_time,
	pro.status, pro.type, case.case_id ,
	case.jzcase_id,
	case.case_name,case.register_date,
	category.id,  category.name 
from t_property pro 
join t_detail_list detail on pro.mix_id = detail.list_id  and pro.table_type = 'LIST' 
join t_record rec on rec.record_id = detail.record_id
left join t_case case on rec.case_id = case.case_id
left join t_category category on pro.category_id = category.id
where 1=1 and pro.valid_status = 'Y' and case.valid_status = 'Y' and detail.valid_status = 'Y'
union all
select pro.id, pro.name, pro.quantity,
	pro.owner,
	pro.seizure_basis, pro.characteristic,pro.create_time,
	pro.status, pro.type, case.case_id ,
	case.jzcase_id,
	case.case_name,case.register_date,
  category.id,  category.name
from t_property pro
join t_extract_record ext on ext.id = pro.mix_id and pro.table_type = 'EXTRAC'
left join t_case case on case.case_id = ext.case_id
left join t_category category on pro.category_id = category.id
where 1=1 and pro.valid_status = 'Y' and case.valid_status = 'Y' and ext.valid_status = 'Y';

alter table t_extract_record add(
	type varchar2(2) default '12'
);
alter table t_elec_record add(
	transactor	varchar2(20)
);
 
--历史笔录、登记表和电子物证查询的视图
create or replace view vw_history("historyID","startTime", "endTime", "recorder", "type", "transactor", "caseId")
as
select distinct rec.record_id , rec.start_time, rec.end_time, pol.name, rec.type, rec.transactor, rec.case_id
from t_record rec
left join t_police pol on pol.mix_id = rec.record_id and pol.table_type = 'REC' and pol.type = '16' and pol.valid_status = 'Y'
where rec.valid_status = 'Y' 
union all
select distinct ext.id, ext.create_time, ext.create_time, pol.name, ext.type, ext.transactor, ext.case_id
from T_EXTRACT_RECORD ext 
left join t_police pol on pol.mix_id = ext.id and pol.table_type = 'EXTRACT' and pol.type = '26' and pol.valid_status = 'Y'
where ext.valid_status = 'Y' 
union all
select distinct elec.id, elec.start_time, elec.end_time, pol.name, elec.type, elec.transactor, elec.case_id
from t_elec_record elec
left join t_police pol on pol.mix_id = elec.id and pol.table_type = 'ELEC' and pol.type = '16' and pol.valid_status = 'Y'
where elec.valid_status = 'Y';

--创建笔录、登记表和电子物证移交表 t_transfer
declare v_num number:= 0;  
begin  
    select count(*) into v_num from tab where tname = 'T_TRANSFER';
    if v_num > 0 then   
    execute immediate 'drop table t_transfer cascade constraints';   
    end if;
end;
/
create table t_transfer(
	id number(18,0) primary key,
	create_time timestamp default sysdate,
	creator varchar2(20) not null,
	transferee varchar2(20) not null,
	receiver varchar2(20) not null,
	transfer_id number(18,0) not null,
	type varchar2(2) not null,
  	transfer_status varchar2(10) default 'YJZ',
  	valid_status char(1) default 'Y'
);
alter table t_transfer add(
	reason varchar2(100)
);
--创建t_transfer表序列
declare v_num number:= 0;  
begin  
    select count(*) into v_num from user_sequences where sequence_name = 
'SEQ_TRANSFER'; 
    if v_num > 0 then   
      execute immediate 'drop sequence seq_transfer';   
    end if;
end;
/
create sequence seq_transfer;
--创建t_transfer表id自增触发器
create or replace TRIGGER trigger_transfer
before insert on t_transfer
referencing old as old new as new for each row
declare
begin
select seq_transfer.nextval into :new.id from dual;
end trigger_transfer;
/

exec sp_drop_table('t_storehouse');
create table t_storehouse(
	id number(18,0) primary key,
	storehouse_num varchar2(50) not null,
	storehouse_name varchar2(20 char),
	storehouse_type varchar2(5),
	remark varchar2(100 char),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y',
	dept_id number(18,0)
);
exec  sp_increment_auto('seq_storehouse', 'trigger_storehouse', 't_storehouse', 'id');

exec sp_drop_table('t_storeroom');
create table t_storeroom(
	id number(18,0) primary key,
	storeroom_num varchar2(50) not null,
	storeroom_name varchar2(20 char),
	storeroom_area number(10,3),
	storeroom_type varchar2(10),
	storeroom_addr varchar2(50 char),
	room_num varchar2(10),
	storehouse_id number(18,0),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y'
);
exec  sp_increment_auto('SEQ_STOREROOM', 'TRIGGER_STOREROOM', 'T_STOREROOM', 'id');

exec sp_drop_table('t_rack');
create table t_rack(
	id number(18,0) primary key,
	rack_num varchar2(100) not null,
	rack_height number(10,3),
	rack_length number(10,3),
	rack_load number(10,3),
	storeroom_id number(18,0),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y'
);
exec sp_increment_auto('SEQ_RACK', 'TRIGGER_RACK', 'T_RACK', 'id');

exec sp_drop_table('t_locker');
create table t_locker(
	id number(18,0) primary key,
	locker_num varchar2(100) not null,
	locker_volume number(10,3),
	locker_type varchar2(20 char),
	goods_num number(10,0),
	inventory_status varchar2(2) default 'U',
	rfid_num varchar2(100),
	rack_id number(18,0),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y'
);
exec sp_increment_auto('SEQ_LOCKER', 'TRIGGER_LOCKER', 'T_LOCKER', 'id');

exec sp_drop_table('t_storage_location');
create table t_storage_location(
	id number(18,0) primary key,
	location_num varchar2(100) not null,
	location_type varchar2(20 char),
	goods_num number(10,0),
	inventory_status varchar2(2) default 'U',
	rfid_num varchar2(100),
	storeroom_id number(18,0),
	create_time timestamp default sysdate,
	valid_status char(1) default 'Y'
);
exec sp_increment_auto('SEQ_STORAGE_LOCATION', 'TRIGGER_STORAGE_LOCATION', 'T_STORAGE_LOCATION', 'id');
      