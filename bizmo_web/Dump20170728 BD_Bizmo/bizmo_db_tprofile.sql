-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: bizmo_db
-- ------------------------------------------------------
-- Server version	5.6.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tprofile`
--

DROP TABLE IF EXISTS `tprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tprofile` (
  `idprofile` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `fk_uid` varchar(36) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `fk_type_service` int(11) DEFAULT NULL,
  `valuation` smallint(6) DEFAULT '1',
  PRIMARY KEY (`idprofile`),
  KEY `fk_profile_tusers_idx` (`fk_uid`),
  KEY `fk_tprofile_tmaster_services1_idx` (`fk_type_service`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tprofile`
--

LOCK TABLES `tprofile` WRITE;
/*!40000 ALTER TABLE `tprofile` DISABLE KEYS */;
INSERT INTO `tprofile` VALUES (1,'Fotografia','2017-05-10','48e892ef-2dee-11e7-a5fd-00163ca3ae26','Cuidado de Ancianos',1030,1),(2,'Arstistas','2017-05-10','48e892ef-2dee-11e7-a5fd-00163ca3ae26','Mejor fotografo de la zona',1010,1),(3,'Fotografo','2017-05-10','7bf6635f-2d47-11e7-a5fd-00163ca3ae26','Fotografo Profesional clase 4',1030,1),(4,'Abogados','2017-05-12','a5be187a-369b-11e7-93d2-00163ca3ae26','Abogado Ligitante en lo Penal',2030,1);
/*!40000 ALTER TABLE `tprofile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-28 12:58:18
