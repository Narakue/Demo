/*
 Navicat Premium Data Transfer

 Source Server         : zero
 Source Server Type    : MySQL
 Source Server Version : 50701
 Source Host           : localhost:3306
 Source Schema         : projectdemo

 Target Server Type    : MySQL
 Target Server Version : 50701
 File Encoding         : 65001

 Date: 27/11/2021 19:34:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_choose_subject
-- ----------------------------
DROP TABLE IF EXISTS `tbl_choose_subject`;
CREATE TABLE `tbl_choose_subject`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NULL DEFAULT NULL,
  `student_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `choose_time` datetime NULL DEFAULT NULL,
  `confirm_time` datetime NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `choose_student_id_fk`(`student_id`) USING BTREE,
  INDEX `choose_subject_id_fk`(`subject_id`) USING BTREE,
  CONSTRAINT `choose_student_id_fk` FOREIGN KEY (`student_id`) REFERENCES `tbl_student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `choose_subject_id_fk` FOREIGN KEY (`subject_id`) REFERENCES `tbl_subject` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_choose_subject
-- ----------------------------
INSERT INTO `tbl_choose_subject` VALUES (1, 1, '100', '2021-11-27 17:47:08', '2021-11-27 19:13:03', 3);
INSERT INTO `tbl_choose_subject` VALUES (2, 1, '123', '2021-11-27 19:23:58', NULL, 2);

-- ----------------------------
-- Table structure for tbl_file
-- ----------------------------
DROP TABLE IF EXISTS `tbl_file`;
CREATE TABLE `tbl_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_id`(`id`) USING BTREE,
  INDEX `file_teacher_id_fk`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_file
-- ----------------------------
INSERT INTO `tbl_file` VALUES (1, 't123', NULL, '2021-11-10 05:21:34', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211110\\0.020210918课堂复习404.docx');
INSERT INTO `tbl_file` VALUES (2, 't123', NULL, '2021-11-10 05:30:47', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211110\\0.020210918课堂复习404.docx');
INSERT INTO `tbl_file` VALUES (3, 't123', NULL, '2021-11-11 12:48:43', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211111\\0.020210918课堂复习404.docx');
INSERT INTO `tbl_file` VALUES (4, 't123', NULL, '2021-11-12 11:35:11', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\Project\\target\\classes\\files\\20211112\\0.00 .bat');
INSERT INTO `tbl_file` VALUES (5, NULL, '123', '2021-11-29 14:19:39', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\Project\\target\\classes\\files\\20211129\\zyz0 .bat');

-- ----------------------------
-- Table structure for tbl_student
-- ----------------------------
DROP TABLE IF EXISTS `tbl_student`;
CREATE TABLE `tbl_student`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` int(11) NULL DEFAULT NULL,
  `qq` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_student
-- ----------------------------
INSERT INTO `tbl_student` VALUES ('100', '123', 'zhb', '男', 110, 111, 3);
INSERT INTO `tbl_student` VALUES ('101', '123', 'ysy', '男', 111, 111, 1);
INSERT INTO `tbl_student` VALUES ('123', '123', 'zyz', '男', 110, 111, 2);

-- ----------------------------
-- Table structure for tbl_subject
-- ----------------------------
DROP TABLE IF EXISTS `tbl_subject`;
CREATE TABLE `tbl_subject`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `basic_requirement` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `research_objective` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reference` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `amount` int(11) NULL DEFAULT NULL,
  `selected` int(11) NULL DEFAULT NULL,
  `passed` int(11) NULL DEFAULT NULL,
  `teacher_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `subject_teacher_id_fk`(`teacher_id`) USING BTREE,
  CONSTRAINT `subject_teacher_id_fk` FOREIGN KEY (`teacher_id`) REFERENCES `tbl_teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_subject
-- ----------------------------
INSERT INTO `tbl_subject` VALUES (1, '题目1', '基本需求1', '这是一个课题需求', '参考文献1', 100, 1, 1, 't123', NULL, NULL);
INSERT INTO `tbl_subject` VALUES (2, '题目2', '基本需求2', '这是一个课题需求', '参考文献2', 10, 0, 0, 't123', 'url', NULL);
INSERT INTO `tbl_subject` VALUES (3, '题目3', '基本需求3', '这是一个课题需求', '参考文献3', 10, 0, 0, 't111', 'D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211111\\0.020210918课堂复习404.docx', NULL);

-- ----------------------------
-- Table structure for tbl_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tbl_teacher`;
CREATE TABLE `tbl_teacher`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` int(11) NULL DEFAULT NULL,
  `qq` int(11) NULL DEFAULT NULL,
  `amount` int(11) NULL DEFAULT NULL,
  `selected` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_teacher
-- ----------------------------
INSERT INTO `tbl_teacher` VALUES ('t111', '123', '教师2', NULL, NULL, 15, 0);
INSERT INTO `tbl_teacher` VALUES ('t123', '123', '教师1', NULL, NULL, 15, 2);

SET FOREIGN_KEY_CHECKS = 1;
