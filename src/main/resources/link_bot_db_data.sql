CREATE DATABASE  IF NOT EXISTS `link_bot_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `link_bot_db`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: link_bot_db
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `discord_servers`
--

LOCK TABLES `discord_servers` WRITE;
/*!40000 ALTER TABLE `discord_servers` DISABLE KEYS */;
INSERT INTO `discord_servers` VALUES (1,632542166501294109,'n word gamers'),(2,967993942207520860,'bot test');
/*!40000 ALTER TABLE `discord_servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `discord_users`
--

LOCK TABLES `discord_users` WRITE;
/*!40000 ALTER TABLE `discord_users` DISABLE KEYS */;
INSERT INTO `discord_users` VALUES (1,1,492555962058539009,'lega#3949'),(2,3,360991771054047233,'mcg#9976'),(3,3,514779821654409247,'josephyqf#5307'),(4,3,321820053429551105,'fpg#0239'),(5,3,538275792304472064,'Overturn#1923'),(6,3,498717370031013900,'juel yappy#8812'),(7,3,369043350474719233,'Thomas#7527');
/*!40000 ALTER TABLE `discord_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `links`
--

LOCK TABLES `links` WRITE;
/*!40000 ALTER TABLE `links` DISABLE KEYS */;
INSERT INTO `links` VALUES (1,'https://twitter.com/OneneChan/status/1534483452229488640?t=ORh2q62t2mJ3fuAsBwWkCw&s=19','super good hatsune miku art',1,'2022-06-28 12:26:11'),(2,'https://www.pixiv.net/en/artworks/99318866','loli neko',1,'2022-06-28 14:06:43'),(3,'https://twitter.com/mmu_nkr/status/1541966767463223297?s=21&t=shYvNZsW1CjNoxiyrQ02Xg','dabian moment',2,'2022-06-30 14:17:00'),(4,'https://twitter.com/mychristian2/status/1537253627810615296?s=20&t=MfPPdYm-mztU10XiUqPPVQ','waifu',1,'2022-06-30 14:20:15'),(5,'https://twitter.com/9ujin_/status/1542132599971545088?s=21&t=FQ9kdoH7A-aO5hQOLWR5pQ','osnsjosjd',2,'2022-06-30 15:31:22'),(6,'https://twitter.com/naoillus/status/1539402854712201218','very nice gura art',1,'2022-07-01 00:45:31'),(7,'https://twitter.com/oTgjHkMpJpTIfVO/status/1518129348418490368','very big booba',1,'2022-07-01 11:11:17'),(8,'https://twitter.com/ririkocafe/status/1542688207866736640?s=21&t=njor2Oips0AWQHLUYFu4lw','ririko',2,'2022-07-01 13:19:01'),(9,'https://twitter.com/hem_oo_/status/1542088419941027840?s=21&t=njor2Oips0AWQHLUYFu4lw','shiroko swimsuit',2,'2022-07-01 13:20:23'),(10,'https://twitter.com/raiya_atelier/status/1542885655378001921?s=21&t=I8Tpn0R0y_9hF4k7qxgQ6w','keqing bikini',2,'2022-07-02 00:53:40'),(11,'https://twitter.com/dangmyo/status/1543205531913166848?s=21&t=Epv-rFL3PbX2yC-GnVTxTQ','deepsea',2,'2022-07-02 23:19:21'),(12,'https://twitter.com/20_chain/status/1542863295278788608?s=21&t=wLYeztTVvWywlaa8qBJjUg','based art',2,'2022-07-03 01:50:11'),(13,'https://twitter.com/zero15101991/status/1542109254911692800?s=20&t=ZzTSbD6_llwE02T3f2jzkA','gura',1,'2022-07-03 03:14:43'),(14,'https://www.pixiv.net/en/artworks/99459363','see thru glasses',1,'2022-07-03 03:15:42'),(15,'https://twitter.com/eredhen1/status/1542756961392934913?s=21&t=1u92DFC7UINZAe1YVmn8Zw','student suisei',1,'2022-07-03 05:16:03'),(16,'https://twitter.com/Nardack/status/1542342244812095488','desuwaaaa',1,'2022-07-03 05:16:47'),(17,'https://twitter.com/chyoellll/status/1541591492170375174?s=20&t=eTN1B2a2KcOe5qzxG1jGiA','yor with a side of anya',1,'2022-07-03 05:21:13'),(18,'https://twitter.com/ui_shig/status/1543219148335235072?s=20&t=BBv2zhKXxvDqtefksKYQyQ','shuba duck',1,'2022-07-03 11:54:47'),(19,'https://twitter.com/eimon_23/status/1543259725164187649?s=21&t=nQJz-apjWDr3XD9Nl7x-RA','ayame switch sports',1,'2022-07-03 18:02:00'),(20,'https://twitter.com/lbkei1234/status/1501937596481818635?s=20&t=zUOznzLVgWme9LPShZSDPQ','shady fauna',1,'2022-07-03 18:07:56'),(21,'https://twitter.com/ROiNA23338376/status/1543309858002599936?s=20&t=ELqQzRu3rL5z2wKC7qTsZg','nekosuisei feeeeet',1,'2022-07-03 18:30:49'),(22,'https://twitter.com/zixsiga/status/1542333145747378176','kronmei scales',1,'2022-07-03 18:31:38'),(23,'https://twitter.com/NAMIORII/status/1543193015677698048?s=20&t=2srZczzSfnDQZ0ZwoJNq6Q','pool irys',1,'2022-07-03 18:40:48'),(24,'https://twitter.com/Saruei_/status/1543378603823583233','booba moomers',1,'2022-07-03 18:49:45'),(25,'https://twitter.com/kakao_rantan/status/1543550189872750592?s=21&t=6MNXx7O-u_LANHsL5xPM8Q','kko',2,'2022-07-03 20:37:50'),(26,'https://twitter.com/marumaru_wanwan/status/1543257512686026753?s=21&t=6MNXx7O-u_LANHsL5xPM8Q','fischl skin',2,'2022-07-03 22:27:45'),(27,'https://twitter.com/Chocoan09/status/1318177537051086848','suisei',1,'2022-07-03 22:59:21'),(28,'https://twitter.com/akitahika44/status/1542440304430780417','double blond',1,'2022-07-03 23:01:00'),(29,'https://twitter.com/ImInRize/status/1543303774655156226?s=20&t=AHuDd1NhSApYdL3kJKIMsw','moom ghost',1,'2022-07-04 00:14:56'),(30,'https://twitter.com/hd_1735/status/1548196976629469184?s=20&t=l_1YiqOH2Pv6O0uxkrbT8A','ayaka',2,'2022-07-17 00:56:13');
/*!40000 ALTER TABLE `links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `prefixes`
--

LOCK TABLES `prefixes` WRITE;
/*!40000 ALTER TABLE `prefixes` DISABLE KEYS */;
INSERT INTO `prefixes` VALUES (1,'!',1),(9,'!',2);
/*!40000 ALTER TABLE `prefixes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (2,'Admin'),(1,'Owner'),(3,'User');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-13 20:57:20
