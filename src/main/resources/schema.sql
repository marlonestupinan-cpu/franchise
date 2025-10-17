-- Create database
CREATE DATABASE `franchise` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- Create franchise table
CREATE TABLE franchise.franchise (
	id BIGINT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT franchise_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- Create branch table
CREATE TABLE franchise.branch (
	id BIGINT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	id_franchise BIGINT NOT NULL,
	CONSTRAINT branch_pk PRIMARY KEY (id),
	CONSTRAINT branch_franchise_FK FOREIGN KEY (id_franchise) REFERENCES franchise.franchise(id) ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- Create product table
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `stock` int NOT NULL,
  `id_branch` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_branch_FK` (`id_branch`),
  CONSTRAINT `product_branch_FK` FOREIGN KEY (`id_branch`) REFERENCES `branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
