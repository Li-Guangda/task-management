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
drop table if exists `classroom`;
create table `classroom` (
    `classroom_id` bigint(20) auto_increment comment '班级id',
    `lecturer_id` bigint(20) not null comment '教师id',
    `class_name` varchar(50) not null comment '班级名称',
    `class_code` char(6) not null unique comment  '邀请码',
    primary key (`classroom_id`)
);

-- 用户表
drop table if exists `user`;
create table `user` (
    `user_id` bigint(20) auto_increment comment '用户id',
    `role` varchar(20) not null comment '角色',
    `username` varchar(32) not null unique comment '登录名(账号)',
    `password` varchar(256) not null comment 'bcrypt加密过的登陆密码',
    primary key (`user_id`)
);

-- 作业表
drop table if exists `task`;
create table `task` (
   `task_id` bigint(20) auto_increment comment '作业id',
   `classroom_id` bigint(20) not null comment '班级id',
   `task_title` varchar(20) not null comment '作业标题',
   `task_desc` varchar(256) not null comment '作业描述',
   `date_start` timestamp(0) not null comment '开始日期',
   `date_end` timestamp(0) not null comment '截止日期',
   primary key (`task_id`)
);

-- 已交作业学生登记表
drop table if exists `student_task`;
create table `student_task` (
    `task_id` bigint(20) comment '作业id',
    `student_id` bigint(20) comment '学生id',
    `remark` varchar(1024) comment '教师评语',
    `is_checked` boolean default false comment '教师是否批阅',
    primary key (`task_id`, `student_id`)
);

-- 选择题
drop table if exists `choice_question`;
create table `choice_question` (
    `choice_question_id` bigint(20) auto_increment comment '试题id',
    `task_id` bigint(20) not null comment '作业id',
    `sequence_number` smallint(4) not null comment '题目序号',
    `title` varchar(256) not null comment '题目',
    `type` tinyint(1) default 0 not null comment '试题类型, 0为单选, 1为多选',
    `score` tinyint(3) not null comment '分值',
    primary key (`choice_question_id`)
);

-- 选择题选项表
drop table if exists `question_option`;
create table `question_option` (
    `question_option_id` bigint(20) auto_increment comment '试题选项id',
    `choice_question_id` bigint(20) not null comment '试题id',
    `sequence_number` tinyint(3) not null comment '选项序号',
    `content` varchar(128) not null comment '选项内容',
    primary key (`question_option_id`)
);

-- 学生选择题作答表(已选择的选项记录)
drop table if exists `student_choice`;
create table `student_choice` (
    `student_id` bigint(20) comment '学生id',
    `question_option_id` bigint(20) comment '试题选项id',
    primary key (`student_id`,`question_option_id`)
);

-- 选择题参考答案表
drop table if exists `choice_question_answer`;
create table `choice_question_answer` (
    `choice_question_id` bigint(20) comment '试题id',
    `question_option_id` bigint(20) comment '正确的试题选项id',
    primary key (`choice_question_id`, `question_option_id`)
);

-- 简答题
drop table if exists `short_answer_question`;
create table `short_answer_question` (
    `short_answer_question_id` bigint(20) auto_increment comment '简答题id',
    `task_id` bigint(20) not null comment '作业id',
    `sequence_number` tinyint(3) not null comment '题目序号',
    `title` varchar(256) not null comment '题目',
    `score` tinyint(3) not null comment  '分值',
    primary key (`short_answer_question_id`)
);

-- 学生简答题作答表
drop table if exists `student_short_answer`;
create table `student_short_answer` (
    `short_answer_question_id` bigint(20) comment '简答题id',
    `student_id` bigint(20) comment '学生id',
    `answer` varchar(1024) comment '简答内容',
    `score` tinyint(3) default 0 comment '得分',
    primary key (`short_answer_question_id`, `student_id`)
);

-- 公告表
drop table if exists `notice`;
create table `notice` (
    `notice_id` bigint(20) auto_increment comment '公告id',
    `classroom_id` bigint(20) not null comment '班级id',
    `content` varchar(4096) not null comment '公告内容',
    `date` timestamp(0) default current_timestamp(0) comment  '发布日期',
    primary key (`notice_id`)
);

-- 评论表
drop table if exists `comment`;
create table `comment` (
    `comment_id` bigint(20) auto_increment comment '评论id',
    `classroom_id` bigint(20) not null comment '班级id',
    `user_id` bigint(20) not null comment '用户id',
    `content` varchar(1024) not null comment '评论内容',
    `date` timestamp(0) default current_timestamp(0) comment '发表日期',
    primary key (`comment_id`)
);

-- 教师信息表
drop table if exists `lecturer_info`;
create table `lecturer_info` (
    `lecturer_id` bigint(20) comment '教师id',
    `university` varchar(50) default '' comment '院校',
    `position` varchar(30) default '' comment '职位',
    `avatar` varchar(256) default '' comment '头像存储位置',
    `name` varchar(50) default '' comment '姓名',
    `gender` char(1) default '' comment '性别',
    `intro` varchar(256) default '' comment '简介',
    primary key (`lecturer_id`)
);

-- 学生信息表
drop table if exists `student_info`;
create table `student_info` (
    `student_id` bigint(20) comment '学生id',
    `university` varchar(50) default '' comment '院校',
    `student_number` varchar(20) default '' comment '学号',
    `avatar` varchar(256) default '' comment '头像存储位置',
    `name` varchar(50) default '' comment '姓名',
    `gender` char(1) default '' comment '性别',
    `intro` varchar(256) default '' comment '简介',
    primary key (`student_id`)
);

-- 班级学生表
drop table if exists `classroom_student`;
create table `classroom_student` (
    `classroom_id` bigint(20) comment '班级id',
    `student_id` bigint(20) comment '学生id',
    `joined` boolean default false comment '已加入为true，待加入为false',
    primary key (`classroom_id`, `student_id`)
);