use blogs;
drop table if exists users;
CREATE TABLE users( #用户表
id INT NOT NULL,
user_name VARCHAR(25) NOT NULL, #用户名
pass_word VARCHAR(255) NOT NULL, #密码
avatar VARCHAR(255), #头像
priviledged BIT NOT NULL, #是否管理员
PRIMARY KEY (id)
);

drop table if exists articles;
CREATE TABLE articles( #文章表
id INT NOT NULL,
verified BIT NOT NULL, #是否已审核
selected BIT NOT NULL, #是否精华文章
time TIMESTAMP NOT NULL, #发表时间
category_id INT NOT NULL, #类别
title VARCHAR(255) NOT NULL, #标题
content LONGTEXT NOT NULL, #内容
descript VARCHAR(255) NOT NULL, #介绍
likes INT NOT NULL, #点赞数（冗余属性，可由like_items表统计得到）
stars INT NOT NULL, #收藏数（冗余属性，可由star_items表统计得到）
views INT NOT NULL,
comments INT NOT NULL, #评论数（冗余属性，可由comment_items表统计得到）
author_id INT NOT NULL, #评论用户id
PRIMARY KEY (id)
);

drop table if exists blog_label;
CREATE TABLE blog_label( #博客标签表
article_id INT NOT NULL, #标签所属文章id
label_id INT NOT NULL #标签id
);

drop table if EXISTS categories;
CREATE TABLE categories( #类别查询表
id INT NOT NULL, #编号
category_name VARCHAR(25) NOT NULL, #文本内容
PRIMARY KEY (id)
);
ALTER TABLE `categories` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

drop table if EXISTS labels;
CREATE TABLE labels( #标签查询表
id INT NOT NULL, #编号
label_name VARCHAR(25) NOT NULL, #文本内容
PRIMARY KEY (id)
);
ALTER TABLE `labels` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

drop table if exists like_items;
CREATE TABLE like_items( #点赞表
id INT NOT NULL, 
article_id INT NOT NULL, #被点赞文章id
user_id INT NOT NULL, #点赞用户id
PRIMARY KEY (id)
-- FOREIGN KEY (article_id) REFERENCES articles(id), #文章与用户存在多对多关系
-- FOREIGN KEY (user_id) REFERENCES users(id) #文章与用户存在多对多关系
);
ALTER TABLE `like_items` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

drop table if exists star_items;
CREATE TABLE star_items( #收藏表
id INT NOT NULL, 
article_id INT NOT NULL, #被收藏文章id
user_id INT NOT NULL, #收藏用户id
PRIMARY KEY (id)
-- FOREIGN KEY (article_id) REFERENCES articles(id), #文章与用户存在多对多关系
-- FOREIGN KEY (user_id) REFERENCES users(id) #文章与用户存在多对多关系
);
ALTER TABLE `star_items` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

drop table if exists comment_items;
CREATE TABLE comment_items( #评论表
id INT NOT NULL, 
article_id INT NOT NULL, #被评论文章id
user_id INT NOT NULL, #评论用户id
time TIMESTAMP NOT NULL, #评论发表时间
content VARCHAR(10000) NOT NULL,
PRIMARY KEY (id)
);

drop table if exists follow_items;
CREATE TABLE follow_items( #关注表
id INT NOT NULL, 
followed_id INT NOT NULL, #被关注用户id
follower_id INT NOT NULL, #关注用户id
PRIMARY KEY (id)
);
ALTER TABLE `follow_items` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

drop table if exists menu;
CREATE TABLE menu(
id INT NOT NULL,
name varchar(100) NOT NULL,
path varchar(100) NOT NULL,
component varchar(100) NOT NULL,
icon varchar(100) NOT NULL,
ordernum INT NOT NULL,
parent INT,
ishidden BIT NOT NULL,
type INT NOT NULL,
PRIMARY KEY (id)
);
ALTER TABLE `menu` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT;

