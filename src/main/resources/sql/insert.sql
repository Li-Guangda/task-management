/*
    Source Server Type      : MySQL
    Source Server Version   : 8.0.32
    Source Host: localhost  : 3306
    Source Schema           : my_db

    Target Server Type      : MySQL
    Target Server Version   : 8.0.32

    Date                    : 2023/3/2
 */

 -- 添加用户
insert into `user` (`user_id`, `role`, `username`, `password`) values(1, 'ROLE_ADMIN', 'root', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(2, 'ROLE_STUDENT', 'lgd', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(3, 'ROLE_STUDENT', 'onlyone', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(4, 'ROLE_STUDENT', 'jzh', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(5, 'ROLE_STUDENT', 'nobody', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(6, 'ROLE_STUDENT', 'ladygg', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(7, 'ROLE_LECTURER', 'obama', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(8, 'ROLE_LECTURER', 'WHOAMI', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');
insert into `user` (`user_id`, `role`, `username`, `password`) values(9, 'ROLE_LECTURER', 'MarryCarry', '{bcrypt}$2a$10$2yCbCkBZKck3PACzw.VuPuK/UUCA0cwLxhY5AOrVtKMesOBvq31za');

-- 添加班级
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(1, 7, 'class-1', 'hkamze');
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(2, 7, 'class-2', 'njxnsm');
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(3, 8, 'class-3', 'dmkmsj');
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(4, 8, 'class-4', 'mnauqn');
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(5, 9, 'class-5', 'oopndm');
insert into `classroom` (`classroom_id`, `lecturer_id`, `class_name`, `class_code`) values(6, 9, 'class-6', 'aiwexj');

-- 添加作业
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(1, 1, 'English-0302', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(2, 1, 'English-8920', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(3, 1, 'writing-03', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(4, 2, 'drawing-23', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(5, 2, 'math-029', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(6, 3, 'geometry-9238', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(7, 4, 'geometry-928', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(8, 4, 'grammar-993', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(9, 5, 'writing-9923', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(10, 6, 'reading-23923', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(11, 6, 'learning-3232', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
insert into `task` (`task_id`, `classroom_id`, `task_title`, `task_desc`, `date_start`, `date_end`) values(12, 6, 'art-329', 'nothing', '2023-03-02 20:39:00', '2023-05-01 23:59:59');
