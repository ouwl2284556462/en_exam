-- 创建数据库
create database en_exam;

--切换数据库
use en_exam;

--改变数据库编码
alter database en_exam character set utf8;

--创建账号表
create table account (
  id int not null auto_increment,
  account_name varchar(64) not null,
  password varchar(64) not null,
  primary key (id)
);

ALTER TABLE account ADD unique(account_name);

--创建用户表详细信息表
create table user_dtl(
	acct_id int not null,
	name varchar(64) not null,
	tel varchar(20) not null,
	identity_Num varchar(32) not null,
	identity_type varchar(2) not null,
	primary key (acct_id)
);

--字典表
create table sys_dict_item(
	group_id varchar(32) not null,
	item_id varchar(32) not null,
	item_name varchar(64) not null,
	sort int not null,
	description varchar(128),
	mark varchar(64)
);
ALTER TABLE sys_dict_item ADD PRIMARY KEY (group_id, item_id);

--插入证件类型信息
insert into sys_dict_item(group_id, item_id, item_name, sort) values('identity_type', '1', '中华人民共和国居民身份证', 1);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('identity_type', '2', '护照', 2);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('identity_type', '3', '香港身份证', 3);

--考试级别
insert into sys_dict_item(group_id, item_id, item_name, sort) values('exam_level', '1', '四级', 1);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('exam_level', '2', '六级', 2);

--国籍编码
insert into sys_dict_item(group_id, item_id, item_name, sort) values('country_code', '1', '中国', 1);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('country_code', '2', '美国', 2);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('country_code', '3', '日本', 3);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('country_code', '4', '俄罗斯', 4);

--性别
insert into sys_dict_item(group_id, item_id, item_name, sort) values('sex', '1', '男', 1);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('sex', '2', '女', 2);

--考点地区
insert into sys_dict_item(group_id, item_id, item_name, sort) values('exam_place_region', '1', '北京', 1);
insert into sys_dict_item(group_id, item_id, item_name, sort) values('exam_place_region', '2', '广东', 2);

--考试信息表
create table exam_info (
  id int not null auto_increment,
  exam_name varchar(32) not null,
  exam_level varchar(2) not null,
  apply_start_time datetime  not null,
  apply_end_time datetime  not null,
  exam_time datetime not null,
  create_time datetime not null,
  state varchar(2) not null,
  mark varchar(64),
  primary key (id)
);

--插入考试信息
insert into exam_info(exam_name, exam_level, apply_start_time, apply_end_time, exam_time, create_time, state)
value('2019年6月份四级英语考试', '1', '2019-04-10 00:00:00', '2019-05-10 00:00:00', '2019-06-10 13:00:00', now(), '1');

insert into exam_info(exam_name, exam_level, apply_start_time, apply_end_time, exam_time, create_time, state)
value('2019年6月份六级英语考试', '2', '2019-04-10 00:00:00', '2019-05-10 00:00:00', '2019-06-10 13:00:00', now(), '1');


--系统参数表
create table sys_param(
	param_id varchar(32) not null,
	param_val varchar(64) not null,
	mark varchar(64),
	primary key (param_id)
);


--考场信息
create table exam_place(
	id int not null auto_increment,
	region varchar(4) not null,
	name varchar(32) not null,
	address varchar(255) not null,
	traffic varchar(255) not null,
	mark varchar(64),
	primary key (id)
);

--插入考场信息
insert into exam_place(region, name, address, traffic) 
values('1', '北京大学', '北京市海淀区颐和园路5号', '从北京站出发,乘坐808(北宫门-石佛营),抵达北京大学. 约22.84公里');

insert into exam_place(region, name, address, traffic) 
values('1', '清华大学', '北京市海淀区清华大学', '乘坐运通110、355、355（支）、375(支)，小5、小71路，双清路站下车。');

insert into exam_place(region, name, address, traffic) 
values('2', '中山大学', '中山大学（广州校区东校区）', '地铁大学城北站D出口步行30分钟左右。');

--报考信息
create table exam_apply_info(
	id int not null auto_increment,
	code varchar(5) not null,
	exam_id int not null,
	place_id int not null,
	photo_url varchar(128) not null,
	name varchar(64) not null,
	name_spell varchar(64) not null,
	identity_Num varchar(32) not null,
	identity_type varchar(2) not null,
	sex varchar(2) not null,
	country varchar(3) not null,
	birthday date not null,
	apply_time datetime not null,
	primary key (id, code)
);

alter table exam_apply_info AUTO_INCREMENT=100001;

--用户-报考信息关系表
create table user_exam_apply(
	user_id int not null,
	exam_apply_id int not null,
	primary key (user_id)
);




