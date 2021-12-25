/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql-3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 26/03/2021 23:25:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `occupation` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职业',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '教主', 1, '1999-04-16', '13093913252', '上单', '这个人很懒，什么都没有留下');
INSERT INTO `customer` VALUES (2, '测试用户一', 2, '2021-03-26', '13093913252', '上单', NULL);
INSERT INTO `customer` VALUES (3, '测试用户二', 1, '2021-03-26', '13093913252', NULL, '测试用户二（未设置职业）');
INSERT INTO `customer` VALUES (4, '测试用户三', 1, '2021-03-26', NULL, '上单', '测试用户三（未设置手机号）');
INSERT INTO `customer` VALUES (5, '测试用户四', 1, NULL, '13093913252', '上单', '测试用户四（未设置出生日期）');
INSERT INTO `customer` VALUES (6, '测试用户五', NULL, '2021-03-26', '13093913252', '上单', '测试用户五（未设置性别）');

SET FOREIGN_KEY_CHECKS = 1;
