/*
SQLyog Ultimate v8.32 
MySQL - 5.1.28-rc-community : Database - starcarpooling
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`starcarpooling` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `starcarpooling`;

/*Table structure for table `carpooling_user` */

DROP TABLE IF EXISTS `carpooling_user`;

CREATE TABLE `carpooling_user` (
  `Carpooling_id` bigint(70) NOT NULL,
  `user_id` bigint(70) NOT NULL,
  `cu_status` int(10) DEFAULT '1' COMMENT '1表示正在拼车队伍,0表示退出拼车队伍',
  PRIMARY KEY (`Carpooling_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `carpooling_user` */

insert  into `carpooling_user`(`Carpooling_id`,`user_id`,`cu_status`) values (21,123456789,-1),(68,11,-1),(68,77,-1),(68,1234,1),(68,1414,1),(68,13996767513,1),(68,15123237716,1),(68,18883856739,-1),(72,13996767513,1),(73,985666,1),(73,13996767513,1),(76,141411,-1),(77,18883258911,-1),(79,18883258911,-1),(81,141411,1);

/*Table structure for table `s_carpooling` */

DROP TABLE IF EXISTS `s_carpooling`;

CREATE TABLE `s_carpooling` (
  `Carpooling_id` bigint(70) NOT NULL AUTO_INCREMENT COMMENT '拼车贴id，自增id',
  `user_id` bigint(70) DEFAULT NULL COMMENT '关联user表中的主键id',
  `Carpooling_origin` varchar(50) DEFAULT NULL COMMENT '出发地',
  `Carpooling_destination` varchar(50) DEFAULT NULL COMMENT '目的地',
  `Carpooling_Date` varchar(50) DEFAULT NULL COMMENT '最后出发时间',
  `Carpooling_distance` int(30) DEFAULT NULL COMMENT '两地距离',
  `Carpooling_way` varchar(30) DEFAULT NULL COMMENT '拼车方式',
  `Carpooling_count` int(10) DEFAULT NULL COMMENT '已有人数',
  `Carpooling_nearme` bigint(70) DEFAULT NULL COMMENT '发起人距离我多远',
  `Carpooling_content` varchar(100) DEFAULT NULL COMMENT '帖子备注',
  `Carpooling_status` int(10) DEFAULT '10' COMMENT '帖子状态,10表示正在找拼车伙伴，0表示已经过期',
  `Carpooling_longitude` double DEFAULT NULL COMMENT '拼车地点纬度',
  `Carpooling_latitude` double DEFAULT NULL COMMENT '拼车地点经度',
  PRIMARY KEY (`Carpooling_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

/*Data for the table `s_carpooling` */

insert  into `s_carpooling`(`Carpooling_id`,`user_id`,`Carpooling_origin`,`Carpooling_destination`,`Carpooling_Date`,`Carpooling_distance`,`Carpooling_way`,`Carpooling_count`,`Carpooling_nearme`,`Carpooling_content`,`Carpooling_status`,`Carpooling_longitude`,`Carpooling_latitude`) values (21,13996767513,'1分开','龙华','2015-10-16 17:07:12',46,'',2,NULL,NULL,10,32.1,23.6),(68,123456789,'我叫代小星','我是涛小星','2015-10-16 17:07:12',1000,'',1,NULL,NULL,10,32.1,23.6),(69,123456789,'破三王','形容','2015-10-16 17:07:12',12545,'',3,NULL,NULL,10,32.1,23.6),(70,13996767513,'1册了咯了了','空虚了','2015-10-16 17:07:12',5685,'',53655,NULL,NULL,10,32.1,23.6),(71,13996767513,'45468946','4946494','2015-10-16 17:07:12',4946494,'',4646464,NULL,NULL,10,32.1,23.6),(72,77,'hhbhjjxghb发挥好ghcj独孤皇后','感觉很','2015-10-16 17:07:12',2599,'',1,NULL,NULL,10,32.1,23.6),(73,985693584,'111','111','2015-10-16 17:07:12',111,'',1,NULL,NULL,10,32.1,23.6),(74,985666,'11139999','111','2015-10-16 17:07:12',111,'',1,NULL,NULL,10,32.1,23.6),(75,18883856739,'13685','5655','2015-10-16 17:07:12',266,'',566,NULL,NULL,100,32.1,23.6),(76,18883258911,'疯','先锋','2015-10-16 17:07:12',11,'',1,NULL,NULL,10,32.1,23.6),(77,141411,'刚刚','刚刚','2015-10-16 17:07:12',11,'',2,NULL,NULL,10,32.1,23.6),(78,18883258911,'懒了','先锋','2015-10-16 17:07:12',11,'',1,NULL,NULL,10,32.1,23.6),(79,141411,'刚刚天天','刚刚','2015-10-16 17:07:12',11,'',2,NULL,NULL,100,32.1,23.6),(80,18883258911,'懒了','先锋','2015-10-16 17:07:12',114,'',1,NULL,NULL,10,32.1,23.6),(81,18883258911,'懒了利民','先锋胡','2015-10-16 17:07:12',114,'',1,NULL,NULL,100,32.1,23.6),(82,141411,'炮哥','刚刚','2015-10-16 17:07:12',11,'',21,NULL,NULL,10,32.1,23.6);

/*Table structure for table `s_integral` */

DROP TABLE IF EXISTS `s_integral`;

CREATE TABLE `s_integral` (
  `integral_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '用户积分id主键，自增',
  `user_id` bigint(70) NOT NULL COMMENT '关联用户id',
  `integral_level` int(30) DEFAULT '1' COMMENT '用户等级',
  `integral_carpoolingcount` int(30) DEFAULT '0' COMMENT '用户拼车次数',
  `integral_initiatecount` int(30) DEFAULT '0' COMMENT '用户发起次数',
  `integral_goodpraisecount` int(30) DEFAULT '0' COMMENT '用户好评次数',
  PRIMARY KEY (`integral_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `s_integral` */

insert  into `s_integral`(`integral_id`,`user_id`,`integral_level`,`integral_carpoolingcount`,`integral_initiatecount`,`integral_goodpraisecount`) values (1,13996767513,1,1,1,1),(8,123456789,1,0,0,0),(9,11,1,0,0,0),(10,1414,1,0,0,0),(11,1234,1,0,0,0),(12,1458,1,0,0,0),(13,77,1,0,0,0),(14,985693584,1,0,0,0),(15,985666,1,0,0,0),(16,15123237716,1,0,0,0),(17,15123237716,1,0,0,0),(18,18883856739,1,0,0,0),(19,141411,1,0,0,0),(20,18883258911,1,0,0,0),(21,1888325891199,1,0,0,0);

/*Table structure for table `s_user` */

DROP TABLE IF EXISTS `s_user`;

CREATE TABLE `s_user` (
  `user_id` bigint(70) NOT NULL COMMENT '主键',
  `user_tel` bigint(70) DEFAULT NULL COMMENT '用户电话号码，与主键值一致',
  `user_password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `user_longitude` double DEFAULT NULL COMMENT '用户所在位置纬度',
  `user_latitude` double DEFAULT NULL COMMENT '用户所在位置经度',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `user_content` varchar(100) DEFAULT NULL COMMENT '用户自我介绍',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `s_user` */

insert  into `s_user`(`user_id`,`user_tel`,`user_password`,`user_longitude`,`user_latitude`,`user_name`,`user_content`) values (11,11,'123',12,11,'孙悟空','女神免费拼车'),(77,77,'123',12,77,'娜扎','女神免费拼车'),(1234,1234,'123',12,1234,'猪八戒','女神免费拼车'),(1414,1414,'123',12,1414,'唐僧','女神免费拼车'),(1458,1458,'12.0',12,1458,'感觉','女神免费拼车'),(141411,141411,'14',12,12,'x1','女神免费拼车'),(985666,985666,'12.0',12,985666,'老封','女神免费拼车'),(123456789,123456789,'123',12,123456789,'老封','女神免费拼车'),(985693584,985693584,'12.0',12,985693584,'老封','女神免费拼车'),(13996767513,13996767513,'yeyuan0110',41.501276,87.955825,'流浪的蛐蛐','女神免费拼车'),(15123237716,15123237716,'12.0',12,15123237716,'x2','女神免费拼车'),(18883258911,18883258911,'11',12,12,'s1','女神免费拼车'),(18883856739,18883856739,'123',12,12,'s2','女神免费拼车'),(1888325891199,1888325891199,'116',12,12,'3s','女神免费拼车');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
