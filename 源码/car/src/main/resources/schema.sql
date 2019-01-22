CREATE DATABASE IF NOT EXISTS `car` DEFAULT CHARACTER SET utf8;

USE `car`;

DROP TABLE IF EXISTS `t_code`;
CREATE TABLE `t_code` (
  `codeid` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`codeid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

