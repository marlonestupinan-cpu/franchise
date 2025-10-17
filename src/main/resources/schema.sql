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

