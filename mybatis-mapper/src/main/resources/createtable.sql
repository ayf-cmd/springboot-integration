-- ----------------------------
-- 设置编码
-- ----------------------------
SET NAMES 'utf8mb4';
-- ----------------------------
-- 创建数据库
-- ----------------------------
CREATE DATABASE mydb default character set utf8mb4 collate utf8mb4_unicode_ci;

-- ----------------------------
-- 使用数据库
-- ----------------------------
USE mydb;
-- ----------------------------

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(30) DEFAULT NULL,
  `order_money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1', 'order1', '100');
INSERT INTO `t_order` VALUES ('2', 'order2', '100');
INSERT INTO `t_order` VALUES ('3', 'order3', '100');
INSERT INTO `t_order` VALUES ('4', 'order4', '100');
