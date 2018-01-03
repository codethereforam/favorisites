CREATE DATABASE `favorisites` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `sex` tinyint(3) unsigned DEFAULT NULL COMMENT '性别(0:女，1:男，2:不愿透露)',
  `locked` tinyint(3) unsigned DEFAULT NULL COMMENT '状态(0:正常,1:锁定)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='用户';