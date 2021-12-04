-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: social_media_system
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `report_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcq6hwtukw527pld91vsq40l0` (`report_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKcq6hwtukw527pld91vsq40l0` FOREIGN KEY (`report_id`) REFERENCES `reports` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (5,'寒すぎる','2021-11-29 13:36:14.548349','2021-11-29 13:36:14.548349',8,1),(6,'おなかすいた','2021-12-01 10:43:15.271293','2021-12-01 10:43:15.271293',5,1),(7,'こんにちは','2021-12-01 10:46:34.696163','2021-12-01 10:46:34.696163',4,1),(8,'寒いね','2021-12-02 15:02:30.296725','2021-12-02 15:02:30.296725',8,8),(9,'がんばって\r\n！！！','2021-12-03 00:08:25.262065','2021-12-03 00:08:25.262065',7,1),(10,'雨降るらしいよ','2021-12-03 00:10:13.130522','2021-12-03 00:10:13.130522',7,1),(11,'気を付けてね','2021-12-03 00:10:28.554371','2021-12-03 00:10:28.554371',7,1),(12,'俺も走りに行くか','2021-12-03 00:10:57.892561','2021-12-03 00:10:57.892561',7,1),(13,'こんばんはー','2021-12-03 00:13:16.061149','2021-12-03 00:13:16.061149',4,1),(14,'ああ','2021-12-03 00:17:57.481284','2021-12-03 00:17:57.481284',7,1),(15,'ああ','2021-12-03 00:18:00.289701','2021-12-03 00:18:00.289701',7,1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `report_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKni3s9svq896efowdht7t78oqi` (`report_id`),
  KEY `FKk7du8b8ewipawnnpg76d55fus` (`user_id`),
  CONSTRAINT `FKk7du8b8ewipawnnpg76d55fus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKni3s9svq896efowdht7t78oqi` FOREIGN KEY (`report_id`) REFERENCES `reports` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (7,'2021-11-29 18:40:43.386897','2021-11-29 18:40:43.386897',5,1),(9,'2021-11-30 15:31:23.218605','2021-11-30 15:31:23.218605',8,8),(16,'2021-12-03 00:08:04.183591','2021-12-03 00:08:04.183591',7,1),(26,'2021-12-03 18:04:29.226731','2021-12-03 18:04:29.226731',8,1);
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follows`
--

DROP TABLE IF EXISTS `follows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follows` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `follow_id` int NOT NULL,
  `follower_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKftq54lsotuw5587xq4l1lto7k` (`follow_id`),
  KEY `FKqnkw0cwwh6572nyhvdjqlr163` (`follower_id`),
  CONSTRAINT `FKftq54lsotuw5587xq4l1lto7k` FOREIGN KEY (`follow_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKqnkw0cwwh6572nyhvdjqlr163` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follows`
--

LOCK TABLES `follows` WRITE;
/*!40000 ALTER TABLE `follows` DISABLE KEYS */;
INSERT INTO `follows` VALUES (4,'2021-12-01 19:52:49.467826','2021-12-01 19:52:49.467826',4,2),(7,'2021-12-01 19:58:54.127379','2021-12-01 19:58:54.127379',4,1),(8,'2021-12-02 00:27:16.504376','2021-12-02 00:27:16.504376',1,6),(10,'2021-12-02 00:27:28.301408','2021-12-02 00:27:28.301408',1,3),(13,'2021-12-02 00:36:21.271218','2021-12-02 00:36:21.271218',1,4),(14,'2021-12-02 15:02:38.513244','2021-12-02 15:02:38.513244',8,2),(15,'2021-12-02 17:01:02.885109','2021-12-02 17:01:02.885109',1,2),(16,'2021-12-02 17:58:36.194163','2021-12-02 17:58:36.194163',1,7),(17,'2021-12-03 00:12:36.587515','2021-12-03 00:12:36.587515',1,8),(18,'2021-12-03 13:48:18.384900','2021-12-03 13:48:18.384900',9,1);
/*!40000 ALTER TABLE `follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2o32rer9hfweeylg7x8ut8rj2` (`user_id`),
  CONSTRAINT `FK2o32rer9hfweeylg7x8ut8rj2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,'おなかすいた','2021-11-25 16:36:35.494768','眠たい','2021-11-27 13:47:41.801911',1),(2,'ペース上げて頑張ろう','2021-11-25 17:21:04.106985','もうこんな時間','2021-11-25 17:21:04.106985',7),(4,'wolde','2021-11-25 18:15:17.528589','hello','2021-11-25 18:15:17.528589',4),(5,'サッポロ一番食べたい','2021-11-25 18:16:09.769857','ラーメン食べたい','2021-11-25 18:16:09.769857',3),(7,'夜走りにいくか','2021-11-25 18:17:47.027699','運動頑張る','2021-11-25 18:17:47.027699',2),(8,'間違えて寒い','2021-11-25 18:19:02.878967','服装','2021-11-25 18:19:02.878967',6),(13,'test用','2021-12-02 15:02:11.947903','test','2021-12-02 15:02:11.947903',8);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `delete_flag` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2021-11-25 15:42:45.480491',0,'suzu@gmail.com','鈴木　翔太','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 15:42:45.480491'),(2,'2021-11-25 16:15:55.137138',0,'shimada@gmail.com','島田　一郎','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 16:15:55.137138'),(3,'2021-11-25 16:16:32.573920',0,'tanaka@gmail.com','田中　太郎','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 16:16:32.573920'),(4,'2021-11-25 16:17:08.725610',0,'hirameki@gmail.com','煌木　次郎','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 16:17:08.725610'),(5,'2021-11-25 16:17:48.499479',0,'sato@gmail.com','佐藤　花子','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 16:17:48.499479'),(6,'2021-11-25 16:37:32.236120',0,'nakamura@gmail.com','中村　三郎','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 16:37:32.236120'),(7,'2021-11-25 17:20:15.558721',0,'hayashi@gmail.com','林　小太郎','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-11-25 17:20:15.558721'),(8,'2021-11-30 15:30:56.086241',0,'shimada@shimada.com','aa','3A070AD1F47177818E3ADFDAB1F5273B1D061F588513F8E9C4A6A328A56ACF87','2021-11-30 15:30:56.086241'),(9,'2021-12-03 13:47:44.136433',0,'dousakakuninnyou@dousakakuninn','動作確認用テスト名前','A911E4DC0D4BC758AA1A9910EF5DB5A715969252A4BCF7BDC1DE8C104824C0E9','2021-12-03 13:47:44.136433');
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

-- Dump completed on 2021-12-03 20:10:55
