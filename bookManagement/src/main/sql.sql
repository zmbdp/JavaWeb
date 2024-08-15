-- 这个文件用来创建库和表
-- 编写 SQL 完成建库建表操作.

create database if not exists book_management charset utf8mb4;

use book_management;

drop table if exists user_info;
drop table if exists book_info;

create table user_info (
    `user_id` int not null auto_increment,
    `user_name` varchar(128) not null,
    `password` varchar(128) not null,
    `delete_flag` tinyint(4) null default 0,
    `create_time` datetime default now(),
    `update_time` datetime default now() on update now(),
    primary key (`user_id`),
    unique index `user_name_UNIQUE` (`user_name` asc)
) engine = innodb default character set = utf8mb4 comment = '用户表';
create table `book_info` (
    `book_id` int(11) not null auto_increment,
    `book_name` varchar(127) not null,
    `author` varchar(127) not null,
    `count` int(11) not null,
    `price` decimal(7,2) not null,
    `publish` varchar(256) not null,
    `status` tinyint(4) default 1 comment '0-无效, 1-正常, 2-不允许借阅',
    `create_time` datetime default now(),
    `update_time` datetime default now() on update now(),
    primary key (`book_id`)
) engine = innodb default charset = utf8mb4;

-- 构造一些初始数据, 方便后续的测试。
-- 初始化数据
insert into user_info (user_name, password) values ("lisi", "654321");
insert into user_info (user_name, password) values ("zhangsan", "123456");
-- 初始化图书数据
insert into `book_info` (book_name, author, count, price, publish) values ('活着', '余华', 29, 22.00, '北京文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('平凡的世界', '路遥', 5, 98.56, '北京十月文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('三体', '刘慈欣', 9, 102.67, '重庆出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('金字塔原理', '麦肯锡', 16, 178.00, '民主与建设出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('仙剑奇侠传壹', '管平潮', 50, 59.99, '江苏文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('仙剑奇侠传贰', '管平潮', 8, 59.99, '中国文联出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('仙剑奇侠传叁', '管平潮', 42, 59.99, '中国文联出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·凡人篇', '忘语', 25, 29.99, '中国出版集团现代出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·魔道篇', '忘语', 54, 29.99, '中国出版集团现代出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·正魔篇', '忘语', 36, 29.99, '上海文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·妖魔篇', '忘语', 24, 29.99, '中国出版集团现代出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·逆劫篇', '忘语', 46, 29.99, '山东文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·天南篇', '忘语', 21, 29.99, '广东人民出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·云龙山篇', '忘语', 32, 29.99, '中国出版集团现代出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·乱星海篇', '忘语', 35, 29.99, '山东文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·魔界篇', '忘语', 59, 29.99, '江苏凤凰文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·灵界篇', '忘语', 95, 29.99, '中国出版集团现代出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·真仙界篇', '忘语', 19, 29.99, '浙江文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·仙界篇', '忘语', 6, 29.99, '新星出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·神界篇', '忘语', 16, 29.99, '安徽文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·界内界篇', '忘语', 29, 29.99, '浙江文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·界外篇', '忘语', 38, 29.99, '吉林出版集团');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·界主篇', '忘语', 49, 29.99, '浙江文艺出版社');
insert into `book_info` (book_name, author, count, price, publish) values ('凡人修仙传·大结局篇', '忘语', 41, 29.99, '四川文艺出版社');
