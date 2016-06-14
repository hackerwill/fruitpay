# ************************************************************
# Sequel Pro SQL dump
# Version 4529
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 139.162.2.196 (MySQL 5.5.44-0ubuntu0.14.04.1)
# Database: fruitpay_db_formal
# Generation Time: 2016-06-13 15:17:55 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE fruitpay_db;
CREATE DATABASE fruitpay_db;
alter database fruitpay_db
character set utf8;
USE fruitpay_db;

# Dump of table Customer
# ------------------------------------------------------------

CREATE TABLE `Customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '顧客ID',
  `fb_id` varchar(45) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL COMMENT '姓氏',
  `first_name` varchar(50) DEFAULT NULL COMMENT '名字',
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cellphone` varchar(20) DEFAULT NULL,
  `house_phone` varchar(20) DEFAULT NULL,
  `office_phone` varchar(20) DEFAULT NULL,
  `referenced_id` int(11) DEFAULT NULL COMMENT '推薦人ID',
  `password` varchar(50) NOT NULL,
  `birthday` date DEFAULT NULL,
  `register_from` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_Customer_Recommendation_idx` (`referenced_id`),
  KEY `fk_Customer_PostId_idx` (`post_id`),
  KEY `fk_Constant_RegisterFrom_idx` (`register_from`),
  KEY `FKbsjace6xgdgt0fgfhb6o0sx6f` (`create_id`),
  KEY `FKabdhuepc7qa63yvwp7vrp71fs` (`update_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Customer` (`customer_id`, `fb_id`, `last_name`, `first_name`, `gender`, `email`, `post_id`, `address`, `cellphone`, `house_phone`, `office_phone`, `referenced_id`, `password`, `birthday`, `register_from`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1223, '936990283004654', '徐', '瑋志', 'M', 'deviant604@hotmail.com', 2031, '永靖鄉同安村', '0933370691', '048238111', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', '1990-06-04', 34, '1990-01-01 00:00:00', '2016-05-01 20:21:37', NULL, 1223);


# Dump of table Constant
# ------------------------------------------------------------

CREATE TABLE `Constant` (
  `const_id` int(11) NOT NULL AUTO_INCREMENT,
  `const_name` varchar(50) NOT NULL,
  `const_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`const_id`),
  KEY `FKexrhplg11w6uagxin3m311lly` (`create_id`),
  KEY `FKj9beg213qp8ecpyvovj9031ml` (`update_id`),
  CONSTRAINT `FKexrhplg11w6uagxin3m311lly` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKj9beg213qp8ecpyvovj9031ml` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Constant` WRITE;
/*!40000 ALTER TABLE `Constant` DISABLE KEYS */;

INSERT INTO `Constant` (`const_id`, `const_name`, `const_desc`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'收件方式',NULL,NULL,NULL,NULL,NULL),
	(2,'配送時段',NULL,NULL,NULL,NULL,NULL),
	(3,'得知我們',NULL,NULL,NULL,NULL,NULL),
	(4,'發票資訊',NULL,NULL,NULL,NULL,NULL),
	(5,'性別',NULL,NULL,NULL,NULL,NULL),
	(6,'配送日',NULL,NULL,NULL,NULL,NULL),
	(7,'是否',NULL,NULL,NULL,NULL,NULL),
	(8,'折價券類型',NULL,NULL,NULL,NULL,NULL),
	(9,'商品數量',NULL,NULL,NULL,NULL,NULL),
	(10,'註冊來源',NULL,NULL,NULL,NULL,NULL),
	(11,'配送異動狀態',NULL,'2016-05-01 20:12:26','2016-05-01 20:12:26',NULL,NULL),
	(12,'系統角色',NULL,'2016-05-01 20:12:39','2016-05-01 20:12:39',NULL,NULL),
	(14,'取消暫停原因','','2016-05-08 00:00:00','2016-05-08 00:00:00',NULL,NULL),
	(15,'有效狀態',NULL,'2016-05-15 00:00:00','2016-05-15 00:00:00',NULL,NULL);

/*!40000 ALTER TABLE `Constant` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ConstantOption
# ------------------------------------------------------------

CREATE TABLE `ConstantOption` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT,
  `const_id` int(11) NOT NULL,
  `option_name` varchar(200) NOT NULL,
  `option_desc` varchar(255) DEFAULT NULL,
  `valid_flag` char(1) NOT NULL DEFAULT '1',
  `order_no` int(11) DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`option_id`),
  KEY `fk_Option_const_id_idx` (`const_id`),
  KEY `FKs67i65puet1oh65ybllbunufb` (`create_id`),
  KEY `FKjwtbtpu5emaerhip1vq3x7meg` (`update_id`),
  CONSTRAINT `FKjwtbtpu5emaerhip1vq3x7meg` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKkdgyif8vbnvuayotqpslqynei` FOREIGN KEY (`const_id`) REFERENCES `Constant` (`const_id`),
  CONSTRAINT `FKs67i65puet1oh65ybllbunufb` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_Option_ConstId` FOREIGN KEY (`const_id`) REFERENCES `Constant` (`const_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ConstantOption` WRITE;
/*!40000 ALTER TABLE `ConstantOption` DISABLE KEYS */;

