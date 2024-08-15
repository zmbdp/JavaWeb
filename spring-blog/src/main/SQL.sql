create database if not exists spring_blog charset utf8mb4;
use spring_blog;
-- 用户表
drop table if exists user_info;
create table user_info (
    `id` int not null auto_increment,
    `user_name` varchar(128) not null,
    `password` varchar(128) not null,
    `github_url` varchar(128) null,
    `delete_flag` tinyint(4) null default 0,
    `create_time` datetime default now(),
    `update_time` datetime default now() on update now(),
    primary key (`id`),
    unique index user_name_unique (`user_name` asc)
) engine = innodb default character set = utf8mb4 comment = '用户表';

drop table if exists blog_info;
-- 博客表
create table blog_info (
    `id` int not null auto_increment,
    `title` varchar(200) null,
    `content` text null,
    `user_id` int(11) null,
    `delete_flag` tinyint(4) null default 0,
    `create_time` datetime default now(),
    `update_time` datetime default now() on update now(),
    primary key (`id`)
) engine = innodb default charset = utf8mb4 comment = '博客表';

-- 新增用户信息
insert into user_info (`user_name`, `password`, `github_url`) values("稚名不带撇", "d5965f63e48140cf9f8128f6ba24c82ee10adc3949ba59abbe56e057f20f883e", "https://github.com/zmbdp");
insert into user_info (`user_name`, `password`, `github_url`) values("zhangsan", "75551cffd7394898b12207b8074485b7e10adc3949ba59abbe56e057f20f883e", "https://gitee.com/zmbdp");
insert into user_info (`user_name`, `password`, `github_url`) values("lisi", "b556a9a082bd436194ce570b7c7b3dbbe10adc3949ba59abbe56e057f20f883e", "https://github.com/zmbdp");

-- 插入博客信息
insert into blog_info (`title`, `content`, `user_id`) values("第一篇博客", "这是我写的第一篇博客，有点害羞。", 1);
insert into blog_info (`title`, `content`, `user_id`) values("第二篇博客", "这是我写的第二篇博客，有点点害羞。", 2);
insert into blog_info (`title`, `content`, `user_id`) values("第三篇博客", "这是我写的第三篇博客，有点不害羞。", 2);
