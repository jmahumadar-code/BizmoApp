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
-- Table structure for table `tmaster_services`
--

DROP TABLE IF EXISTS `tmaster_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmaster_services` (
  `idtmaster_services` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `fk_family` int(11) DEFAULT NULL,
  `more_detail` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtmaster_services`),
  KEY `fk_tmaster_services_tfamily_services1_idx` (`fk_family`)
) ENGINE=InnoDB AUTO_INCREMENT=2081 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmaster_services`
--

LOCK TABLES `tmaster_services` WRITE;
/*!40000 ALTER TABLE `tmaster_services` DISABLE KEYS */;
INSERT INTO `tmaster_services` VALUES (10,'Alarmas y Seguridad','Alarmas y Seguridad',1000,'\0'),(20,'Automotriz','Automotriz',1000,'\0'),(30,'Construccion','Construccion',1000,'\0'),(40,'Electricidad y Gas','Electricidad y Gas',1000,'\0'),(50,'Pisos y Alfombras','Pisos y Alfombras',1000,'\0'),(60,'Vidrios y Ventanas','Vidrios y Ventanas',1000,'\0'),(70,'Pintura y Empastado','Pintura y Empastado',1000,'\0'),(80,'Plomeria','Plomeria',1000,'\0'),(90,'Techumbres','Techumbres',1000,'\0'),(100,'Otros Oficios','Otros Oficios',1000,''),(500,'Animales y mascotas','Animales y mascotas',2000,'\0'),(510,'Arquitectura y diseno','Arquitectura y diseno',2000,'\0'),(520,'Nineras','Nineras',2000,'\0'),(530,'Limpieza','Limpieza',2000,'\0'),(540,'Computacion','Computacion',2000,'\0'),(550,'Mudanza y almacenamieto','Mudanza y almacenamieto',2000,'\0'),(560,'Asesoras del hogas','Asesoras del hogas',2000,'\0'),(570,'Cuidado de personas','Cuidado de personas',2000,'\0'),(580,'Jardineria','Jardineria',2000,'\0'),(590,'Reparacion y mantencion','Reparacion y mantencion',2000,'\0'),(600,'Otros servicios domesticos','Otros servicios domesticos',2000,''),(1000,'Cocteleria','Cocteleria',3000,'\0'),(1010,'Artistas y espectaculos','Artistas y espectaculos',3000,'\0'),(1020,'Locales, Fiestas y suministros','Locales, Fiestas y suministros',3000,'\0'),(1030,'Fotografia y video','Fotografia y video',3000,'\0'),(1040,'Matrimonios','Matrimonios',3000,'\0'),(1050,'Otros servicios de eventos','Otros servicios de eventos',3000,''),(1500,'Belleza y relajacion','Belleza y relajacion',4000,'\0'),(1510,'Consejo de vida y asesoramiento','Consejo de vida y asesoramiento',4000,'\0'),(1520,'Servicios medicos','Servicios medicos',4000,'\0'),(1530,'Entrenadores personales','Entrenadores personales',4000,'\0'),(1540,'Perdida de peso','Perdida de peso',4000,'\0'),(1550,'Otros servicios de salud','Otros servicios de salud',4000,''),(2000,'Turismo y aventura','Turismo y aventura',99000,''),(2010,'Campo y agricultura','Campo y agricultura',99000,''),(2020,'Financieros','Financieros',99000,''),(2030,'Legales','Legales',99000,''),(2040,'Regalos','Regalos',99000,''),(2050,'Bienes raices','Bienes raices',99000,''),(2060,'Espiritualidad','Espiritualidad',99000,''),(2070,'Ensenanza e instruccion','Ensenanza e instruccion',99000,''),(2080,'Otros','Otros',99000,'');
/*!40000 ALTER TABLE `tmaster_services` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-28 12:58:07
