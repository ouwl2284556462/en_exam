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





