-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 192.168.63.235    Database: library
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'J.R.R. Tolkien'),(2,'Patrick Rothfuss'),(3,'Brandon Sanderson');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'El señor de los anillos','J.R.R. Tolkien','978-84-450-7570-9'),(2,'El hobbit','J.R.R. Tolkien','978-84-450-7570-9'),(3,'El silmarillion','J.R.R. Tolkien','978-84-450-7570-9'),(4,'El nombre del viento','Patrick Rothfuss','978-84-450-7570-9'),(5,'El temor de un hombre sabio','Patrick Rothfuss','978-84-450-7570-9'),(6,'El imperio final','Brandon Sanderson','978-84-450-7570-9'),(7,'El pozo de la ascensión','Brandon Sanderson','978-84-450-7570-9'),(8,'El héroe de las eras','Brandon Sanderson','978-84-450-7570-9'),(9,'El aliento de los dioses','Brandon Sanderson','978-84-450-7570-9'),(10,'Elantris','Brandon Sanderson','978-84-450-7570-9'),(11,'La esperanza del venado','Brandon Sanderson','978-84-450-7570-9'),(12,'La senda de los héroes','Brandon Sanderson','978-84-450-7570-9'),(13,'hola chepe','Josafat Muñoz Valverde','-7135'),(14,'Betjader mal jefe de grupo','Josafat Muñoz Valverde','-7135'),(15,'Niño es acosador','Josafat Muñoz Valverde','-7135'),(16,'Ayudenme vivo en temixco y hay toque de queda','Josafat Muñoz Valverde','-7135'),(17,'AJ>>>>MAJO','Josafat Muñoz Valverde','-7135');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books_authors`
--

DROP TABLE IF EXISTS `books_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books_authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `books_authors_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `books_authors_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books_authors`
--

LOCK TABLES `books_authors` WRITE;
/*!40000 ALTER TABLE `books_authors` DISABLE KEYS */;
INSERT INTO `books_authors` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,2),(5,5,2),(6,6,3),(7,7,3),(8,8,3),(9,9,3),(10,10,3),(11,11,3),(12,12,3);
/*!40000 ALTER TABLE `books_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books_categories`
--

DROP TABLE IF EXISTS `books_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `books_categories_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `books_categories_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books_categories`
--

LOCK TABLES `books_categories` WRITE;
/*!40000 ALTER TABLE `books_categories` DISABLE KEYS */;
INSERT INTO `books_categories` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1),(9,9,1),(10,10,1),(11,11,1),(12,12,1);
/*!40000 ALTER TABLE `books_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books_publishers`
--

DROP TABLE IF EXISTS `books_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books_publishers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `publisher_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `publisher_id` (`publisher_id`),
  CONSTRAINT `books_publishers_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `books_publishers_ibfk_2` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books_publishers`
--

LOCK TABLES `books_publishers` WRITE;
/*!40000 ALTER TABLE `books_publishers` DISABLE KEYS */;
INSERT INTO `books_publishers` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,2),(5,5,2),(6,6,3),(7,7,3),(8,8,3),(9,9,3),(10,10,3),(11,11,3),(12,12,3);
/*!40000 ALTER TABLE `books_publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Fantasía'),(2,'Ciencia ficción'),(3,'Aventura');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `loan_date` date NOT NULL,
  `return_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `loans_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (1,1,1,'2020-01-01','2020-01-15'),(2,1,2,'2020-01-01','2020-01-15'),(3,1,3,'2020-01-01','2020-01-15'),(4,2,4,'2020-01-01','2020-01-15'),(5,2,5,'2020-01-01','2020-01-15'),(6,2,6,'2020-01-01','2020-01-15'),(7,3,7,'2020-01-01','2020-01-15'),(8,3,8,'2020-01-01','2020-01-15'),(9,3,9,'2020-01-01','2020-01-15'),(10,4,10,'2020-01-01','2020-01-15'),(11,4,11,'2020-01-01','2020-01-15'),(12,4,12,'2020-01-01','2020-01-15');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishers`
--

DROP TABLE IF EXISTS `publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publishers`
--

LOCK TABLES `publishers` WRITE;
/*!40000 ALTER TABLE `publishers` DISABLE KEYS */;
INSERT INTO `publishers` VALUES (1,'Planeta'),(2,'Debolsillo'),(3,'Alfaguara');
/*!40000 ALTER TABLE `publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Juan','juan@gmail.com'),(2,'Pedro','pedro@gmail.com'),(3,'Maria','maria@hotmail.com'),(4,'Ana','ana@utez.edu.mx'),(5,'Luis','luis@outlook.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-11 17:53:58
