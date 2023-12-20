CREATE DATABASE IF NOT EXISTS `cafe` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `cafe`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
	`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phonenum` VARCHAR(15) NOT NULL,
    `pw` VARCHAR(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS user_roles(
	`user_id` BIGINT NOT NULL,
    `roles` VARCHAR(255),
    CONSTRAINT `FK_user_role_user_id`
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product`(
	`productid` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`category` VARCHAR(30) COLLATE utf8mb4_unicode_ci,
    `price` BIGINT,
    `cost` BIGINT,
    `name` VARCHAR(255) COLLATE utf8mb4_unicode_ci,
    `charname` VARCHAR(255) COLLATE utf8mb4_unicode_ci,
    `description` VARCHAR(255) COLLATE utf8mb4_unicode_ci,
    `barcode` VARCHAR(30),
    `expirationdate` CHAR(10),
    size CHAR(5) 
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;