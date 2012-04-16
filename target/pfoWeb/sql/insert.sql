DROP SCHEMA IF EXISTS `pfo`;
CREATE SCHEMA  `pfo` CHARACTER SET=utf8;

USE `pfp`;
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Enabled` bit(1) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Authority` varchar(50) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `authorities_user` (`UserId`)
) ENGINE=InnoDB;

INSERT INTO `pfo`.`users` (`Enabled`, `Password`, `Username`) VALUES (true, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'eugen');

INSERT INTO `pfo`.`authorities` (`Authority`, `Username`, `UserId`) VALUES ('ROLE_ADMIN', 'eugen', 1);


DROP TABLE IF EXISTS `Settings`;
CREATE TABLE `Settings` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CronExpresion` varchar(255) DEFAULT NULL,
  `ItemsOnPage` int(11) DEFAULT NULL,
  `MaxLivingLinks` bigint(20) DEFAULT NULL,
  `NumberOfPages` int(11) DEFAULT NULL,
  `RefreshInterval` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB;

INSERT INTO `Settings` VALUES ('1', '0 0 * * * ?', '20', '600', '5', '1000');


DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `UUID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB;



DROP TABLE IF EXISTS `VideoNews`;
CREATE TABLE `VideoNews` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `AddedDate` datetime DEFAULT NULL,
  `Duration` int(11) DEFAULT NULL,
  `ImgUrl` varchar(255) DEFAULT NULL,
  `LastUrlUpdate` datetime DEFAULT NULL,
  `Rating` int(11) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Title_ru` varchar(255) DEFAULT NULL,
  `Title_tr` varchar(255) DEFAULT NULL,
  `VideoKey` varchar(255) DEFAULT NULL,
  `VideoUrl` varchar(255) DEFAULT NULL,
  `Views` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `Category_VideoNewses`;
CREATE TABLE `Category_VideoNewses` (
  `CategoryId` bigint(20) NOT NULL,
  `VideoNewsId` bigint(20) NOT NULL,
  KEY `CategoryId_VideoNewsId` (`CategoryId`),
  KEY `VideoNewsId_CategoryId` (`VideoNewsId`)
) ENGINE=InnoDB;