INSERT INTO `ConstantOption` (`option_id`, `const_id`, `option_name`, `option_desc`, `valid_flag`, `order_no`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,1,'家裡(本人親收)','家裡(本人親收)','1',0,NULL,NULL,NULL,NULL),
	(2,1,'家裡(管理室代收)','家裡(管理室代收)','1',0,NULL,NULL,NULL,NULL),
	(3,1,'家裡(親友代收)','家裡(親友代收)','1',0,NULL,NULL,NULL,NULL),
	(4,1,'公司','公司','1',0,NULL,NULL,NULL,NULL),
	(5,2,'上午(9-12)','上午(9-12)','1',0,NULL,NULL,NULL,NULL),
	(6,2,'下午(12-17)','下午(12-17)','1',0,NULL,NULL,NULL,NULL),
	(7,2,'晚上(17-20)','晚上(17-20)','1',0,NULL,NULL,NULL,NULL),
	(8,3,'Facebook','Facebook','1',0,NULL,NULL,NULL,NULL),
	(9,3,'Google搜尋','Google搜尋','1',0,NULL,NULL,NULL,NULL),
	(10,3,'line','line','1',0,NULL,NULL,NULL,NULL),
	(11,3,'報章媒體','報章媒體','1',1,NULL,NULL,NULL,NULL),
	(12,4,'二聯式電子發票','二聯式電子發票','1',2,NULL,NULL,NULL,NULL),
	(13,4,'三聯式紙本發票','三聯式紙本發票','1',3,NULL,NULL,NULL,NULL),
	(14,3,'統一業務','統一業務','0',4,NULL,NULL,NULL,NULL),
	(15,6,'1','星期一','1',0,NULL,NULL,NULL,NULL),
	(16,6,'3','星期三','1',0,NULL,NULL,NULL,NULL),
	(17,7,'Y','是','1',0,NULL,NULL,NULL,NULL),
	(18,7,'N','否','1',0,NULL,NULL,NULL,NULL),
	(19,8,'periodByPercentage','依打折百分比%','1',0,NULL,NULL,NULL,NULL),
	(20,9,'1','1','1',0,NULL,NULL,NULL,NULL),
	(21,9,'2','2','1',0,NULL,NULL,NULL,NULL),
	(22,9,'3','3','1',0,NULL,NULL,NULL,NULL),
	(23,9,'4','4','1',0,NULL,NULL,NULL,NULL),
	(24,3,'其他','其他','1',6,NULL,NULL,NULL,NULL),
	(27,8,'periodFirstTimeByAmount','定期第一次依金額','1',0,NULL,NULL,NULL,NULL),
	(28,8,'periodFirstTimeByPercentage','定期第一次依打折數','1',0,NULL,NULL,NULL,NULL),
	(29,8,'periodByAmount','依金額','1',0,NULL,NULL,NULL,NULL),
	(30,3,'親友推薦','親友推薦','1',5,NULL,NULL,NULL,NULL),
	(31,6,'4','星期四','1',0,NULL,NULL,NULL,NULL),
	(32,6,'5','星期五','1',0,NULL,NULL,NULL,NULL),
	(33,10,'officialWebsite','官網','1',0,NULL,NULL,NULL,NULL),
	(34,10,'wordpress','Wordpress','1',0,NULL,NULL,NULL,NULL),
	(35,11,'shipmentPulse','暫停','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(36,11,'shipmentCancel','取消','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(37,11,'shipmentDeliver','需配送','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(38,11,'shipmentDelivered','已配送','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(39,12,'SYSTEM_MANAGER','系統管理員','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(40,12,'CUSTOMER','顧客','1',0,'2016-05-01 20:24:35','2016-05-01 20:24:35',1223,1223),
	(41,11,'shipmentReady','即將配送','1',0,'2016-05-04 00:00:00','2016-05-04 00:00:00',1223,1223),
	(42,14,'changeReasonGoOut','出國/出差','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(43,14,'changeReasonTooFew','份量太少','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(44,14,'changeReasonTooMany','份量太多','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(45,14,'changeReasonLowQuality','品質不好','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(46,14,'changeReasonNotSweet\n','水果不甜','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(47,14,'changeReasonShipmentInconvenient','收貨時間不方便','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(48,14,'changeReasonHighPrice','價格太高','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(49,14,'changeReasonDuplicated','水果太多重複','1',0,'2016-05-08 00:00:00','2016-05-08 00:00:00',1223,1223),
	(50,14,'changeReasonOneTime','只訂購一次','1',0,'2016-05-09 00:00:00','2016-05-09 00:00:00',1223,1223),
	(51,14,' changeReasonWantCosenByMyself','想自己挑選水果','1',0,'2016-05-09 00:00:00','2016-05-09 00:00:00',1223,1223),
	(52,15,'1','有效','1',0,'2016-05-15 00:00:00','2016-05-15 00:00:00',NULL,NULL),
	(53,15,'0','無效','1',0,'2016-05-15 00:00:00','2016-05-15 00:00:00',NULL,NULL),
	(54,11,'shipmentReturn','已退貨','1',0,'2016-06-13 20:50:35','2016-06-13 20:50:35',1223,1223);

/*!40000 ALTER TABLE `ConstantOption` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ShipmentPeriod
# ------------------------------------------------------------

CREATE TABLE `ShipmentPeriod` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT,
  `period_name` varchar(50) NOT NULL,
  `period_desc` varchar(255) DEFAULT NULL,
  `duration` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`period_id`),
  KEY `FK3pt7de0mrucxtfianopisl2k5` (`create_id`),
  KEY `FKjel6si1pn8d5efob5on9qo2u8` (`update_id`),
  CONSTRAINT `FK3pt7de0mrucxtfianopisl2k5` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKjel6si1pn8d5efob5on9qo2u8` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ShipmentPeriod` WRITE;
/*!40000 ALTER TABLE `ShipmentPeriod` DISABLE KEYS */;

INSERT INTO `ShipmentPeriod` (`period_id`, `period_name`, `period_desc`, `duration`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'每周配送','每周配送一次水果箱',7,NULL,NULL,NULL,NULL),
	(2,'隔周配送','兩周配送一次水果箱',14,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `ShipmentPeriod` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ShipmentDays
# ------------------------------------------------------------

CREATE TABLE `ShipmentDays` (
  `shipment_days_id` int(11) NOT NULL AUTO_INCREMENT,
  `shipment_days_name` varchar(50) NOT NULL,
  `shipment_days_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_days_id`),
  KEY `FKru58i6bxeuiwekh95k5cgwyb3` (`create_id`),
  KEY `FKlnqa3nja174yvfq4vxflr2k01` (`update_id`),
  CONSTRAINT `FKlnqa3nja174yvfq4vxflr2k01` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKru58i6bxeuiwekh95k5cgwyb3` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ShipmentDays` WRITE;
/*!40000 ALTER TABLE `ShipmentDays` DISABLE KEYS */;

INSERT INTO `ShipmentDays` (`shipment_days_id`, `shipment_days_name`, `shipment_days_desc`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'星期一',NULL,NULL,NULL,NULL,NULL),
	(2,'星期二',NULL,NULL,NULL,NULL,NULL),
	(3,'星期三',NULL,NULL,NULL,NULL,NULL),
	(4,'星期四',NULL,NULL,NULL,NULL,NULL),
	(5,'星期五',NULL,NULL,NULL,NULL,NULL),
	(6,'星期六',NULL,NULL,NULL,NULL,NULL),
	(7,'星期日',NULL,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `ShipmentDays` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ShipmentStatus
# ------------------------------------------------------------

CREATE TABLE `ShipmentStatus` (
  `shipment_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `shipment_status_name` varchar(50) NOT NULL,
  `shipment_status_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_status_id`),
  KEY `FKryx1fd44vx76k4sq76ud75r7v` (`create_id`),
  KEY `FKlj7dclsmegf7m87cuoxyon8vi` (`update_id`),
  CONSTRAINT `FKlj7dclsmegf7m87cuoxyon8vi` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKryx1fd44vx76k4sq76ud75r7v` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Unit
# ------------------------------------------------------------

CREATE TABLE `Unit` (
  `unit_id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(50) NOT NULL,
  `unit_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`unit_id`),
  KEY `FK2jd7h9wq8s92rlpawbq50nu9c` (`create_id`),
  KEY `FKpn3x38cx9nrm7slxey86a0vlk` (`update_id`),
  CONSTRAINT `FK2jd7h9wq8s92rlpawbq50nu9c` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKpn3x38cx9nrm7slxey86a0vlk` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Unit` WRITE;
/*!40000 ALTER TABLE `Unit` DISABLE KEYS */;

INSERT INTO `Unit` (`unit_id`, `unit_name`, `unit_desc`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'顆',NULL,NULL,NULL,NULL,NULL),
	(2,'盒',NULL,NULL,NULL,NULL,NULL),
	(3,'斤',NULL,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `Unit` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table OrderProgram
# ------------------------------------------------------------

CREATE TABLE `OrderProgram` (
  `program_id` int(11) NOT NULL AUTO_INCREMENT,
  `program_name` varchar(50) NOT NULL,
  `program_desc` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `img_link` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`program_id`),
  KEY `FKc2niq0c4flt1yb4jdw2x0qdlt` (`create_id`),
  KEY `FKmeugnbc1c7qfh91icjg1qcn70` (`update_id`),
  CONSTRAINT `FKc2niq0c4flt1yb4jdw2x0qdlt` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKmeugnbc1c7qfh91icjg1qcn70` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `OrderProgram` WRITE;
/*!40000 ALTER TABLE `OrderProgram` DISABLE KEYS */;

INSERT INTO `OrderProgram` (`program_id`, `program_name`, `program_desc`, `price`, `img_link`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'單人活力水果箱','4-5種新鮮水果<br> 客製化您的水果箱<br> 適用:小資女、單身、學生',499,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/08/34272516_ml.jpg?resize=200%2C200',NULL,NULL,NULL,NULL),
	(2,'家庭元氣水果箱',' 6-8種新鮮水果<br> 客製化您的水果箱<br> 適用:家庭主婦、上班族',699,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/07/25466674_ml.jpg?resize=200%2C200',NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `OrderProgram` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table OrderStatus
# ------------------------------------------------------------

CREATE TABLE `OrderStatus` (
  `order_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_status_name` varchar(50) NOT NULL,
  `order_status_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_status_id`),
  KEY `FKobddke54yjl2hvmamtdeeboch` (`create_id`),
  KEY `FK1a4bl3k76644btqea97gbf7uu` (`update_id`),
  CONSTRAINT `FK1a4bl3k76644btqea97gbf7uu` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKobddke54yjl2hvmamtdeeboch` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `OrderStatus` WRITE;
/*!40000 ALTER TABLE `OrderStatus` DISABLE KEYS */;

INSERT INTO `OrderStatus` (`order_status_id`, `order_status_name`, `order_status_desc`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'貨到付款下訂成功',NULL,NULL,NULL,NULL,NULL),
	(2,'已取消訂單',NULL,NULL,NULL,NULL,NULL),
	(3,'信用卡付款成功',NULL,NULL,NULL,NULL,NULL),
	(4,'信用卡付款失敗',NULL,NULL,NULL,NULL,NULL),
	(5,'已出貨',NULL,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `OrderStatus` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PaymentMode
# ------------------------------------------------------------

CREATE TABLE `PaymentMode` (
  `payment_mode_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_mode_name` varchar(50) NOT NULL,
  `payment_mode_desc` varchar(255) DEFAULT NULL,
  `payment_extra_price` int(11) NOT NULL DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`payment_mode_id`),
  KEY `FKf08oyqg5l2dwenx978wibq2nc` (`create_id`),
  KEY `FK86xkr5g9oa602qe8yeic0d9nd` (`update_id`),
  CONSTRAINT `FK86xkr5g9oa602qe8yeic0d9nd` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKf08oyqg5l2dwenx978wibq2nc` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `PaymentMode` WRITE;
/*!40000 ALTER TABLE `PaymentMode` DISABLE KEYS */;

INSERT INTO `PaymentMode` (`payment_mode_id`, `payment_mode_name`, `payment_mode_desc`, `payment_extra_price`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'信用卡付款','<b>每週/隔週</b>自動扣款，免綁約，隨時可以終止!<br>國內前三大線上刷卡付款，提供 256 bit 加密傳輸，3D 驗證，ssl 加密保障!',0,NULL,NULL,NULL,NULL),
	(2,'貨到付款','<b>每週/隔週</b>自動續約配送，到貨扣款，酌收30元手續費喔~!',30,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `PaymentMode` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PaymentStatus
# ------------------------------------------------------------

CREATE TABLE `PaymentStatus` (
  `payment_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_status_name` varchar(50) NOT NULL,
  `payment_status_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`payment_status_id`),
  KEY `FK795o01e3mvd93b502jyrukwdn` (`create_id`),
  KEY `FKdtec6fx1gp0oe5qh023q8t24v` (`update_id`),
  CONSTRAINT `FK795o01e3mvd93b502jyrukwdn` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKdtec6fx1gp0oe5qh023q8t24v` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table PostalCode
# ------------------------------------------------------------

CREATE TABLE `PostalCode` (
  `post_id` int(11) NOT NULL,
  `post_code` int(11) DEFAULT NULL,
  `county_name` varchar(45) NOT NULL,
  `towership_name` varchar(45) DEFAULT NULL,
  `allow_shipment` varchar(1) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FKp9l88kaixb6uo5pvnmdvv17u1` (`create_id`),
  KEY `FKahydus0vi5s7otva750dky9if` (`update_id`),
  CONSTRAINT `FKahydus0vi5s7otva750dky9if` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKp9l88kaixb6uo5pvnmdvv17u1` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `PostalCode` WRITE;
/*!40000 ALTER TABLE `PostalCode` DISABLE KEYS */;

INSERT INTO `PostalCode` (`post_id`, `post_code`, `county_name`, `towership_name`, `allow_shipment`, `full_name`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1001,100,'臺北市','中正區','Y','100 臺北市 中正區',NULL,NULL,NULL,NULL),
	(1031,103,'臺北市','大同區','Y','103 臺北市 大同區',NULL,NULL,NULL,NULL),
	(1041,104,'臺北市','中山區','Y','104 臺北市 中山區',NULL,NULL,NULL,NULL),
	(1051,105,'臺北市','松山區','Y','105 臺北市 松山區',NULL,NULL,NULL,NULL),
	(1061,106,'臺北市','大安區','Y','106 臺北市 大安區',NULL,NULL,NULL,NULL),
	(1081,108,'臺北市','萬華區','Y','108 臺北市 萬華區',NULL,NULL,NULL,NULL),
	(1101,110,'臺北市','信義區','Y','110 臺北市 信義區',NULL,NULL,NULL,NULL),
	(1111,111,'臺北市','士林區','Y','111 臺北市 士林區',NULL,NULL,NULL,NULL),
	(1121,112,'臺北市','北投區','Y','112 臺北市 北投區',NULL,NULL,NULL,NULL),
	(1141,114,'臺北市','內湖區','Y','114 臺北市 內湖區',NULL,NULL,NULL,NULL),
	(1151,115,'臺北市','南港區','Y','115 臺北市 南港區',NULL,NULL,NULL,NULL),
	(1161,116,'臺北市','文山區','Y','116 臺北市 文山區',NULL,NULL,NULL,NULL),
	(2001,200,'基隆市','仁愛區','Y','200 基隆市 仁愛區',NULL,NULL,NULL,NULL),
	(2011,201,'基隆市','信義區','Y','201 基隆市 信義區',NULL,NULL,NULL,NULL),
	(2021,202,'基隆市','中正區','Y','202 基隆市 中正區',NULL,NULL,NULL,NULL),
	(2031,203,'基隆市','中山區','Y','203 基隆市 中山區',NULL,NULL,NULL,NULL),
	(2041,204,'基隆市','安樂區','Y','204 基隆市 安樂區',NULL,NULL,NULL,NULL),
	(2051,205,'基隆市','暖暖區','Y','205 基隆市 暖暖區',NULL,NULL,NULL,NULL),
	(2061,206,'基隆市','七堵區','Y','206 基隆市 七堵區',NULL,NULL,NULL,NULL),
	(2071,207,'新北市','萬里區','Y','207 新北市 萬里區',NULL,NULL,NULL,NULL),
	(2081,208,'新北市','金山區','Y','208 新北市 金山區',NULL,NULL,NULL,NULL),
	(2091,209,'連江縣','南竿鄉','N','209 連江縣 南竿鄉',NULL,NULL,NULL,NULL),
	(2101,210,'連江縣','北竿鄉','N','210 連江縣 北竿鄉',NULL,NULL,NULL,NULL),
	(2111,211,'連江縣','莒光鄉','N','211 連江縣 莒光鄉',NULL,NULL,NULL,NULL),
	(2121,212,'連江縣','東引鄉','N','212 連江縣 東引鄉',NULL,NULL,NULL,NULL),
	(2201,220,'新北市','板橋區','Y','220 新北市 板橋區',NULL,NULL,NULL,NULL),
	(2211,221,'新北市','汐止區','Y','221 新北市 汐止區',NULL,NULL,NULL,NULL),
	(2221,222,'新北市','深坑區','Y','222 新北市 深坑區',NULL,NULL,NULL,NULL),
	(2231,223,'新北市','石碇區','Y','223 新北市 石碇區',NULL,NULL,NULL,NULL),
	(2241,224,'新北市','瑞芳區','Y','224 新北市 瑞芳區',NULL,NULL,NULL,NULL),
	(2261,226,'新北市','平溪區','Y','226 新北市 平溪區',NULL,NULL,NULL,NULL),
	(2271,227,'新北市','雙溪區','Y','227 新北市 雙溪區',NULL,NULL,NULL,NULL),
	(2281,228,'新北市','貢寮區','Y','228 新北市 貢寮區',NULL,NULL,NULL,NULL),
	(2311,231,'新北市','新店區','Y','231 新北市 新店區',NULL,NULL,NULL,NULL),
	(2321,232,'新北市','坪林區','Y','232 新北市 坪林區',NULL,NULL,NULL,NULL),
	(2331,233,'新北市','烏來區','Y','233 新北市 烏來區',NULL,NULL,NULL,NULL),
	(2341,234,'新北市','永和區','Y','234 新北市 永和區',NULL,NULL,NULL,NULL),
	(2351,235,'新北市','中和區','Y','235 新北市 中和區',NULL,NULL,NULL,NULL),
	(2361,236,'新北市','土城區','Y','236 新北市 土城區',NULL,NULL,NULL,NULL),
	(2371,237,'新北市','三峽區','Y','237 新北市 三峽區',NULL,NULL,NULL,NULL),
	(2381,238,'新北市','樹林區','Y','238 新北市 樹林區',NULL,NULL,NULL,NULL),
	(2391,239,'新北市','鶯歌區','Y','239 新北市 鶯歌區',NULL,NULL,NULL,NULL),
	(2411,241,'新北市','三重區','Y','241 新北市 三重區',NULL,NULL,NULL,NULL),
	(2421,242,'新北市','新莊區','Y','242 新北市 新莊區',NULL,NULL,NULL,NULL),
	(2431,243,'新北市','泰山區','Y','243 新北市 泰山區',NULL,NULL,NULL,NULL),
	(2441,244,'新北市','林口區','Y','244 新北市 林口區',NULL,NULL,NULL,NULL),
	(2471,247,'新北市','蘆洲區','Y','247 新北市 蘆洲區',NULL,NULL,NULL,NULL),
	(2481,248,'新北市','五股區','Y','248 新北市 五股區',NULL,NULL,NULL,NULL),
	(2491,249,'新北市','八里區','Y','249 新北市 八里區',NULL,NULL,NULL,NULL),
	(2511,251,'新北市','淡水區','Y','251 新北市 淡水區',NULL,NULL,NULL,NULL),
	(2521,252,'新北市','三芝區','Y','252 新北市 三芝區',NULL,NULL,NULL,NULL),
	(2531,253,'新北市','石門區','Y','253 新北市 石門區',NULL,NULL,NULL,NULL),
	(2601,260,'宜蘭縣','宜蘭市','Y','260 宜蘭縣 宜蘭市',NULL,NULL,NULL,NULL),
	(2611,261,'宜蘭縣','頭城鎮','Y','261 宜蘭縣 頭城鎮',NULL,NULL,NULL,NULL),
	(2621,262,'宜蘭縣','礁溪鄉','Y','262 宜蘭縣 礁溪鄉',NULL,NULL,NULL,NULL),
	(2631,263,'宜蘭縣','壯圍鄉','Y','263 宜蘭縣 壯圍鄉',NULL,NULL,NULL,NULL),
	(2641,264,'宜蘭縣','員山鄉','Y','264 宜蘭縣 員山鄉',NULL,NULL,NULL,NULL),
	(2651,265,'宜蘭縣','羅東鎮','Y','265 宜蘭縣 羅東鎮',NULL,NULL,NULL,NULL),
	(2661,266,'宜蘭縣','三星鄉','Y','266 宜蘭縣 三星鄉',NULL,NULL,NULL,NULL),
	(2671,267,'宜蘭縣','大同鄉','Y','267 宜蘭縣 大同鄉',NULL,NULL,NULL,NULL),
	(2681,268,'宜蘭縣','五結鄉','Y','268 宜蘭縣 五結鄉',NULL,NULL,NULL,NULL),
	(2691,269,'宜蘭縣','冬山鄉','Y','269 宜蘭縣 冬山鄉',NULL,NULL,NULL,NULL),
	(2701,270,'宜蘭縣','蘇澳鄉','Y','270 宜蘭縣 蘇澳鄉',NULL,NULL,NULL,NULL),
	(2721,272,'宜蘭縣','南澳鄉','Y','272 宜蘭縣 南澳鄉',NULL,NULL,NULL,NULL),
	(2901,290,'宜蘭縣','釣魚台列嶼','N','290 宜蘭縣 釣魚台列嶼',NULL,NULL,NULL,NULL),
	(3001,300,'新竹市','東區','Y','300 新竹市 東區',NULL,NULL,NULL,NULL),
	(3002,300,'新竹市','北區','Y','300 新竹市 北區',NULL,NULL,NULL,NULL),
	(3003,300,'新竹市','香山區','Y','300 新竹市 香山區',NULL,NULL,NULL,NULL),
	(3021,302,'新竹縣','竹北市','Y','302 新竹縣 竹北市',NULL,NULL,NULL,NULL),
	(3031,303,'新竹縣','湖口鄉','Y','303 新竹縣 湖口鄉',NULL,NULL,NULL,NULL),
	(3041,304,'新竹縣','新豐鄉','Y','304 新竹縣 新豐鄉',NULL,NULL,NULL,NULL),
	(3051,305,'新竹縣','新埔鄉','Y','305 新竹縣 新埔鄉',NULL,NULL,NULL,NULL),
	(3061,306,'新竹縣','關西鎮','Y','306 新竹縣 關西鎮',NULL,NULL,NULL,NULL),
	(3071,307,'新竹縣','芎林鄉','Y','307 新竹縣 芎林鄉',NULL,NULL,NULL,NULL),
	(3081,308,'新竹縣','寶山鄉','Y','308 新竹縣 寶山鄉',NULL,NULL,NULL,NULL),
	(3101,310,'新竹縣','竹東鎮','Y','310 新竹縣 竹東鎮',NULL,NULL,NULL,NULL),
	(3111,311,'新竹縣','五峰鄉','Y','311 新竹縣 五峰鄉',NULL,NULL,NULL,NULL),
	(3121,312,'新竹縣','橫山鄉','Y','312 新竹縣 橫山鄉',NULL,NULL,NULL,NULL),
	(3131,313,'新竹縣','尖石鄉','Y','313 新竹縣 尖石鄉',NULL,NULL,NULL,NULL),
	(3141,314,'新竹縣','北埔鄉','Y','314 新竹縣 北埔鄉',NULL,NULL,NULL,NULL),
	(3151,315,'新竹縣','峨眉鄉','Y','315 新竹縣 峨眉鄉',NULL,NULL,NULL,NULL),
	(3201,320,'桃園市','中壢區','Y','320 桃園市 中壢區',NULL,NULL,NULL,NULL),
	(3241,324,'桃園市','平鎮區','Y','324 桃園市 平鎮區',NULL,NULL,NULL,NULL),
	(3251,325,'桃園市','龍潭區','Y','325 桃園市 龍潭區',NULL,NULL,NULL,NULL),
	(3261,326,'桃園市','楊梅區','Y','326 桃園市 楊梅區',NULL,NULL,NULL,NULL),
	(3271,327,'桃園市','新屋區','Y','327 桃園市 新屋區',NULL,NULL,NULL,NULL),
	(3281,328,'桃園市','觀音區','Y','328 桃園市 觀音區',NULL,NULL,NULL,NULL),
	(3301,330,'桃園市','桃園區','Y','330 桃園市 桃園區',NULL,NULL,NULL,NULL),
	(3331,333,'桃園市','龜山區','Y','333 桃園市 龜山區',NULL,NULL,NULL,NULL),
	(3341,334,'桃園市','八德區','Y','334 桃園市 八德區',NULL,NULL,NULL,NULL),
	(3351,335,'桃園市','大溪區','Y','335 桃園市 大溪區',NULL,NULL,NULL,NULL),
	(3361,336,'桃園市','復興區','Y','336 桃園市 復興區',NULL,NULL,NULL,NULL),
	(3371,337,'桃園市','大園區','Y','337 桃園市 大園區',NULL,NULL,NULL,NULL),
	(3381,338,'桃園市','蘆竹區','Y','338 桃園市 蘆竹區',NULL,NULL,NULL,NULL),
	(3501,350,'苗栗縣','竹南鎮','Y','350 苗栗縣 竹南鎮',NULL,NULL,NULL,NULL),
	(3511,351,'苗栗縣','頭份市','Y','351 苗栗縣 頭份市',NULL,NULL,NULL,NULL),
	(3521,352,'苗栗縣','三灣鄉','Y','352 苗栗縣 三灣鄉',NULL,NULL,NULL,NULL),
	(3531,353,'苗栗縣','南庄鄉','Y','353 苗栗縣 南庄鄉',NULL,NULL,NULL,NULL),
	(3541,354,'苗栗縣','獅潭鄉','Y','354 苗栗縣 獅潭鄉',NULL,NULL,NULL,NULL),
	(3561,356,'苗栗縣','後龍鎮','Y','356 苗栗縣 後龍鎮',NULL,NULL,NULL,NULL),
	(3571,357,'苗栗縣','通霄鎮','Y','357 苗栗縣 通霄鎮',NULL,NULL,NULL,NULL),
	(3581,358,'苗栗縣','苑裡鎮','Y','358 苗栗縣 苑裡鎮',NULL,NULL,NULL,NULL),
	(3601,360,'苗栗縣','苗栗市','Y','360 苗栗縣 苗栗市',NULL,NULL,NULL,NULL),
	(3611,361,'苗栗縣','造橋鄉','Y','361 苗栗縣 造橋鄉',NULL,NULL,NULL,NULL),
	(3621,362,'苗栗縣','頭屋鄉','Y','362 苗栗縣 頭屋鄉',NULL,NULL,NULL,NULL),
	(3631,363,'苗栗縣','公館鄉','Y','363 苗栗縣 公館鄉',NULL,NULL,NULL,NULL),
	(3641,364,'苗栗縣','大湖鄉','Y','364 苗栗縣 大湖鄉',NULL,NULL,NULL,NULL),
	(3651,365,'苗栗縣','泰安鄉','Y','365 苗栗縣 泰安鄉',NULL,NULL,NULL,NULL),
	(3661,366,'苗栗縣','銅鑼鄉','Y','366 苗栗縣 銅鑼鄉',NULL,NULL,NULL,NULL),
	(3671,367,'苗栗縣','三義鄉','Y','367 苗栗縣 三義鄉',NULL,NULL,NULL,NULL),
	(3681,368,'苗栗縣','西湖鄉','Y','368 苗栗縣 西湖鄉',NULL,NULL,NULL,NULL),
	(3691,369,'苗栗縣','卓蘭鎮','Y','369 苗栗縣 卓蘭鎮',NULL,NULL,NULL,NULL),
	(4001,400,'臺中市','中區','Y','400 臺中市 中區',NULL,NULL,NULL,NULL),
	(4011,401,'臺中市','東區','Y','401 臺中市 東區',NULL,NULL,NULL,NULL),
	(4021,402,'臺中市','南區','Y','402 臺中市 南區',NULL,NULL,NULL,NULL),
	(4031,403,'臺中市','西區','Y','403 臺中市 西區',NULL,NULL,NULL,NULL),
	(4041,404,'臺中市','北區','Y','404 臺中市 北區',NULL,NULL,NULL,NULL),
	(4061,406,'臺中市','北屯區','Y','406 臺中市 北屯區',NULL,NULL,NULL,NULL),
	(4071,407,'臺中市','西屯區','Y','407 臺中市 西屯區',NULL,NULL,NULL,NULL),
	(4081,408,'臺中市','南屯區','Y','408 臺中市 南屯區',NULL,NULL,NULL,NULL),
	(4111,411,'臺中市','太平區','Y','411 臺中市 太平區',NULL,NULL,NULL,NULL),
	(4121,412,'臺中市','大里區','Y','412 臺中市 大里區',NULL,NULL,NULL,NULL),
	(4131,413,'臺中市','霧峰區','Y','413 臺中市 霧峰區',NULL,NULL,NULL,NULL),
	(4141,414,'臺中市','烏日區','Y','414 臺中市 烏日區',NULL,NULL,NULL,NULL),
	(4201,420,'臺中市','豐原區','Y','420 臺中市 豐原區',NULL,NULL,NULL,NULL),
	(4211,421,'臺中市','后里區','Y','421 臺中市 后里區',NULL,NULL,NULL,NULL),
	(4221,422,'臺中市','石岡區','Y','422 臺中市 石岡區',NULL,NULL,NULL,NULL),
	(4231,423,'臺中市','東勢區','Y','423 臺中市 東勢區',NULL,NULL,NULL,NULL),
	(4241,424,'臺中市','和平區','Y','424 臺中市 和平區',NULL,NULL,NULL,NULL),
	(4261,426,'臺中市','新社區','Y','426 臺中市 新社區',NULL,NULL,NULL,NULL),
	(4271,427,'臺中市','潭子區','Y','427 臺中市 潭子區',NULL,NULL,NULL,NULL),
	(4281,428,'臺中市','大雅區','Y','428 臺中市 大雅區',NULL,NULL,NULL,NULL),
	(4291,429,'臺中市','神岡區','Y','429 臺中市 神岡區',NULL,NULL,NULL,NULL),
	(4321,432,'臺中市','大肚區','Y','432 臺中市 大肚區',NULL,NULL,NULL,NULL),
	(4331,433,'臺中市','沙鹿區','Y','433 臺中市 沙鹿區',NULL,NULL,NULL,NULL),
	(4341,434,'臺中市','龍井區','Y','434 臺中市 龍井區',NULL,NULL,NULL,NULL),
	(4351,435,'臺中市','梧棲區','Y','435 臺中市 梧棲區',NULL,NULL,NULL,NULL),
	(4361,436,'臺中市','清水區','Y','436 臺中市 清水區',NULL,NULL,NULL,NULL),
	(4371,437,'臺中市','大甲區','Y','437 臺中市 大甲區',NULL,NULL,NULL,NULL),
	(4381,438,'臺中市','外埔區','Y','438 臺中市 外埔區',NULL,NULL,NULL,NULL),
	(4391,439,'臺中市','大安區','Y','439 臺中市 大安區',NULL,NULL,NULL,NULL),
	(5001,500,'彰化縣','彰化市','Y','500 彰化縣 彰化市',NULL,NULL,NULL,NULL),
	(5021,502,'彰化縣','芬園鄉','Y','502 彰化縣 芬園鄉',NULL,NULL,NULL,NULL),
	(5031,503,'彰化縣','花壇鄉','Y','503 彰化縣 花壇鄉',NULL,NULL,NULL,NULL),
	(5041,504,'彰化縣','秀水鄉','Y','504 彰化縣 秀水鄉',NULL,NULL,NULL,NULL),
	(5051,505,'彰化縣','鹿港鎮','Y','505 彰化縣 鹿港鎮',NULL,NULL,NULL,NULL),
	(5061,506,'彰化縣','福興鄉','Y','506 彰化縣 福興鄉',NULL,NULL,NULL,NULL),
	(5071,507,'彰化縣','線西鄉','Y','507 彰化縣 線西鄉',NULL,NULL,NULL,NULL),
	(5081,508,'彰化縣','和美鎮','Y','508 彰化縣 和美鎮',NULL,NULL,NULL,NULL),
	(5091,509,'彰化縣','伸港鄉','Y','509 彰化縣 伸港鄉',NULL,NULL,NULL,NULL),
	(5101,510,'彰化縣','員林市','Y','510 彰化縣 員林市',NULL,NULL,NULL,NULL),
	(5111,511,'彰化縣','社頭鄉','Y','511 彰化縣 社頭鄉',NULL,NULL,NULL,NULL),
	(5121,512,'彰化縣','永靖鄉','Y','512 彰化縣 永靖鄉',NULL,NULL,NULL,NULL),
	(5131,513,'彰化縣','埔心鄉','Y','513 彰化縣 埔心鄉',NULL,NULL,NULL,NULL),
	(5141,514,'彰化縣','溪湖鎮','Y','514 彰化縣 溪湖鎮',NULL,NULL,NULL,NULL),
	(5151,515,'彰化縣','大村鄉','Y','515 彰化縣 大村鄉',NULL,NULL,NULL,NULL),
	(5161,516,'彰化縣','埔鹽鄉','Y','516 彰化縣 埔鹽鄉',NULL,NULL,NULL,NULL),
	(5201,520,'彰化縣','田中鎮','Y','520 彰化縣 田中鎮',NULL,NULL,NULL,NULL),
	(5211,521,'彰化縣','北斗鎮','Y','521 彰化縣 北斗鎮',NULL,NULL,NULL,NULL),
	(5221,522,'彰化縣','田尾鄉','Y','522 彰化縣 田尾鄉',NULL,NULL,NULL,NULL),
	(5231,523,'彰化縣','埤頭鄉','Y','523 彰化縣 埤頭鄉',NULL,NULL,NULL,NULL),
	(5241,524,'彰化縣','溪州鄉','Y','524 彰化縣 溪州鄉',NULL,NULL,NULL,NULL),
	(5251,525,'彰化縣','竹塘鄉','Y','525 彰化縣 竹塘鄉',NULL,NULL,NULL,NULL),
	(5261,526,'彰化縣','二林鎮','Y','526 彰化縣 二林鎮',NULL,NULL,NULL,NULL),
	(5271,527,'彰化縣','大城鄉','Y','527 彰化縣 大城鄉',NULL,NULL,NULL,NULL),
	(5281,528,'彰化縣','芳苑鄉','Y','528 彰化縣 芳苑鄉',NULL,NULL,NULL,NULL),
	(5301,530,'彰化縣','二水鄉','Y','530 彰化縣 二水鄉',NULL,NULL,NULL,NULL),
	(5401,540,'南投縣','南投市','Y','540 南投縣 南投市',NULL,NULL,NULL,NULL),
	(5411,541,'南投縣','中寮鄉','Y','541 南投縣 中寮鄉',NULL,NULL,NULL,NULL),
	(5421,542,'南投縣','草屯鎮','Y','542 南投縣 草屯鎮',NULL,NULL,NULL,NULL),
	(5441,544,'南投縣','國姓鄉','Y','544 南投縣 國姓鄉',NULL,NULL,NULL,NULL),
	(5451,545,'南投縣','埔里鎮','Y','545 南投縣 埔里鎮',NULL,NULL,NULL,NULL),
	(5461,546,'南投縣','仁愛鄉','Y','546 南投縣 仁愛鄉',NULL,NULL,NULL,NULL),
	(5511,551,'南投縣','名間鄉','Y','551 南投縣 名間鄉',NULL,NULL,NULL,NULL),
	(5521,552,'南投縣','集集鎮','Y','552 南投縣 集集鎮',NULL,NULL,NULL,NULL),
	(5531,553,'南投縣','水里鄉','Y','553 南投縣 水里鄉',NULL,NULL,NULL,NULL),
	(5551,555,'南投縣','魚池鄉','Y','555 南投縣 魚池鄉',NULL,NULL,NULL,NULL),
	(5561,556,'南投縣','信義鄉','Y','556 南投縣 信義鄉',NULL,NULL,NULL,NULL),
	(5571,557,'南投縣','竹山鎮','Y','557 南投縣 竹山鎮',NULL,NULL,NULL,NULL),
	(5581,558,'南投縣','鹿谷鄉','Y','558 南投縣 鹿谷鄉',NULL,NULL,NULL,NULL),
	(6001,600,'嘉義市','東區','Y','600 嘉義市 東區',NULL,NULL,NULL,NULL),
	(6002,600,'嘉義市','西區','Y','600 嘉義市 西區',NULL,NULL,NULL,NULL),
	(6021,602,'嘉義縣','番路鄉','Y','602 嘉義縣 番路鄉',NULL,NULL,NULL,NULL),
	(6031,603,'嘉義縣','梅山鄉','Y','603 嘉義縣 梅山鄉',NULL,NULL,NULL,NULL),
	(6041,604,'嘉義縣','竹崎鄉','Y','604 嘉義縣 竹崎鄉',NULL,NULL,NULL,NULL),
	(6051,605,'嘉義縣','阿里山','Y','605 嘉義縣 阿里山',NULL,NULL,NULL,NULL),
	(6061,606,'嘉義縣','中埔鄉','Y','606 嘉義縣 中埔鄉',NULL,NULL,NULL,NULL),
	(6071,607,'嘉義縣','大埔鄉','Y','607 嘉義縣 大埔鄉',NULL,NULL,NULL,NULL),
	(6081,608,'嘉義縣','水上鄉','Y','608 嘉義縣 水上鄉',NULL,NULL,NULL,NULL),
	(6111,611,'嘉義縣','鹿草鄉','Y','611 嘉義縣 鹿草鄉',NULL,NULL,NULL,NULL),
	(6121,612,'嘉義縣','太保鄉','Y','612 嘉義縣 太保鄉',NULL,NULL,NULL,NULL),
	(6131,613,'嘉義縣','朴子鄉','Y','613 嘉義縣 朴子鄉',NULL,NULL,NULL,NULL),
	(6141,614,'嘉義縣','東石鄉','Y','614 嘉義縣 東石鄉',NULL,NULL,NULL,NULL),
	(6151,615,'嘉義縣','六腳鄉','Y','615 嘉義縣 六腳鄉',NULL,NULL,NULL,NULL),
	(6161,616,'嘉義縣','新港鄉','Y','616 嘉義縣 新港鄉',NULL,NULL,NULL,NULL),
	(6211,621,'嘉義縣','民雄鄉','Y','621 嘉義縣 民雄鄉',NULL,NULL,NULL,NULL),
	(6221,622,'嘉義縣','大林鎮','Y','622 嘉義縣 大林鎮',NULL,NULL,NULL,NULL),
	(6231,623,'嘉義縣','溪口鄉','Y','623 嘉義縣 溪口鄉',NULL,NULL,NULL,NULL),
	(6241,624,'嘉義縣','義竹鄉','Y','624 嘉義縣 義竹鄉',NULL,NULL,NULL,NULL),
	(6251,625,'嘉義縣','布袋鎮','Y','625 嘉義縣 布袋鎮',NULL,NULL,NULL,NULL),
	(6301,630,'雲林縣','斗南鎮','Y','630 雲林縣 斗南鎮',NULL,NULL,NULL,NULL),
	(6311,631,'雲林縣','大埤鄉','Y','631 雲林縣 大埤鄉',NULL,NULL,NULL,NULL),
	(6321,632,'雲林縣','虎尾鎮','Y','632 雲林縣 虎尾鎮',NULL,NULL,NULL,NULL),
	(6331,633,'雲林縣','土庫鎮','Y','633 雲林縣 土庫鎮',NULL,NULL,NULL,NULL),
	(6341,634,'雲林縣','褒忠鄉','Y','634 雲林縣 褒忠鄉',NULL,NULL,NULL,NULL),
	(6351,635,'雲林縣','東勢鄉','Y','635 雲林縣 東勢鄉',NULL,NULL,NULL,NULL),
	(6361,636,'雲林縣','臺西鄉','Y','636 雲林縣 臺西鄉',NULL,NULL,NULL,NULL),
	(6371,637,'雲林縣','崙背鄉','Y','637 雲林縣 崙背鄉',NULL,NULL,NULL,NULL),
	(6381,638,'雲林縣','麥寮鄉','Y','638 雲林縣 麥寮鄉',NULL,NULL,NULL,NULL),
	(6401,640,'雲林縣','斗六鄉','Y','640 雲林縣 斗六鄉',NULL,NULL,NULL,NULL),
	(6431,643,'雲林縣','林內鄉','Y','643 雲林縣 林內鄉',NULL,NULL,NULL,NULL),
	(6461,646,'雲林縣','古坑鄉','Y','646 雲林縣 古坑鄉',NULL,NULL,NULL,NULL),
	(6471,647,'雲林縣','莿桐鄉','Y','647 雲林縣 莿桐鄉',NULL,NULL,NULL,NULL),
	(6481,648,'雲林縣','西螺鎮','Y','648 雲林縣 西螺鎮',NULL,NULL,NULL,NULL),
	(6491,649,'雲林縣','二崙鄉','Y','649 雲林縣 二崙鄉',NULL,NULL,NULL,NULL),
	(6511,651,'雲林縣','北港鎮','Y','651 雲林縣 北港鎮',NULL,NULL,NULL,NULL),
	(6521,652,'雲林縣','水林鄉','Y','652 雲林縣 水林鄉',NULL,NULL,NULL,NULL),
	(6531,653,'雲林縣','口湖鄉','Y','653 雲林縣 口湖鄉',NULL,NULL,NULL,NULL),
	(6541,654,'雲林縣','四湖鄉','Y','654 雲林縣 四湖鄉',NULL,NULL,NULL,NULL),
	(6551,655,'雲林縣','元長鄉','Y','655 雲林縣 元長鄉',NULL,NULL,NULL,NULL),
	(7001,700,'臺南市','中西區','Y','700 臺南市 中西區',NULL,NULL,NULL,NULL),
	(7011,701,'臺南市','東區','Y','701 臺南市 東區',NULL,NULL,NULL,NULL),
	(7021,702,'臺南市','南區','Y','702 臺南市 南區',NULL,NULL,NULL,NULL),
	(7041,704,'臺南市','北區','Y','704 臺南市 北區',NULL,NULL,NULL,NULL),
	(7081,708,'臺南市','安平區','Y','708 臺南市 安平區',NULL,NULL,NULL,NULL),
	(7091,709,'臺南市','安南區','Y','709 臺南市 安南區',NULL,NULL,NULL,NULL),
	(7101,710,'臺南市','永康區','Y','710 臺南市 永康區',NULL,NULL,NULL,NULL),
	(7111,711,'臺南市','歸仁區','Y','711 臺南市 歸仁區',NULL,NULL,NULL,NULL),
	(7121,712,'臺南市','新化區','Y','712 臺南市 新化區',NULL,NULL,NULL,NULL),
	(7131,713,'臺南市','左鎮區','Y','713 臺南市 左鎮區',NULL,NULL,NULL,NULL),
	(7141,714,'臺南市','玉井區','Y','714 臺南市 玉井區',NULL,NULL,NULL,NULL),
	(7151,715,'臺南市','楠西區','Y','715 臺南市 楠西區',NULL,NULL,NULL,NULL),
	(7161,716,'臺南市','南化區','Y','716 臺南市 南化區',NULL,NULL,NULL,NULL),
	(7171,717,'臺南市','仁德區','Y','717 臺南市 仁德區',NULL,NULL,NULL,NULL),
	(7181,718,'臺南市','關廟區','Y','718 臺南市 關廟區',NULL,NULL,NULL,NULL),
	(7191,719,'臺南市','龍崎區','Y','719 臺南市 龍崎區',NULL,NULL,NULL,NULL),
	(7201,720,'臺南市','官田區','Y','720 臺南市 官田區',NULL,NULL,NULL,NULL),
	(7211,721,'臺南市','麻豆區','Y','721 臺南市 麻豆區',NULL,NULL,NULL,NULL),
	(7221,722,'臺南市','佳里區','Y','722 臺南市 佳里區',NULL,NULL,NULL,NULL),
	(7231,723,'臺南市','西港區','Y','723 臺南市 西港區',NULL,NULL,NULL,NULL),
	(7241,724,'臺南市','七股區','Y','724 臺南市 七股區',NULL,NULL,NULL,NULL),
	(7251,725,'臺南市','將軍區','Y','725 臺南市 將軍區',NULL,NULL,NULL,NULL),
	(7261,726,'臺南市','學甲區','Y','726 臺南市 學甲區',NULL,NULL,NULL,NULL),
	(7271,727,'臺南市','北門區','Y','727 臺南市 北門區',NULL,NULL,NULL,NULL),
	(7301,730,'臺南市','新營區','Y','730 臺南市 新營區',NULL,NULL,NULL,NULL),
	(7311,731,'臺南市','後壁區','Y','731 臺南市 後壁區',NULL,NULL,NULL,NULL),
	(7321,732,'臺南市','白河區','Y','732 臺南市 白河區',NULL,NULL,NULL,NULL),
	(7331,733,'臺南市','東山區','Y','733 臺南市 東山區',NULL,NULL,NULL,NULL),
	(7341,734,'臺南市','六甲區','Y','734 臺南市 六甲區',NULL,NULL,NULL,NULL),
	(7351,735,'臺南市','下營區','Y','735 臺南市 下營區',NULL,NULL,NULL,NULL),
	(7361,736,'臺南市','柳營區','Y','736 臺南市 柳營區',NULL,NULL,NULL,NULL),
	(7371,737,'臺南市','鹽水區','Y','737 臺南市 鹽水區',NULL,NULL,NULL,NULL),
	(7411,741,'臺南市','善化區','Y','741 臺南市 善化區',NULL,NULL,NULL,NULL),
	(7421,742,'臺南市','大內區','Y','742 臺南市 大內區',NULL,NULL,NULL,NULL),
	(7431,743,'臺南市','山上區','Y','743 臺南市 山上區',NULL,NULL,NULL,NULL),
	(7441,744,'臺南市','新市區','Y','744 臺南市 新市區',NULL,NULL,NULL,NULL),
	(7451,745,'臺南市','安定區','Y','745 臺南市 安定區',NULL,NULL,NULL,NULL),
	(8001,800,'高雄市','新興區','Y','800 高雄市 新興區',NULL,NULL,NULL,NULL),
	(8011,801,'高雄市','前金區','Y','801 高雄市 前金區',NULL,NULL,NULL,NULL),
	(8021,802,'高雄市','苓雅區','Y','802 高雄市 苓雅區',NULL,NULL,NULL,NULL),
	(8031,803,'高雄市','鹽埕區','Y','803 高雄市 鹽埕區',NULL,NULL,NULL,NULL),
	(8041,804,'高雄市','鼓山區','Y','804 高雄市 鼓山區',NULL,NULL,NULL,NULL),
	(8051,805,'高雄市','旗津區','Y','805 高雄市 旗津區',NULL,NULL,NULL,NULL),
	(8061,806,'高雄市','前鎮區','Y','806 高雄市 前鎮區',NULL,NULL,NULL,NULL),
	(8071,807,'高雄市','三民區','Y','807 高雄市 三民區',NULL,NULL,NULL,NULL),
	(8111,811,'高雄市','楠梓區','Y','811 高雄市 楠梓區',NULL,NULL,NULL,NULL),
	(8121,812,'高雄市','小港區','Y','812 高雄市 小港區',NULL,NULL,NULL,NULL),
	(8131,813,'高雄市','左營區','Y','813 高雄市 左營區',NULL,NULL,NULL,NULL),
	(8141,814,'高雄市','仁武區','Y','814 高雄市 仁武區',NULL,NULL,NULL,NULL),
	(8151,815,'高雄市','大社區','Y','815 高雄市 大社區',NULL,NULL,NULL,NULL),
	(8171,817,'南海諸島','東沙','N','817 南海諸島 東沙',NULL,NULL,NULL,NULL),
	(8191,819,'南海諸島','南沙','N','819 南海諸島 南沙',NULL,NULL,NULL,NULL),
	(8201,820,'高雄市','岡山區','Y','820 高雄市 岡山區',NULL,NULL,NULL,NULL),
	(8211,821,'高雄市','路竹區','Y','821 高雄市 路竹區',NULL,NULL,NULL,NULL),
	(8221,822,'高雄市','阿蓮區','Y','822 高雄市 阿蓮區',NULL,NULL,NULL,NULL),
	(8231,823,'高雄市','田寮區','Y','823 高雄市 田寮區',NULL,NULL,NULL,NULL),
	(8241,824,'高雄市','燕巢區','Y','824 高雄市 燕巢區',NULL,NULL,NULL,NULL),
	(8251,825,'高雄市','橋頭區','Y','825 高雄市 橋頭區',NULL,NULL,NULL,NULL),
	(8261,826,'高雄市','梓官區','Y','826 高雄市 梓官區',NULL,NULL,NULL,NULL),
	(8271,827,'高雄市','彌陀區','Y','827 高雄市 彌陀區',NULL,NULL,NULL,NULL),
	(8281,828,'高雄市','永安區','Y','828 高雄市 永安區',NULL,NULL,NULL,NULL),
	(8291,829,'高雄市','湖內區','Y','829 高雄市 湖內區',NULL,NULL,NULL,NULL),
	(8301,830,'高雄市','鳳山區','Y','830 高雄市 鳳山區',NULL,NULL,NULL,NULL),
	(8311,831,'高雄市','大寮區','Y','831 高雄市 大寮區',NULL,NULL,NULL,NULL),
	(8321,832,'高雄市','林園區','Y','832 高雄市 林園區',NULL,NULL,NULL,NULL),
	(8331,833,'高雄市','鳥松區','Y','833 高雄市 鳥松區',NULL,NULL,NULL,NULL),
	(8401,840,'高雄市','大樹區','Y','840 高雄市 大樹區',NULL,NULL,NULL,NULL),
	(8421,842,'高雄市','旗山區','Y','842 高雄市 旗山區',NULL,NULL,NULL,NULL),
	(8431,843,'高雄市','美濃區','Y','843 高雄市 美濃區',NULL,NULL,NULL,NULL),
	(8441,844,'高雄市','六龜區','Y','844 高雄市 六龜區',NULL,NULL,NULL,NULL),
	(8451,845,'高雄市','內門區','Y','845 高雄市 內門區',NULL,NULL,NULL,NULL),
	(8461,846,'高雄市','杉林區','Y','846 高雄市 杉林區',NULL,NULL,NULL,NULL),
	(8471,847,'高雄市','甲仙區','Y','847 高雄市 甲仙區',NULL,NULL,NULL,NULL),
	(8481,848,'高雄市','桃源區','Y','848 高雄市 桃源區',NULL,NULL,NULL,NULL),
	(8491,849,'高雄市','那瑪夏區','Y','849 高雄市 那瑪夏區',NULL,NULL,NULL,NULL),
	(8511,851,'高雄市','茂林區','Y','851 高雄市 茂林區',NULL,NULL,NULL,NULL),
	(8521,852,'高雄市','茄萣區','Y','852 高雄市 茄萣區',NULL,NULL,NULL,NULL),
	(8801,880,'澎湖縣','馬公市','N','880 澎湖縣 馬公市',NULL,NULL,NULL,NULL),
	(8811,881,'澎湖縣','西嶼鄉','N','881 澎湖縣 西嶼鄉',NULL,NULL,NULL,NULL),
	(8821,882,'澎湖縣','望安鄉','N','882 澎湖縣 望安鄉',NULL,NULL,NULL,NULL),
	(8831,883,'澎湖縣','七美鄉','N','883 澎湖縣 七美鄉',NULL,NULL,NULL,NULL),
	(8841,884,'澎湖縣','白沙鄉','N','884 澎湖縣 白沙鄉',NULL,NULL,NULL,NULL),
	(8851,885,'澎湖縣','湖西鄉','N','885 澎湖縣 湖西鄉',NULL,NULL,NULL,NULL),
	(8901,890,'金門縣','金沙鎮','N','890 金門縣 金沙鎮',NULL,NULL,NULL,NULL),
	(8911,891,'金門縣','金湖鎮','N','891 金門縣 金湖鎮',NULL,NULL,NULL,NULL),
	(8921,892,'金門縣','金寧鄉','N','892 金門縣 金寧鄉',NULL,NULL,NULL,NULL),
	(8931,893,'金門縣','金城鎮','N','893 金門縣 金城鎮',NULL,NULL,NULL,NULL),
	(8941,894,'金門縣','烈嶼鄉','N','894 金門縣 烈嶼鄉',NULL,NULL,NULL,NULL),
	(8961,896,'金門縣','烏坵鄉','N','896 金門縣 烏坵鄉',NULL,NULL,NULL,NULL),
	(9001,900,'屏東縣','屏東市','Y','900 屏東縣 屏東市',NULL,NULL,NULL,NULL),
	(9011,901,'屏東縣','三地門鄉','Y','901 屏東縣 三地門鄉',NULL,NULL,NULL,NULL),
	(9021,902,'屏東縣','霧臺鄉','Y','902 屏東縣 霧臺鄉',NULL,NULL,NULL,NULL),
	(9031,903,'屏東縣','瑪家鄉','Y','903 屏東縣 瑪家鄉',NULL,NULL,NULL,NULL),
	(9041,904,'屏東縣','九如鄉','Y','904 屏東縣 九如鄉',NULL,NULL,NULL,NULL),
	(9051,905,'屏東縣','里港鄉','Y','905 屏東縣 里港鄉',NULL,NULL,NULL,NULL),
	(9061,906,'屏東縣','高樹鄉','Y','906 屏東縣 高樹鄉',NULL,NULL,NULL,NULL),
	(9071,907,'屏東縣','盬埔鄉','Y','907 屏東縣 盬埔鄉',NULL,NULL,NULL,NULL),
	(9081,908,'屏東縣','長治鄉','Y','908 屏東縣 長治鄉',NULL,NULL,NULL,NULL),
	(9091,909,'屏東縣','麟洛鄉','Y','909 屏東縣 麟洛鄉',NULL,NULL,NULL,NULL),
	(9111,911,'屏東縣','竹田鄉','Y','911 屏東縣 竹田鄉',NULL,NULL,NULL,NULL),
	(9121,912,'屏東縣','內埔鄉','Y','912 屏東縣 內埔鄉',NULL,NULL,NULL,NULL),
	(9131,913,'屏東縣','萬丹鄉','Y','913 屏東縣 萬丹鄉',NULL,NULL,NULL,NULL),
	(9201,920,'屏東縣','潮州鎮','Y','920 屏東縣 潮州鎮',NULL,NULL,NULL,NULL),
	(9211,921,'屏東縣','泰武鄉','Y','921 屏東縣 泰武鄉',NULL,NULL,NULL,NULL),
	(9221,922,'屏東縣','來義鄉','Y','922 屏東縣 來義鄉',NULL,NULL,NULL,NULL),
	(9231,923,'屏東縣','萬巒鄉','Y','923 屏東縣 萬巒鄉',NULL,NULL,NULL,NULL),
	(9241,924,'屏東縣','崁頂鄉','Y','924 屏東縣 崁頂鄉',NULL,NULL,NULL,NULL),
	(9251,925,'屏東縣','新埤鄉','Y','925 屏東縣 新埤鄉',NULL,NULL,NULL,NULL),
	(9261,926,'屏東縣','南州鄉','Y','926 屏東縣 南州鄉',NULL,NULL,NULL,NULL),
	(9271,927,'屏東縣','林邊鄉','Y','927 屏東縣 林邊鄉',NULL,NULL,NULL,NULL),
	(9281,928,'屏東縣','東港鎮','Y','928 屏東縣 東港鎮',NULL,NULL,NULL,NULL),
	(9291,929,'屏東縣','琉球鄉','Y','929 屏東縣 琉球鄉',NULL,NULL,NULL,NULL),
	(9311,931,'屏東縣','佳冬鄉','Y','931 屏東縣 佳冬鄉',NULL,NULL,NULL,NULL),
	(9321,932,'屏東縣','新園鄉','Y','932 屏東縣 新園鄉',NULL,NULL,NULL,NULL),
	(9401,940,'屏東縣','枋寮鄉','Y','940 屏東縣 枋寮鄉',NULL,NULL,NULL,NULL),
	(9411,941,'屏東縣','枋山鄉','Y','941 屏東縣 枋山鄉',NULL,NULL,NULL,NULL),
	(9421,942,'屏東縣','春日鄉','Y','942 屏東縣 春日鄉',NULL,NULL,NULL,NULL),
	(9431,943,'屏東縣','獅子鄉','Y','943 屏東縣 獅子鄉',NULL,NULL,NULL,NULL),
	(9441,944,'屏東縣','車城鄉','Y','944 屏東縣 車城鄉',NULL,NULL,NULL,NULL),
	(9451,945,'屏東縣','牡丹鄉','Y','945 屏東縣 牡丹鄉',NULL,NULL,NULL,NULL),
	(9461,946,'屏東縣','恆春鎮','Y','946 屏東縣 恆春鎮',NULL,NULL,NULL,NULL),
	(9471,947,'屏東縣','滿州鄉','Y','947 屏東縣 滿州鄉',NULL,NULL,NULL,NULL),
	(9501,950,'臺東縣','臺東市','Y','950 臺東縣 臺東市',NULL,NULL,NULL,NULL),
	(9511,951,'臺東縣','綠島鄉','N','951 臺東縣 綠島鄉',NULL,NULL,NULL,NULL),
	(9521,952,'臺東縣','蘭嶼鄉','N','952 臺東縣 蘭嶼鄉',NULL,NULL,NULL,NULL),
	(9531,953,'臺東縣','延平鄉','Y','953 臺東縣 延平鄉',NULL,NULL,NULL,NULL),
	(9541,954,'臺東縣','卑南鄉','Y','954 臺東縣 卑南鄉',NULL,NULL,NULL,NULL),
	(9551,955,'臺東縣','鹿野鄉','Y','955 臺東縣 鹿野鄉',NULL,NULL,NULL,NULL),
	(9561,956,'臺東縣','關山鎮','Y','956 臺東縣 關山鎮',NULL,NULL,NULL,NULL),
	(9571,957,'臺東縣','海端鄉','Y','957 臺東縣 海端鄉',NULL,NULL,NULL,NULL),
	(9581,958,'臺東縣','池上鄉','Y','958 臺東縣 池上鄉',NULL,NULL,NULL,NULL),
	(9591,959,'臺東縣','東河鄉','Y','959 臺東縣 東河鄉',NULL,NULL,NULL,NULL),
	(9611,961,'臺東縣','成功鎮','Y','961 臺東縣 成功鎮',NULL,NULL,NULL,NULL),
	(9621,962,'臺東縣','長濱鄉','Y','962 臺東縣 長濱鄉',NULL,NULL,NULL,NULL),
	(9631,963,'臺東縣','太麻里鄉','Y','963 臺東縣 太麻里鄉',NULL,NULL,NULL,NULL),
	(9641,964,'臺東縣','金峰鄉','Y','964 臺東縣 金峰鄉',NULL,NULL,NULL,NULL),
	(9651,965,'臺東縣','大武鄉','Y','965 臺東縣 大武鄉',NULL,NULL,NULL,NULL),
	(9661,966,'臺東縣','達仁鄉','Y','966 臺東縣 達仁鄉',NULL,NULL,NULL,NULL),
	(9701,970,'花蓮縣','花蓮市','Y','970 花蓮縣 花蓮市',NULL,NULL,NULL,NULL),
	(9711,971,'花蓮縣','新城鄉','Y','971 花蓮縣 新城鄉',NULL,NULL,NULL,NULL),
	(9721,972,'花蓮縣','秀林鄉','Y','972 花蓮縣 秀林鄉',NULL,NULL,NULL,NULL),
	(9731,973,'花蓮縣','吉安鄉','Y','973 花蓮縣 吉安鄉',NULL,NULL,NULL,NULL),
	(9741,974,'花蓮縣','壽豐鄉','Y','974 花蓮縣 壽豐鄉',NULL,NULL,NULL,NULL),
	(9751,975,'花蓮縣','鳳林鎮','Y','975 花蓮縣 鳳林鎮',NULL,NULL,NULL,NULL),
	(9761,976,'花蓮縣','光復鄉','Y','976 花蓮縣 光復鄉',NULL,NULL,NULL,NULL),
	(9771,977,'花蓮縣','豐濱鄉','Y','977 花蓮縣 豐濱鄉',NULL,NULL,NULL,NULL),
	(9781,978,'花蓮縣','瑞穗鄉','Y','978 花蓮縣 瑞穗鄉',NULL,NULL,NULL,NULL),
	(9791,979,'花蓮縣','萬榮鄉','Y','979 花蓮縣 萬榮鄉',NULL,NULL,NULL,NULL),
	(9811,981,'花蓮縣','玉里鎮','Y','981 花蓮縣 玉里鎮',NULL,NULL,NULL,NULL),
	(9821,982,'花蓮縣','卓溪鄉','Y','982 花蓮縣 卓溪鄉',NULL,NULL,NULL,NULL),
	(9831,983,'花蓮縣','富里鄉','Y','983 花蓮縣 富里鄉',NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `PostalCode` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Product
# ------------------------------------------------------------

CREATE TABLE `Product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_english_name` varchar(100) DEFAULT NULL,
  `product_name` varchar(50) NOT NULL,
  `product_desc` text,
  `image_link` varchar(255) DEFAULT NULL,
  `unit_id` int(11) NOT NULL DEFAULT '1',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_Product_Unit_idx` (`unit_id`),
  KEY `FK3x8jbxkh06xgina0lk88myphm` (`create_id`),
  KEY `FKqr23xuxiunmdifcebmysc5y5c` (`update_id`),
  CONSTRAINT `FK3x8jbxkh06xgina0lk88myphm` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKl41nix15pg9kv38mw5y00wkbl` FOREIGN KEY (`unit_id`) REFERENCES `Unit` (`unit_id`),
  CONSTRAINT `FKqr23xuxiunmdifcebmysc5y5c` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_Product_Unit` FOREIGN KEY (`unit_id`) REFERENCES `Unit` (`unit_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;

INSERT INTO `Product` (`product_id`, `product_english_name`, `product_name`, `product_desc`, `image_link`, `unit_id`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(41,'pineapple','鳳梨',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/pineapple.jpg',1,NULL,NULL,NULL,NULL),
	(42,'mango','芒果',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/tw-mango.jpg',1,NULL,NULL,NULL,NULL),
	(43,'pomelo','柚子',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/pomelo.jpg',1,NULL,NULL,NULL,NULL),
	(44,'banana','香蕉',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/banana.jpg',1,NULL,NULL,NULL,NULL),
	(45,'papaya','木瓜',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/papaya.jpg',1,NULL,NULL,NULL,NULL),
	(46,'guava','芭樂',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/guava.jpg',1,NULL,NULL,NULL,NULL),
	(47,'waxapple','蓮霧',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/waxapple.jpg',1,NULL,NULL,NULL,NULL),
	(48,'jujube','棗子',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/Jujube.jpg',1,NULL,NULL,NULL,NULL),
	(49,'redbeauty watermelon','西瓜',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/redbeauty-watermelon.jpg',1,NULL,NULL,NULL,NULL),
	(50,'peach','水蜜桃',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/peach.jpg',1,NULL,NULL,NULL,NULL),
	(51,'plums','李子',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/plums.jpg',1,NULL,NULL,NULL,NULL),
	(52,'kumquat','金棗',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/kumquat.jpg',1,NULL,NULL,NULL,NULL),
	(53,'foreign orange','柳丁',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/fforeign-orange.jpg',1,NULL,NULL,NULL,NULL),
	(54,'murcott','柑橘',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/murcott.jpg',1,NULL,NULL,NULL,NULL),
	(55,'persimmon','甜柿(硬)',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/persimmon.jpg',1,NULL,NULL,NULL,NULL),
	(56,'persimmon','甜柿(軟)',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/persimmon.jpg',1,NULL,NULL,NULL,NULL),
	(57,'lemon','檸檬',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/lemon2.jpg',1,NULL,NULL,NULL,NULL),
	(58,'litchi','荔枝',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/litchi.jpg',1,NULL,NULL,NULL,NULL),
	(59,'longan','龍眼',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/11/tropicalpick-longan-01.jpg',1,NULL,NULL,NULL,NULL),
	(60,'peal','梨子',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/peal.jpg',1,NULL,NULL,NULL,NULL),
	(61,'suger apple','釋迦',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/sugar-apple.jpg',1,NULL,NULL,NULL,NULL),
	(62,'carambola','楊桃',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/carambola.jpg',1,NULL,NULL,NULL,NULL),
	(63,'strawberry','草莓',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/strawberry.png',1,NULL,NULL,NULL,NULL),
	(64,'passsion fruit','百香果',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/passion_Fruit.jpg',1,NULL,NULL,NULL,NULL),
	(65,'small tomatoes','小番茄',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/small-tomatoes.jpg',1,NULL,NULL,NULL,NULL),
	(66,'fragonfruit','火龍果(紅肉)',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/dragon-fruit.jpg',1,NULL,NULL,NULL,NULL),
	(67,'white drangonfruit','火龍果(白肉)',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/white-dragonfruit.jpg',1,NULL,NULL,NULL,NULL),
	(68,'avocado','酪梨',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/avocado.jpg',1,NULL,NULL,NULL,NULL),
	(69,'grapefruit','葡萄柚',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/11/sweetie-fruit.jpg',1,NULL,NULL,NULL,NULL),
	(70,'loquat','枇杷',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/11/1_357883751.jpg',1,NULL,NULL,NULL,NULL),
	(71,'grape','葡萄',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/grape.jpg',1,NULL,NULL,NULL,NULL),
	(72,'honeymelon','哈密瓜',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/honeymelon.jpg',1,NULL,NULL,NULL,NULL),
	(73,'honeydew','美濃瓜',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/Honeydew.jpg',1,NULL,NULL,NULL,NULL),
	(74,'apple','蘋果',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/fuji-apple.jpg',1,NULL,NULL,NULL,NULL),
	(75,'zespri','奇異果',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/zespri08.jpg',1,NULL,NULL,NULL,NULL),
	(76,'jaboticaba','樹葡萄',NULL,'http://i1.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/jaboticaba.jpg',1,NULL,NULL,NULL,NULL),
	(77,'glodguo','黃金果',NULL,'http://i2.wp.com/fruitpay.com.tw/wp-content/uploads/2015/09/glod-guo.jpg',1,NULL,NULL,NULL,NULL),
	(78,'treeTomato','樹番茄',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2016/06/DSC_0769.jpg',1,NULL,NULL,NULL,NULL),
	(79,'shortBanana','芭蕉',NULL,'http://i0.wp.com/fruitpay.com.tw/wp-content/uploads/2016/06/shortBanana.png',1,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table OrderPlatform
# ------------------------------------------------------------

CREATE TABLE `OrderPlatform` (
  `platform_id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(50) NOT NULL,
  `platform_desc` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`platform_id`),
  KEY `FK7ijkg2fx3l5wffl2ucbn9b5wt` (`create_id`),
  KEY `FK5q4ip406ipmfp5qmm3eom5a82` (`update_id`),
  CONSTRAINT `FK5q4ip406ipmfp5qmm3eom5a82` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FK7ijkg2fx3l5wffl2ucbn9b5wt` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `OrderPlatform` (`platform_id`, `platform_name`, `platform_desc`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,'官方網站','由官網下訂單',NULL,NULL,NULL,NULL);

# Dump of table CustomerOrder
# ------------------------------------------------------------

CREATE TABLE `CustomerOrder` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `platform_id` int(11) NOT NULL,
  `program_id` int(11) NOT NULL,
  `program_num` int(11) NOT NULL DEFAULT '1',
  `receiver_last_name` varchar(50) NOT NULL,
  `receiver_first_name` varchar(50) NOT NULL,
  `receiver_gender` char(1) DEFAULT NULL,
  `receiver_cellphone` varchar(20) DEFAULT '',
  `receiver_house_phone` varchar(20) DEFAULT NULL,
  `post_id` int(11) NOT NULL,
  `receiver_address` varchar(255) NOT NULL,
  `tax_id` varchar(20) DEFAULT NULL,
  `tax_title` varchar(50) DEFAULT NULL,
  `payment_mode_id` int(11) NOT NULL,
  `order_date` datetime NOT NULL,
  `period_id` int(11) NOT NULL,
  `shipment_days_id` int(11) NOT NULL,
  `remark` text,
  `order_status_id` int(11) NOT NULL DEFAULT '0',
  `receive_way` int(11) DEFAULT NULL,
  `shipment_time` int(11) DEFAULT NULL,
  `coming_from` int(11) DEFAULT NULL,
  `receipt_way` int(11) DEFAULT NULL,
  `delivery_day` int(11) NOT NULL,
  `allow_foreign_fruits` char(1) NOT NULL DEFAULT 'N',
  `receipt_title` varchar(200) DEFAULT NULL,
  `receipt_vat_number` varchar(45) DEFAULT NULL,
  `shipping_cost` int(11) NOT NULL DEFAULT '0',
  `total_price` int(11) NOT NULL,
  `valid_flag` int(1) NOT NULL DEFAULT '1',
  `allpay_rtn_code` varchar(10) DEFAULT NULL,
  `allpay_order_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `shipment_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_Order_Customer_idx` (`customer_id`),
  KEY `fk_Order_OrderPlatform_idx` (`platform_id`),
  KEY `fk_Order_OrderProgram_idx` (`program_id`),
  KEY `fk_Order_PaymentMode_idx` (`payment_mode_id`),
  KEY `fk_Order_ShipmentDays_idx` (`shipment_days_id`),
  KEY `fk_Order_OrderStatus_idx` (`order_status_id`),
  KEY `fk_Order_shipmentPeriod_idx` (`period_id`),
  KEY `fk_Order_shipmentTime_idx` (`shipment_time`),
  KEY `fk_Order_comingFrom_idx` (`coming_from`),
  KEY `fk_Order_receiveWay_idx` (`receive_way`),
  KEY `fk_Order_receiptWay_idx` (`receipt_way`),
  KEY `fk_Order_DeliveryDay_idx` (`delivery_day`),
  KEY `fk_AllpayOrder_allpayOrderId_idx` (`allpay_order_id`),
  KEY `fk_PostalCode_idx` (`post_id`),
  KEY `FKcoe554or2wmam1ppkixntwbj4` (`create_id`),
  KEY `FKhm90cxej0yi9hnxh5pmuer3cs` (`update_id`),
  CONSTRAINT `FK2uwer9y7x6y26xwjkseafwd3c` FOREIGN KEY (`receipt_way`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FK5g2bflwphfue627t7xup6c37r` FOREIGN KEY (`coming_from`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FK7p8a39hyliiilaj0d6gu4t233` FOREIGN KEY (`delivery_day`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FK848rdqtbfqherx41smo3lmkq3` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FK8yh167houng1jif8q0g9ly072` FOREIGN KEY (`receive_way`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKcoe554or2wmam1ppkixntwbj4` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKcww0chmwbwwlh51knst1l1w8o` FOREIGN KEY (`program_id`) REFERENCES `OrderProgram` (`program_id`),
  CONSTRAINT `FKfgaq1f6v9sdyjpihk3a390eby` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`),
  CONSTRAINT `FKhfykh7pfr1umj1a8oood5m0dq` FOREIGN KEY (`shipment_days_id`) REFERENCES `ShipmentDays` (`shipment_days_id`),
  CONSTRAINT `FKhm90cxej0yi9hnxh5pmuer3cs` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKjywj7rnmwn218evtlvdvrh7se` FOREIGN KEY (`platform_id`) REFERENCES `OrderPlatform` (`platform_id`),
  CONSTRAINT `FKnstl4cnoymhj2mmf07q2s8mgx` FOREIGN KEY (`period_id`) REFERENCES `ShipmentPeriod` (`period_id`),
  CONSTRAINT `FKok8m2xv23ub75qrfda52hndtc` FOREIGN KEY (`shipment_time`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKqe39oxp6v0stob8o4qox256hy` FOREIGN KEY (`allpay_order_id`) REFERENCES `AllpayOrder` (`allpay_order_id`),
  CONSTRAINT `FKrwg3i7g0afbvs561g4425kjga` FOREIGN KEY (`payment_mode_id`) REFERENCES `PaymentMode` (`payment_mode_id`),
  CONSTRAINT `FKsoct3800xjos6yn0pqptq1i0s` FOREIGN KEY (`order_status_id`) REFERENCES `OrderStatus` (`order_status_id`),
  CONSTRAINT `fk_AllpayOrder_allpayOrderId` FOREIGN KEY (`allpay_order_id`) REFERENCES `AllpayOrder` (`allpay_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_comingFrom` FOREIGN KEY (`coming_from`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Customer` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_deliveryDay` FOREIGN KEY (`delivery_day`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_OrderPlatform` FOREIGN KEY (`platform_id`) REFERENCES `OrderPlatform` (`platform_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_OrderProgram` FOREIGN KEY (`program_id`) REFERENCES `OrderProgram` (`program_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_OrderStatus` FOREIGN KEY (`order_status_id`) REFERENCES `OrderStatus` (`order_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_PaymentMode` FOREIGN KEY (`payment_mode_id`) REFERENCES `PaymentMode` (`payment_mode_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_receiptWay` FOREIGN KEY (`receipt_way`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_receiveWay` FOREIGN KEY (`receive_way`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_ShipmentDays` FOREIGN KEY (`shipment_days_id`) REFERENCES `ShipmentDays` (`shipment_days_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_shipmentTime` FOREIGN KEY (`shipment_time`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PostalCode` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table AllpayOrder
# ------------------------------------------------------------

CREATE TABLE `AllpayOrder` (
  `allpay_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_date` varchar(45) DEFAULT NULL,
  `rtn_code` varchar(10) DEFAULT NULL,
  `rtn_message` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`allpay_order_id`),
  KEY `FKgmx7e2cmf0gqgc9b6uw0b35nt` (`create_id`),
  KEY `FKehr2yk1js18rpal2hux4q6jw9` (`update_id`),
  CONSTRAINT `FKehr2yk1js18rpal2hux4q6jw9` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKgmx7e2cmf0gqgc9b6uw0b35nt` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table Coupon
# ------------------------------------------------------------

CREATE TABLE `Coupon` (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_name` varchar(50) NOT NULL,
  `coupon_desc` varchar(50) DEFAULT NULL,
  `coupon_type` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `first_value` varchar(45) DEFAULT '-1',
  `expiry_day` date DEFAULT NULL,
  `max_limit` int(11) DEFAULT '-1',
  `min_limit` int(11) DEFAULT '-1',
  `max_usage_per_coupon` int(11) DEFAULT '-1',
  `max_usage_per_user` int(11) DEFAULT '-1',
  `usage_individually` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`coupon_id`),
  UNIQUE KEY `coupon_name_UNIQUE` (`coupon_name`),
  KEY `fk_Coupon_CouponType_idx` (`coupon_type`),
  KEY `fk_Coupon_Usage_Individually_idx` (`usage_individually`),
  KEY `FKadpxo7cx8imfqr34ph1i2kds1` (`create_id`),
  KEY `FKsd48sfflwttgn371cmo5kmed` (`update_id`),
  CONSTRAINT `FKad4879lcaxul7htgld00x0kcx` FOREIGN KEY (`coupon_type`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKadpxo7cx8imfqr34ph1i2kds1` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKkohg6aetwxc86tygy87njxq9w` FOREIGN KEY (`usage_individually`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKsd48sfflwttgn371cmo5kmed` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_Coupon_CouponType` FOREIGN KEY (`coupon_type`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Coupon_Usage_Individually` FOREIGN KEY (`usage_individually`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table CouponRecord
# ------------------------------------------------------------

CREATE TABLE `CouponRecord` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `fk_CouponRecord_couponId_idx` (`coupon_id`),
  KEY `fk_CouponRecord_OrderId_idx` (`order_id`),
  CONSTRAINT `FKe4dkbm5mpxv8salca7exh14lw` FOREIGN KEY (`coupon_id`) REFERENCES `Coupon` (`coupon_id`),
  CONSTRAINT `FKk6vt4yt9gwf6kmksq266kvetj` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`),
  CONSTRAINT `fk_CouponRecord_CouponId` FOREIGN KEY (`coupon_id`) REFERENCES `Coupon` (`coupon_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CouponRecord_OrderId` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table CreditCardInfo
# ------------------------------------------------------------

CREATE TABLE `CreditCardInfo` (
  `credit_card_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `card_number` varchar(50) NOT NULL,
  `card_vendor` varchar(50) NOT NULL,
  `card_expired_year` varchar(10) NOT NULL,
  `card_expired_month` varchar(10) NOT NULL,
  `security_code` varchar(10) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`credit_card_info_id`),
  KEY `fk_CreditCardInfo_Customer_idx` (`customer_id`),
  KEY `FKdj16k6ej1xrlh09143niriw13` (`create_id`),
  KEY `FKjga9wa1dftwvwbo4dab2gcq2` (`update_id`),
  CONSTRAINT `FKdj16k6ej1xrlh09143niriw13` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKjga9wa1dftwvwbo4dab2gcq2` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKm0xx1jlf288pmiu7u1ty9sg2r` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_CreditCardInfo_Customer` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table FieldChangeRecord
# ------------------------------------------------------------

CREATE TABLE `FieldChangeRecord` (
  `field_change_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `field_name` varchar(255) DEFAULT NULL,
  `field_value` varchar(255) DEFAULT NULL,
  `pk_id` int(11) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`field_change_record_id`),
  KEY `FK5fy0lludblt308ttax69rq3y9` (`create_id`),
  KEY `FKd46hidfk02hi12t61er03pmp8` (`update_id`),
  CONSTRAINT `FK5fy0lludblt308ttax69rq3y9` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKd46hidfk02hi12t61er03pmp8` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table OrderComment
# ------------------------------------------------------------

CREATE TABLE `OrderComment` (
  `comment_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `valid_flag` int(1) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKr7dhjfp70brq1qexju19yfjf3` (`create_id`),
  KEY `FKh7131avtxpn7tuj0yryx2vrim` (`update_id`),
  KEY `FKrxq20c60gufvmg9uexdht9sd8` (`order_id`),
  CONSTRAINT `FKh7131avtxpn7tuj0yryx2vrim` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKr7dhjfp70brq1qexju19yfjf3` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKrxq20c60gufvmg9uexdht9sd8` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table OrderPreference
# ------------------------------------------------------------

CREATE TABLE `OrderPreference` (
  `preference_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `like_degree` tinyint(4) NOT NULL DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`preference_id`),
  KEY `fk_OrderPreference_Order_idx` (`order_id`),
  KEY `fk_OrderPreference_Product_idx` (`product_id`),
  KEY `FK5gddudybnr4lgdbovn0r8vaf8` (`create_id`),
  KEY `FKgdsmgp8g2288efmu4mcpbmg31` (`update_id`),
  CONSTRAINT `FK5gddudybnr4lgdbovn0r8vaf8` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FK9kgrgjd7ljurt31xjn8nq0480` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`),
  CONSTRAINT `FKgdsmgp8g2288efmu4mcpbmg31` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKm526cait09q3hwe0hgyaedpt1` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`),
  CONSTRAINT `fk_OrderPreference_Product` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table Role
# ------------------------------------------------------------

CREATE TABLE `Role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_type` int(11) NOT NULL,
  `parent_role_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `fk_Role_roleId` (`parent_role_id`),
  KEY `FK5j7eaxj4mxqefyn740bq7pih9` (`create_id`),
  KEY `FKji0jom8xe9uf2lvr06shho7lk` (`update_id`),
  KEY `FKr8rt3peblt2hpgy6dnmubyqjl` (`role_type`),
  CONSTRAINT `FK5j7eaxj4mxqefyn740bq7pih9` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKji0jom8xe9uf2lvr06shho7lk` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKr8rt3peblt2hpgy6dnmubyqjl` FOREIGN KEY (`role_type`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `fk_ConstantOption_optionId` FOREIGN KEY (`role_type`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `fk_Role_roleId` FOREIGN KEY (`parent_role_id`) REFERENCES `Role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;

INSERT INTO `Role` (`role_id`, `role_type`, `parent_role_id`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1,39,NULL,'2016-05-01 20:31:27','2016-05-01 20:31:27',1223,1223);

/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Shipment
# ------------------------------------------------------------

CREATE TABLE `Shipment` (
  `shipment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `year` year(4) NOT NULL,
  `week_of_year` tinyint(4) NOT NULL,
  `post_id` int(11) NOT NULL,
  `receiver_address` varchar(255) NOT NULL,
  `receiver_last_name` varchar(50) NOT NULL,
  `receiver_first_name` varchar(50) NOT NULL,
  `receiver_gender` char(1) DEFAULT NULL,
  `receiver_cellphone` varchar(20) NOT NULL,
  `receiver_house_phone` varchar(20) DEFAULT NULL,
  `period_id` int(11) NOT NULL,
  `shipment_days_id` int(11) NOT NULL,
  `payment_mode_id` int(11) NOT NULL,
  `shipment_status_id` int(11) NOT NULL DEFAULT '0',
  `payment_status_id` int(11) NOT NULL DEFAULT '0',
  `remark` text,
  `record_created_date` timestamp NULL DEFAULT NULL,
  `shipping_cost` int(11) NOT NULL DEFAULT '0',
  `total_price` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_id`),
  KEY `fk_Shipment_Order_idx` (`order_id`),
  KEY `fk_Shipment_ShipmentPeriod_idx` (`period_id`),
  KEY `fk_Shipment_PaymentMode_idx` (`payment_mode_id`),
  KEY `fk_Shipment_ShipmentDays_idx` (`shipment_days_id`),
  KEY `fk_Shipment_ShipmentStatus_idx` (`shipment_status_id`),
  KEY `fk_Shipment_PaymentStatus_idx` (`payment_status_id`),
  KEY `fk_Village_code_idx` (`post_id`),
  KEY `FKnrb8mpd889w2j78dh1baqtpo4` (`create_id`),
  KEY `FKb5l44789dy4eerelivbakth4h` (`update_id`),
  CONSTRAINT `FK8x2ejrw802pdymet0cj0n6f4v` FOREIGN KEY (`shipment_status_id`) REFERENCES `ShipmentStatus` (`shipment_status_id`),
  CONSTRAINT `FKb5l44789dy4eerelivbakth4h` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKgjc7nihr2foljoneduhpb6lp3` FOREIGN KEY (`period_id`) REFERENCES `ShipmentPeriod` (`period_id`),
  CONSTRAINT `FKhmmoacfxqi0fmvd9b2rs2835t` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`),
  CONSTRAINT `FKihk2f57qhlad0at0gsm1egba6` FOREIGN KEY (`shipment_days_id`) REFERENCES `ShipmentDays` (`shipment_days_id`),
  CONSTRAINT `FKmigg38d4rttgj53axk26wet30` FOREIGN KEY (`payment_mode_id`) REFERENCES `PaymentMode` (`payment_mode_id`),
  CONSTRAINT `FKn35l6h5mb83r3bdk2puol4aav` FOREIGN KEY (`payment_status_id`) REFERENCES `PaymentStatus` (`payment_status_id`),
  CONSTRAINT `FKn8tcp7mvuok9q1me4lsc4n5x1` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`),
  CONSTRAINT `FKnrb8mpd889w2j78dh1baqtpo4` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_Shipment_Order` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_PaymentMode` FOREIGN KEY (`payment_mode_id`) REFERENCES `PaymentMode` (`payment_mode_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_PaymentStatus` FOREIGN KEY (`payment_status_id`) REFERENCES `PaymentStatus` (`payment_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_PostId` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_ShipmentDays` FOREIGN KEY (`shipment_days_id`) REFERENCES `ShipmentDays` (`shipment_days_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_ShipmentPeriod` FOREIGN KEY (`period_id`) REFERENCES `ShipmentPeriod` (`period_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_ShipmentStatus` FOREIGN KEY (`shipment_status_id`) REFERENCES `ShipmentStatus` (`shipment_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ShipmentChange
# ------------------------------------------------------------

CREATE TABLE `ShipmentChange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `apply_date` datetime NOT NULL,
  `shipment_change_type` int(11) NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `valid_flag` int(11) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CustomerOrder_orderId_idx` (`order_id`),
  KEY `fk_CustomOption_optionId_idx` (`shipment_change_type`),
  KEY `FKqikos2grc9s3hnqvdm2aa20ni` (`create_id`),
  KEY `FKpc4spv1woy4dpi89xnoowqqix` (`update_id`),
  CONSTRAINT `FK43dd2pgid3ynmne440xjw9jh3` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`),
  CONSTRAINT `FK6f5whq11gojhptndehgu1xnvf` FOREIGN KEY (`shipment_change_type`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKpc4spv1woy4dpi89xnoowqqix` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKqikos2grc9s3hnqvdm2aa20ni` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_CustomerOrder_orderId_cascade` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_CustomOption_optionId` FOREIGN KEY (`shipment_change_type`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table ShipmentDetail
# ------------------------------------------------------------

CREATE TABLE `ShipmentDetail` (
  `shipment_detail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipment_id` bigint(20) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `eating_priority` tinyint(4) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_detail_id`),
  KEY `fk_ShipmentDetail_Shipment_idx` (`shipment_id`),
  KEY `fk_ShipmentDetail_Product_idx` (`product_id`),
  KEY `FKm77mnnicck6wmsf41r2wo464j` (`create_id`),
  KEY `FK2bt8yindy99b7ds1q6cjb2jm5` (`update_id`),
  CONSTRAINT `FK2bt8yindy99b7ds1q6cjb2jm5` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FK7obw0h2vr7j1njrxburqs7d65` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`),
  CONSTRAINT `FKchadifikru331pmcys8gqsnu0` FOREIGN KEY (`shipment_id`) REFERENCES `Shipment` (`shipment_id`),
  CONSTRAINT `FKm77mnnicck6wmsf41r2wo464j` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_ShipmentDetail_Product` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ShipmentDetail_Shipment` FOREIGN KEY (`shipment_id`) REFERENCES `Shipment` (`shipment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table ShipmentRecord
# ------------------------------------------------------------

CREATE TABLE `ShipmentRecord` (
  `shipment_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `valid_flag` int(11) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `shipment_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_record_id`),
  KEY `FKqnju370o1ovsb7la0g717640q` (`create_id`),
  KEY `FKcueetjjft5iwicydjuorh4rko` (`update_id`),
  KEY `FKhmpuquts55ohlls7yku54u7k9` (`shipment_type`),
  CONSTRAINT `FKhmpuquts55ohlls7yku54u7k9` FOREIGN KEY (`shipment_type`) REFERENCES `ConstantOption` (`option_id`),
  CONSTRAINT `FKcueetjjft5iwicydjuorh4rko` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKqnju370o1ovsb7la0g717640q` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ShipmentRecordDetail
# ------------------------------------------------------------

CREATE TABLE `ShipmentRecordDetail` (
  `shipment_record_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `valid_flag` int(11) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `shipment_record_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shipment_record_detail_id`),
  KEY `FKn952hh87nvu4gq8a8ulc9hmsm` (`create_id`),
  KEY `FKffvsvhar6wwxm0s29cisbbiuj` (`update_id`),
  KEY `FKp95jb13mo18ki9pjwlcu71v11` (`order_id`),
  KEY `FKlgolrvnddg3dv56ywm66p019c` (`shipment_record_id`),
  CONSTRAINT `FKlgolrvnddg3dv56ywm66p019c` FOREIGN KEY (`shipment_record_id`) REFERENCES `ShipmentRecord` (`shipment_record_id`),
  CONSTRAINT `FKffvsvhar6wwxm0s29cisbbiuj` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKn952hh87nvu4gq8a8ulc9hmsm` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKp95jb13mo18ki9pjwlcu71v11` FOREIGN KEY (`order_id`) REFERENCES `CustomerOrder` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table UserRole
# ------------------------------------------------------------

CREATE TABLE `UserRole` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `FKsjnunji4jkrknar09q2ppc1uw` (`customer_id`),
  KEY `FKdm5xsn0ukwtm6eibbore303y0` (`role_id`),
  KEY `FKtg7wkx4pmx4rh5sn4tb22xyov` (`create_id`),
  KEY `FKbrsxff62y6axaixfro96j1hj6` (`update_id`),
  CONSTRAINT `FKbrsxff62y6axaixfro96j1hj6` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKdm5xsn0ukwtm6eibbore303y0` FOREIGN KEY (`role_id`) REFERENCES `Role` (`role_id`),
  CONSTRAINT `FKsjnunji4jkrknar09q2ppc1uw` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `FKtg7wkx4pmx4rh5sn4tb22xyov` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
  CONSTRAINT `fk_Customer_customerId` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `UserRole` (`user_role_id`, `customer_id`, `role_id`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
	(1, 1223, 1, '2016-05-01 20:32:41', '2016-05-01 20:32:41', 1223, 1223);


# Add foreign key to Customer
# ------------------------------------------------------------
ALTER TABLE Customer 
ADD CONSTRAINT `FK3hb7mkawj55s58kjdjqr2lfsr` FOREIGN KEY (`referenced_id`) REFERENCES `Customer` (`customer_id`),
ADD CONSTRAINT `FKbsjace6xgdgt0fgfhb6o0sx6f` FOREIGN KEY (`create_id`) REFERENCES `Customer` (`customer_id`),
ADD CONSTRAINT `FKabdhuepc7qa63yvwp7vrp71fs` FOREIGN KEY (`update_id`) REFERENCES `Customer` (`customer_id`),
ADD CONSTRAINT `FK69ihvsu5x4nqk3pt98s50upna` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`),
ADD CONSTRAINT `FKgwy2vwgwteudwgrh206cfxdpt` FOREIGN KEY (`register_from`) REFERENCES `ConstantOption` (`option_id`),
ADD CONSTRAINT `fk_Constant_RegisterFrom` FOREIGN KEY (`register_from`) REFERENCES `ConstantOption` (`option_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_PostalCode_postId` FOREIGN KEY (`post_id`) REFERENCES `PostalCode` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;


# Insert order related datas
# 
# Dump of table CustomerOrder
# ------------------------------------------------------------

LOCK TABLES `CustomerOrder` WRITE;
/*!40000 ALTER TABLE `CustomerOrder` DISABLE KEYS */;

INSERT INTO `CustomerOrder` (`order_id`, `customer_id`, `platform_id`, `program_id`, `program_num`, `receiver_last_name`, `receiver_first_name`, `receiver_gender`, `receiver_cellphone`, `receiver_house_phone`, `post_id`, `receiver_address`, `tax_id`, `tax_title`, `payment_mode_id`, `order_date`, `period_id`, `shipment_days_id`, `remark`, `order_status_id`, `receive_way`, `shipment_time`, `coming_from`, `receipt_way`, `delivery_day`, `allow_foreign_fruits`, `receipt_title`, `receipt_vat_number`, `shipping_cost`, `total_price`, `valid_flag`, `allpay_rtn_code`, `allpay_order_id`, `create_date`, `update_date`, `create_id`, `update_id`, `shipment_count`)
VALUES
  (1,1223,1,1,1,'徐','瑋志','M','0933370691','048238111',2031,'永靖鄉同安村',NULL,NULL,1,'2016-06-14 10:25:45',1,7,NULL,1,1,5,8,12,15,'Y',NULL,NULL,0,699,1,NULL,NULL,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223,3),
  (2,1223,1,1,1,'徐','瑋志','M','0933370691','048238111',2031,'永靖鄉同安村',NULL,NULL,1,'2016-06-14 10:39:25',2,2,NULL,3,1,5,8,12,16,'Y',NULL,NULL,0,699,1,NULL,NULL,'2016-06-14 10:39:46','2016-06-14 10:39:46',1223,1223,3),
  (3,1223,1,1,1,'徐','瑋志','M','0933370691','048238111',2031,'永靖鄉同安村',NULL,NULL,2,'2016-06-14 10:43:41',2,7,NULL,1,1,5,8,12,15,'Y',NULL,NULL,0,699,1,NULL,NULL,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223,3);

/*!40000 ALTER TABLE `CustomerOrder` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table OrderPreference
# ------------------------------------------------------------

LOCK TABLES `OrderPreference` WRITE;
/*!40000 ALTER TABLE `OrderPreference` DISABLE KEYS */;

INSERT INTO `OrderPreference` (`preference_id`, `order_id`, `product_id`, `like_degree`, `create_date`, `update_date`, `create_id`, `update_id`)
VALUES
  (1,1,41,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (2,1,42,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (3,1,43,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (4,1,44,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (5,1,45,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (6,1,46,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (7,1,47,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (8,1,48,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (9,1,49,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (10,1,50,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (11,1,51,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (12,1,52,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (13,1,53,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (14,1,54,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (15,1,55,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (16,1,56,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (17,1,57,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (18,1,58,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (19,1,59,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (20,1,60,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (21,1,61,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (22,1,62,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (23,1,63,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (24,1,64,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (25,1,65,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (26,1,66,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (27,1,67,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (28,1,68,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (29,1,69,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (30,1,70,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (31,1,71,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (32,1,72,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (33,1,73,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (34,1,74,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (35,1,75,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (36,1,76,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (37,1,77,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (38,1,78,3,'2016-06-14 10:25:52','2016-06-14 10:25:52',1223,1223),
  (39,1,79,3,'2016-06-14 10:25:53','2016-06-14 10:25:53',1223,1223),
  (40,2,41,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (41,2,42,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (42,2,43,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (43,2,44,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (44,2,45,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (45,2,46,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (46,2,47,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (47,2,48,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (48,2,49,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (49,2,50,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (50,2,51,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (51,2,52,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (52,2,53,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (53,2,54,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (54,2,55,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (55,2,56,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (56,2,57,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (57,2,58,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (58,2,59,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (59,2,60,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (60,2,61,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (61,2,62,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (62,2,63,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (63,2,64,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (64,2,65,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (65,2,66,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (66,2,67,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (67,2,68,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (68,2,69,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (69,2,70,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (70,2,71,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (71,2,72,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (72,2,73,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (73,2,74,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (74,2,75,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (75,2,76,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (76,2,77,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (77,2,78,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (78,2,79,3,'2016-06-14 10:39:47','2016-06-14 10:39:47',1223,1223),
  (79,3,41,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (80,3,42,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (81,3,43,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (82,3,44,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (83,3,45,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (84,3,46,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (85,3,47,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (86,3,48,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (87,3,49,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (88,3,50,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (89,3,51,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (90,3,52,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (91,3,53,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (92,3,54,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (93,3,55,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (94,3,56,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (95,3,57,3,'2016-06-14 10:43:55','2016-06-14 10:43:55',1223,1223),
  (96,3,58,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (97,3,59,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (98,3,60,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (99,3,61,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (100,3,62,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (101,3,63,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (102,3,64,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (103,3,65,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (104,3,66,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (105,3,67,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (106,3,68,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (107,3,69,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (108,3,70,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (109,3,71,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (110,3,72,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (111,3,73,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (112,3,74,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (113,3,75,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (114,3,76,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (115,3,77,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (116,3,78,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223),
  (117,3,79,3,'2016-06-14 10:43:56','2016-06-14 10:43:56',1223,1223);

/*!40000 ALTER TABLE `OrderPreference` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ShipmentRecord
# ------------------------------------------------------------

LOCK TABLES `ShipmentRecord` WRITE;
/*!40000 ALTER TABLE `ShipmentRecord` DISABLE KEYS */;

INSERT INTO `ShipmentRecord` (`shipment_record_id`, `create_date`, `update_date`, `date`, `valid_flag`, `create_id`, `update_id`, `shipment_type`)
VALUES
  (1,'2016-06-14 10:44:32','2016-06-14 10:44:32','2016-06-20 00:00:00',1,1223,1223,38);

/*!40000 ALTER TABLE `ShipmentRecord` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ShipmentRecordDetail
# ------------------------------------------------------------

LOCK TABLES `ShipmentRecordDetail` WRITE;
/*!40000 ALTER TABLE `ShipmentRecordDetail` DISABLE KEYS */;

INSERT INTO `ShipmentRecordDetail` (`shipment_record_detail_id`, `create_date`, `update_date`, `valid_flag`, `create_id`, `update_id`, `order_id`, `shipment_record_id`)
VALUES
  (1,'2016-06-14 10:44:32','2016-06-14 10:44:32',1,1223,1223,1,1),
  (2,'2016-06-14 10:44:32','2016-06-14 10:44:32',1,1223,1223,3,1);

/*!40000 ALTER TABLE `ShipmentRecordDetail` ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;