-- MySQL dump 10.13  Distrib 5.6.19, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: ibsys
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu1

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

DROP DATABASE IF EXISTS `ibsys`;
CREATE DATABASE `ibsys`;

USE `ibsys`;

--
-- Table structure for table `arbeitsplatz_daten`
--

DROP TABLE IF EXISTS `arbeitsplatz_daten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arbeitsplatz_daten` (
  `nummer` int(11) NOT NULL,
  `lohn_schicht_eins` double DEFAULT NULL,
  `lohn_schicht_zwei` double DEFAULT NULL,
  `lohn_schicht_drei` double DEFAULT NULL,
  `lohn_ueberstunden` double DEFAULT NULL,
  `var_masch_kos` double DEFAULT NULL,
  `fix_masch_kos` double DEFAULT NULL,
  PRIMARY KEY (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bestellung`
--

DROP TABLE IF EXISTS `bestellung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bestellung` (
  `periode` int(11) DEFAULT NULL,
  `bestell_typ` varchar(1) DEFAULT NULL,
  `menge` int(11) DEFAULT NULL,
  `teile_nummer_fk` int(11) DEFAULT NULL,
  `eingetroffen` tinyint(1) NOT NULL DEFAULT '0',
  KEY `teile_nummer_fk` (`teile_nummer_fk`),
  CONSTRAINT `bestellung_ibfk_1` FOREIGN KEY (`teile_nummer_fk`) REFERENCES `teil` (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_drei`
--

DROP TABLE IF EXISTS `dispo_drei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_drei` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_drei_ergebniss`
--

DROP TABLE IF EXISTS `dispo_drei_ergebniss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_drei_ergebniss` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_eins`
--

DROP TABLE IF EXISTS `dispo_eins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_eins` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_eins_ergebniss`
--

DROP TABLE IF EXISTS `dispo_eins_ergebniss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_eins_ergebniss` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_ergebniss`
--

DROP TABLE IF EXISTS `dispo_ergebniss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_ergebniss` (
  `nummer` int(11) DEFAULT NULL,
  `menge` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_zwei`
--

DROP TABLE IF EXISTS `dispo_zwei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_zwei` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispo_zwei_ergebniss`
--

DROP TABLE IF EXISTS `dispo_zwei_ergebniss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispo_zwei_ergebniss` (
  `reihe` int(11) NOT NULL AUTO_INCREMENT,
  `nummer` int(11) DEFAULT NULL,
  `geplante_lagermenge` int(11) DEFAULT NULL,
  `lagerbestand_ende_vorperiode` int(11) DEFAULT NULL,
  `auftraege_warteschlange` int(11) DEFAULT NULL,
  `auftrage_bearbeitung` int(11) DEFAULT NULL,
  `produktionsauftrag_naechste_periode` int(11) DEFAULT NULL,
  `vertriebswunsch` int(11) DEFAULT NULL,
  `helpint` int(11) DEFAULT NULL,
  PRIMARY KEY (`reihe`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lager_disposition`
--

DROP TABLE IF EXISTS `lager_disposition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lager_disposition` (
  `lager_menge` int(11) DEFAULT NULL,
  `diskont_menge` int(11) DEFAULT NULL,
  `bestellkosten` double DEFAULT NULL,
  `wiederbeschaffungszeit_periode` double DEFAULT NULL,
  `wiederbeschaffungszeit_tage` int(11) DEFAULT NULL,
  `abweichung_periode` double DEFAULT NULL,
  `abweichung_tage` int(11) DEFAULT NULL,
  `teile_nummer_fk` int(11) DEFAULT NULL,
  KEY `teile_nummer_fk` (`teile_nummer_fk`),
  CONSTRAINT `lager_disposition_ibfk_1` FOREIGN KEY (`teile_nummer_fk`) REFERENCES `teil` (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mengen_stueckliste`
--

DROP TABLE IF EXISTS `mengen_stueckliste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mengen_stueckliste` (
  `fahrrad_nummer` int(11) DEFAULT NULL,
  `teile_nummer` int(11) DEFAULT NULL,
  `anzahl` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prognose`
--

DROP TABLE IF EXISTS `prognose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prognose` (
  `aktuelle_periode` int(11) DEFAULT NULL,
  `teile_nummer_fk` int(11) DEFAULT NULL,
  `aktuelle_menge` int(11) DEFAULT NULL,
  `plus_eins_menge` int(11) DEFAULT NULL,
  `plus_zwei_menge` int(11) DEFAULT NULL,
  `plus_drei_menge` int(11) DEFAULT NULL,
  KEY `teile_nummer_fk` (`teile_nummer_fk`),
  CONSTRAINT `prognose_ibfk_1` FOREIGN KEY (`teile_nummer_fk`) REFERENCES `teil` (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teil`
--

DROP TABLE IF EXISTS `teil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teil` (
  `nummer` int(11) NOT NULL,
  `buchstabe` varchar(1) DEFAULT NULL,
  `bezeichnung` varchar(100) DEFAULT NULL,
  `verwendung` varchar(3) DEFAULT NULL,
  `wert` double DEFAULT NULL,
  PRIMARY KEY (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teil_ist_in_teil`
--

DROP TABLE IF EXISTS `teil_ist_in_teil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teil_ist_in_teil` (
  `nummer_neu` int(11) NOT NULL,
  `nummer` int(11) NOT NULL,
  `menge` int(11) DEFAULT NULL,
  KEY `nummer` (`nummer`),
  KEY `nummer_neu` (`nummer_neu`),
  CONSTRAINT `teil_ist_in_teil_ibfk_1` FOREIGN KEY (`nummer`) REFERENCES `teil` (`nummer`),
  CONSTRAINT `teil_ist_in_teil_ibfk_2` FOREIGN KEY (`nummer_neu`) REFERENCES `teil` (`nummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-01 21:29:59
