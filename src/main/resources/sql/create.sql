/*
    Source Server Type      : MySQL
    Source Server Version   : 8.0.32
    Source Host: localhost  : 3306
    Source Schema           : my_db

    Target Server Type      : MySQL
    Target Server Version   : 8.0.32

    Date                    : 2023/2/15
 */
set names utf8mb4;
set foreign_key_checks = 0;

-- 班级表
drop table if exists `class`;
create table `class` (
    `class_id` bigint(20) primary key auto_increment comment '班级id',
    `lecturer_id` bigint(20) not null unique comment '教师id',
    `class_name` varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '班级名称',
    `class_code` char(6) character set utf8mb4 collate utf8mb4_0900_ai_ci not null unique comment  '邀请码'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 用户表
drop table if exists `user`;
create table if not exists `user` (
    `user_id` bigint(20) primary key auto_increment comment '用户id',
    `role` varchar(20) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '角色',
    `username` varchar(32) character set utf8mb4 collate utf8mb4_0900_ai_ci not null unique comment '登录名(账号)',
    `password` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment 'bcrypt加密过的登陆密码'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 作业表
drop table if exists `task`;
create table if not exists `task` (
   `task_id` bigint(20) primary key auto_increment comment '作业id',
   `class_id` bigint(20) not null comment '班级id',
   `task_title` varchar(20) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '作业标题',
   `task_desc` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '作业描述',
   `date_start` timestamp(0) not null default current_timestamp(0) comment '开始日期',
   `date_end` timestamp(0) not null comment '截止日期'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 公告表
drop table if exists `notice`;
create table if not exists `notice` (
    `notice_id` bigint(20) primary key auto_increment comment '公告id',
    `class_id` bigint(20) not null unique comment '班级id',
    `content` text character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '公告内容',
    `date` timestamp(0) default current_timestamp(0) comment  '发布日期'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 评论表
drop table if exists `comment`;
create table if not exists `comment` (
    `comment_id` bigint(20) primary key auto_increment comment '评论id',
    `class_id` bigint(20) not null unique comment '班级id',
    `user_id` bigint(20) not null unique comment '用户id',
    `content` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '评论内容'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 学生作业表
drop table if exists `student_task`;
create table if not exists `student_task` (
    `student_id` bigint(20) comment '学生id',
    `task_id` bigint(20) comment '作业id',
    `score` tinyint(3) comment '评分',
    `remark` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '评语',
    `attachment` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '附件位置',
    `content` text character set utf8mb4 collate utf8mb4_0900_ai_ci comment '作业内容'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;


-- 教师信息表
drop table if exists `lecturer_info`;
create table  if not exists `lecturer_info` (
    `lecturer_id` bigint(20) primary key comment '讲师id',
    `university` varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '院校',
    `position` varchar(20) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '职位',
    `avatar` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci unique comment '头像存储位置',
    `name` varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '姓名',
    `gender` char(1) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '性别',
    `intro` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '简介'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 学生信息表
drop table if exists `student_info`;
create table  if not exists `student_info` (
    `student_id` bigint(20) primary key comment '学生id',
    `university` varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '院校',
    `student_number` varchar(20) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '学号',
    `avatar` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci unique comment '头像存储位置',
    `name` varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '姓名',
    `gender` char(1) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '性别',
    `intro` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '简介'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- 班级学生表
drop table if exists `class_student`;
create table if not exists `class_student` (
    `class_id` bigint(20) not null unique comment '班级id',
    `student_id` bigint(20) not null unique comment '学生id',
    `joined` char(1) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '已加入为是，待加入为否'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_0900_ai_ci;

-- sample