USE `ibsys`;

truncate table arbeitsplatz_daten;
truncate table bestellung;
truncate table dispo_drei;
truncate table dispo_drei_ergebniss;
truncate table dispo_eins;
truncate table dispo_eins_ergebniss;
truncate table dispo_ergebniss;
truncate table dispo_zwei;
truncate table dispo_zwei_ergebniss;
truncate table lager_disposition;
truncate table mengen_stueckliste;
truncate table prognose;
truncate table teil;
truncate table teil_ist_in_teil;


-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: ibsys
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Dumping data for table `arbeitsplatz_daten`
--

LOCK TABLES `arbeitsplatz_daten` WRITE;
/*!40000 ALTER TABLE `arbeitsplatz_daten` DISABLE KEYS */;
INSERT INTO `arbeitsplatz_daten` VALUES (1,0.45,0.55,0.7,0.9,0.05,0.01),(2,0.45,0.55,0.7,0.9,0.05,0.01),(3,0.45,0.55,0.7,0.9,0.05,0.01),(4,0.45,0.55,0.7,0.9,0.05,0.01),(6,0.45,0.55,0.7,0.9,0.3,0.1),(7,0.45,0.55,0.7,0.9,0.3,0.1),(8,0.45,0.55,0.7,0.9,0.3,0.1),(9,0.45,0.55,0.7,0.9,0.8,0.25),(10,0.45,0.55,0.7,0.9,0.3,0.1),(11,0.45,0.55,0.7,0.9,0.3,0.1),(12,0.45,0.55,0.7,0.9,0.3,0.1),(13,0.45,0.55,0.7,0.9,0.5,0.15),(14,0.45,0.55,0.7,0.9,0.05,0.01),(15,0.45,0.55,0.7,0.9,0.05,0.01);
/*!40000 ALTER TABLE `arbeitsplatz_daten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bestellung`
--

LOCK TABLES `bestellung` WRITE;
/*!40000 ALTER TABLE `bestellung` DISABLE KEYS */;
/*!40000 ALTER TABLE `bestellung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_drei`
--

LOCK TABLES `dispo_drei` WRITE;
/*!40000 ALTER TABLE `dispo_drei` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_drei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_drei_ergebniss`
--

LOCK TABLES `dispo_drei_ergebniss` WRITE;
/*!40000 ALTER TABLE `dispo_drei_ergebniss` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_drei_ergebniss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_eins`
--

LOCK TABLES `dispo_eins` WRITE;
/*!40000 ALTER TABLE `dispo_eins` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_eins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_eins_ergebniss`
--

LOCK TABLES `dispo_eins_ergebniss` WRITE;
/*!40000 ALTER TABLE `dispo_eins_ergebniss` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_eins_ergebniss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_ergebniss`
--

LOCK TABLES `dispo_ergebniss` WRITE;
/*!40000 ALTER TABLE `dispo_ergebniss` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_ergebniss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_zwei`
--

LOCK TABLES `dispo_zwei` WRITE;
/*!40000 ALTER TABLE `dispo_zwei` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_zwei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dispo_zwei_ergebniss`
--

LOCK TABLES `dispo_zwei_ergebniss` WRITE;
/*!40000 ALTER TABLE `dispo_zwei_ergebniss` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispo_zwei_ergebniss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `lager_disposition`
--

LOCK TABLES `lager_disposition` WRITE;
/*!40000 ALTER TABLE `lager_disposition` DISABLE KEYS */;
INSERT INTO `lager_disposition` VALUES (300,300,50,1.8,9,0.4,2,21),(300,300,50,1.7,9,0.4,2,22),(300,300,50,1.2,6,0.2,1,23),(6100,6100,100,3.2,16,0.3,2,24),(3600,3600,50,0.9,5,0.2,1,25),(1800,1800,75,0.9,5,0.2,1,27),(4500,4500,50,1.7,9,0.4,2,28),(2700,2700,50,2.1,11,0.5,3,32),(900,900,75,1.9,10,0.5,3,33),(22000,22000,50,1.6,8,0.3,2,34),(3600,3600,75,2.2,11,0.4,2,35),(900,900,100,1.2,6,0.1,1,36),(900,900,50,1.5,8,0.3,2,37),(300,300,50,1.7,9,0.4,2,38),(900,1800,75,1.5,8,0.3,2,39),(900,900,50,1.7,9,0.2,1,40),(900,900,50,0.9,5,0.2,1,41),(1800,1800,50,1.2,6,0.3,2,42),(1900,2700,75,2,10,0.5,3,43),(2700,900,50,1,5,0.2,1,44),(900,900,50,1.7,9,0.3,2,45),(900,900,50,0.9,5,0.3,2,46),(900,900,50,1.1,6,0.1,1,47),(1800,1800,75,1,5,0.2,1,48),(600,600,50,1.6,8,0.4,2,52),(22000,22000,50,1.6,8,0.2,1,53),(600,600,50,1.7,9,0.3,2,57),(22000,22000,50,1.6,8,0.5,3,58),(1800,1800,50,0.7,4,0.2,1,59);
/*!40000 ALTER TABLE `lager_disposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `mengen_stueckliste`
--

LOCK TABLES `mengen_stueckliste` WRITE;
/*!40000 ALTER TABLE `mengen_stueckliste` DISABLE KEYS */;
INSERT INTO `mengen_stueckliste` VALUES (1,4,1),(1,7,1),(1,10,1),(1,13,1),(1,16,1),(1,17,1),(1,18,1),(1,26,1),(1,49,1),(1,50,1),(1,51,1),(1,21,1),(1,24,7),(1,25,4),(1,27,2),(1,28,4),(1,32,3),(1,35,4),(1,36,1),(1,37,1),(1,38,1),(1,39,2),(1,40,1),(1,41,1),(1,42,2),(1,43,1),(1,44,3),(1,45,1),(1,46,1),(1,47,1),(1,48,2),(1,52,2),(1,53,72),(1,59,2),(2,5,1),(2,8,1),(2,11,1),(2,14,1),(2,16,1),(2,17,1),(2,19,1),(2,26,1),(2,54,1),(2,55,1),(2,56,1),(2,22,1),(2,24,7),(2,25,4),(2,27,2),(2,28,5),(2,32,3),(2,35,4),(2,36,1),(2,37,1),(2,38,1),(2,39,2),(2,40,1),(2,41,1),(2,42,2),(2,43,1),(2,44,3),(2,45,1),(2,46,1),(2,47,1),(2,48,2),(2,57,2),(2,58,72),(2,59,2),(3,6,1),(3,9,1),(3,12,1),(3,15,1),(3,16,1),(3,17,1),(3,20,1),(3,26,1),(3,29,1),(3,30,1),(3,31,1),(3,23,1),(3,24,7),(3,25,4),(3,27,2),(3,28,6),(3,32,3),(3,33,2),(3,34,72),(3,35,4),(3,36,1),(3,37,1),(3,38,1),(3,39,2),(3,40,1),(3,41,1),(3,42,2),(3,43,1),(3,44,3),(3,45,1),(3,46,1),(3,47,1),(3,48,2),(3,59,2);
/*!40000 ALTER TABLE `mengen_stueckliste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `prognose`
--

LOCK TABLES `prognose` WRITE;
/*!40000 ALTER TABLE `prognose` DISABLE KEYS */;
/*!40000 ALTER TABLE `prognose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `teil`
--

LOCK TABLES `teil` WRITE;
/*!40000 ALTER TABLE `teil` DISABLE KEYS */;
INSERT INTO `teil` VALUES (1,'P','Kinderfahrrad','NIX',156.13),(2,'P','Damenfahrrad','NIX',163.33),(3,'P','Herrenfahrrad','NIX',165.08),(4,'E','Hinterradgruppe','K',40.85),(5,'E','Hinterradgruppe','D',39.85),(6,'E','Hinterradgruppe','H',40.85),(7,'E','Vorderradgruppe','K',35.85),(8,'E','Vorderradgruppe','D',35.85),(9,'E','Vorderradgruppe','H',35.85),(10,'E','Schutzblech h.','K',12.4),(11,'E','Schutzblech h.','D',14.65),(12,'E','Schutzblech h.','H',14.65),(13,'E','Schutzblech v.','K',12.4),(14,'E','Schutzblech v.','D',14.65),(15,'E','Schutzblech v.','H',14.65),(16,'E','Lenker cpl.','KDH',7.02),(17,'E','Sattel cpl.','KDH',7.16),(18,'E','Rahmen','K',13.15),(19,'E','Rahmen','D',14.35),(20,'E','Rahmen','H',15.55),(21,'K','Kette','K',5),(22,'K','Kette','D',6.5),(23,'K','Kette','H',6.5),(24,'K','Mutter 3/8','KDH',0.06),(25,'K','Scheibe 3/8','KDH',0.06),(26,'E','Pedal cpl.','KDH',10.5),(27,'K','Schraube 3/8','KDH',0.1),(28,'K','Rohr 3/4','KDH',1.2),(29,'E','Vorderrad mont.','H',69.29),(30,'E','Rahmen u. Raeder','H',127.53),(31,'E','Fahrrad o. Ped.','H',144.42),(32,'K','Farbe','KDH',0.75),(33,'K','Felde cpl','H',22),(34,'K','Speiche','H',0.1),(35,'K','Nabe','KDH',1),(36,'K','Freilauf','KDH',8),(37,'K','Gabel','KDH',1.5),(38,'K','Welle','KDH',1.5),(39,'K','Blech','KDH',1.5),(40,'K','Lenker','KDH',2.5),(41,'K','Mutter 3/4','KDH',0.06),(42,'K','Griff','KDH',0.1),(43,'K','Sattel','KDH',5),(44,'K','Stange 1/2','KDH',0.5),(45,'K','Mutter 1/4','KDH',0.06),(46,'K','Schraube 1/4','KDH',0.1),(47,'K','Zahnkranz','KDH',3.5),(48,'K','Pedal','KDH',1.5),(49,'E','Vorderrad cpl.','K',64.64),(50,'E','Rahmen u. Raeder','K',120.63),(51,'E','Fahrrad o. Pedal','K',137.47),(52,'K','Felge cpl.','K',22),(53,'K','Speiche','K',0.1),(54,'E','Vorderrad cpl.','D',68.09),(55,'E','Rahmen u. Raeder.','D',125.33),(56,'E','Fahrrad o. Pedal','D',142.67),(57,'K','Felge cpl.','D',22),(58,'K','Speiche','D',0.1),(59,'K','Schweissdraht','KDH',0.15);
/*!40000 ALTER TABLE `teil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `teil_ist_in_teil`
--

LOCK TABLES `teil_ist_in_teil` WRITE;
/*!40000 ALTER TABLE `teil_ist_in_teil` DISABLE KEYS */;
INSERT INTO `teil_ist_in_teil` VALUES (13,39,1),(13,32,1),(18,28,3),(18,59,2),(18,32,1),(7,52,1),(7,53,36),(7,35,2),(7,37,1),(7,38,1),(49,13,1),(49,18,1),(49,7,1),(49,24,2),(49,25,2),(4,52,1),(4,53,36),(4,35,2),(4,36,1),(10,39,1),(10,32,1),(17,43,1),(17,44,1),(17,45,1),(17,46,1),(16,28,1),(16,24,1),(16,40,1),(16,41,1),(16,42,2),(50,49,1),(50,4,1),(50,10,1),(50,24,2),(50,25,2),(51,17,1),(51,16,1),(51,50,1),(51,24,1),(51,27,1),(26,44,2),(26,48,2),(26,47,1),(1,51,1),(1,26,1),(1,21,1),(1,24,1),(1,27,1),(2,22,1),(2,24,1),(2,27,1),(2,26,1),(2,56,1),(56,24,1),(56,27,1),(56,16,1),(56,17,1),(56,55,1),(55,24,2),(55,25,2),(55,5,1),(55,11,1),(55,54,1),(5,35,2),(5,36,1),(5,57,1),(5,58,36),(11,32,1),(11,39,1),(54,24,2),(54,25,2),(54,8,1),(54,14,1),(54,19,1),(8,35,2),(8,37,1),(8,38,1),(8,57,1),(8,58,36),(14,32,1),(14,39,1),(19,28,4),(19,32,1),(19,59,2),(3,23,1),(3,24,1),(3,27,1),(3,26,1),(3,31,1),(31,24,1),(31,27,1),(31,16,1),(31,17,1),(31,30,1),(30,24,2),(30,25,2),(30,6,1),(30,12,1),(30,29,1),(6,33,1),(6,34,36),(6,35,2),(6,36,1),(12,32,1),(12,39,1),(29,24,2),(29,25,2),(29,9,1),(29,15,1),(29,20,1),(9,33,1),(9,34,36),(9,35,2),(9,37,1),(9,38,1),(15,32,1),(15,39,1),(20,28,5),(20,32,1),(20,59,2);
/*!40000 ALTER TABLE `teil_ist_in_teil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-03 12:34:17
