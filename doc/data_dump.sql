-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: iss
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bloodcomponentquantity`
--

DROP TABLE IF EXISTS `bloodcomponentquantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bloodcomponentquantity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `IDTransfusionCenter` int(11) DEFAULT NULL,
  `idDonation` int(11) DEFAULT NULL,
  `idRequest` int(11) DEFAULT NULL,
  `aboBloodGroup` int(11) DEFAULT NULL,
  `bloodComponent` int(11) DEFAULT NULL,
  `bloodStatus` int(11) DEFAULT NULL,
  `expirationDate` datetime(6) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `rhBloodGroup` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfr3sxoh2t826ns24yk52j10fp` (`idDonation`),
  CONSTRAINT `FKfr3sxoh2t826ns24yk52j10fp` FOREIGN KEY (`idDonation`) REFERENCES `donation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodcomponentquantity`
--

LOCK TABLES `bloodcomponentquantity` WRITE;
/*!40000 ALTER TABLE `bloodcomponentquantity` DISABLE KEYS */;
INSERT INTO `bloodcomponentquantity` VALUES (4,2,2,0,2,0,1,'2018-01-15 00:00:00.000000',150,0),(5,2,2,0,2,2,1,'2018-04-10 00:00:00.000000',150,0),(6,2,2,0,2,1,1,'2018-02-21 00:00:00.000000',150,0),(7,4,3,0,2,0,0,'2018-03-21 00:00:00.000000',150,1),(9,4,3,0,2,1,0,'2018-04-27 00:00:00.000000',150,1),(10,2,4,0,1,0,0,'2018-04-29 00:00:00.000000',150,0),(12,2,4,0,1,1,0,'2018-06-05 00:00:00.000000',150,0),(13,4,1,0,1,1,0,'2017-12-04 00:00:00.000000',150,0),(14,2,3,0,2,2,0,'2018-06-14 00:00:00.000000',150,1),(15,2,5,0,0,0,0,'2018-04-22 00:00:00.000000',150,0),(17,2,5,0,0,1,0,'2018-05-29 00:00:00.000000',150,0),(18,4,6,0,1,0,0,'2018-03-09 00:00:00.000000',150,1),(19,4,6,0,1,2,0,'2018-06-02 00:00:00.000000',150,1),(20,4,6,0,1,1,0,'2018-04-15 00:00:00.000000',150,1),(21,2,7,0,2,0,0,'2018-04-14 00:00:00.000000',150,0),(22,2,7,0,2,2,0,'2018-07-08 00:00:00.000000',150,0),(23,2,7,0,2,1,0,'2018-05-21 00:00:00.000000',150,0),(24,2,8,0,2,0,0,'2018-05-15 00:00:00.000000',150,0),(25,2,8,0,2,2,0,'2018-08-08 00:00:00.000000',150,0),(26,2,8,0,2,1,0,'2018-06-21 00:00:00.000000',150,0),(27,2,9,0,3,0,0,'2017-03-17 00:00:00.000000',150,0),(28,2,9,0,3,2,0,'2017-06-10 00:00:00.000000',150,0),(29,2,9,0,3,1,0,'2017-04-23 00:00:00.000000',150,0),(30,4,4,0,1,2,0,'2018-07-23 00:00:00.000000',150,0),(31,4,1,0,1,0,0,'2017-10-28 00:00:00.000000',150,0),(32,4,1,0,1,2,0,'2018-01-21 00:00:00.000000',150,0),(33,4,10,0,2,0,1,'2017-12-13 00:00:00.000000',150,0),(34,4,10,0,2,2,1,'2018-03-08 00:00:00.000000',150,0),(35,4,10,0,2,1,1,'2018-01-19 00:00:00.000000',150,0),(37,2,11,0,2,2,0,'2018-07-30 00:00:00.000000',150,0),(38,2,11,0,2,1,0,'2018-06-12 00:00:00.000000',150,0),(39,4,11,0,2,0,0,'2018-05-06 00:00:00.000000',150,0),(40,4,5,0,0,2,0,'2018-07-16 00:00:00.000000',150,0),(41,2,12,0,0,0,0,'2018-05-25 00:00:00.000000',150,0),(42,2,12,0,0,2,0,'2018-08-18 00:00:00.000000',150,0),(43,2,12,0,0,1,0,'2018-07-01 00:00:00.000000',150,0);
/*!40000 ALTER TABLE `bloodcomponentquantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodrequest`
--

DROP TABLE IF EXISTS `bloodrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bloodrequest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bloodGroup` int(11) DEFAULT NULL,
  `bloodRequestStatus` int(11) DEFAULT NULL,
  `dateRequested` datetime(6) DEFAULT NULL,
  `leukocytesQuantity` int(11) DEFAULT NULL,
  `patientName` varchar(255) DEFAULT NULL,
  `plasmaQuantity` int(11) DEFAULT NULL,
  `rhBloodGroup` int(11) DEFAULT NULL,
  `thrombocytesQuantity` int(11) DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `sender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfoim65klmkxk2s07s7lhjhi29` (`receiver`),
  KEY `FKkk3ln57wpxd05trnokf2601pn` (`sender`),
  CONSTRAINT `FKfoim65klmkxk2s07s7lhjhi29` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`),
  CONSTRAINT `FKkk3ln57wpxd05trnokf2601pn` FOREIGN KEY (`sender`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodrequest`
--

LOCK TABLES `bloodrequest` WRITE;
/*!40000 ALTER TABLE `bloodrequest` DISABLE KEYS */;
INSERT INTO `bloodrequest` VALUES (4,2,0,'2018-05-25 10:30:54.000000',32,'Eduardo',0,0,431,NULL,1),(6,1,0,'2018-05-26 21:11:32.000000',50,'Cineva',0,1,1000,NULL,1),(8,1,0,'2018-05-29 22:48:00.000000',232,'dsadasd',0,0,0,4,1);
/*!40000 ALTER TABLE `bloodrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodtransfusioncenterprofile`
--

DROP TABLE IF EXISTS `bloodtransfusioncenterprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bloodtransfusioncenterprofile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodtransfusioncenterprofile`
--

LOCK TABLES `bloodtransfusioncenterprofile` WRITE;
/*!40000 ALTER TABLE `bloodtransfusioncenterprofile` DISABLE KEYS */;
INSERT INTO `bloodtransfusioncenterprofile` VALUES (1,'Strada Teodor Mihali 42-44, Cluj-Napoca 400000',2),(2,'Strada Clinicilor 3-5, Cluj-Napoca, Cluj 400000',4);
/*!40000 ALTER TABLE `bloodtransfusioncenterprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctorprofile`
--

DROP TABLE IF EXISTS `doctorprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctorprofile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `hospital` varchar(255) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctorprofile`
--

LOCK TABLES `doctorprofile` WRITE;
/*!40000 ALTER TABLE `doctorprofile` DISABLE KEYS */;
INSERT INTO `doctorprofile` VALUES (1,'Doctorescu','Calea Moților 32, Cluj-Napoca 400001',1,'Doctorul'),(2,'Fat','Strada Viilor 46-50, Cluj-Napoca 400066',3,'Sunny');
/*!40000 ALTER TABLE `doctorprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `donation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `HIVorAIDS` bit(1) DEFAULT NULL,
  `HTLV` bit(1) DEFAULT NULL,
  `donationDate` datetime(6) DEFAULT NULL,
  `donationStatus` int(11) DEFAULT NULL,
  `hepatitis` bit(1) DEFAULT NULL,
  `idTransfusionCenter` int(11) DEFAULT NULL,
  `levelALT` int(11) DEFAULT NULL,
  `syphilis` bit(1) DEFAULT NULL,
  `donor` int(11) DEFAULT NULL,
  `medicalQuestionnaire` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmxhxwtmpquxbaxdes0n3bdn0f` (`donor`),
  KEY `FKkv5pjnqlmt3yemwjhlfn43x0e` (`medicalQuestionnaire`),
  CONSTRAINT `FKkv5pjnqlmt3yemwjhlfn43x0e` FOREIGN KEY (`medicalQuestionnaire`) REFERENCES `medicalquestionnaire` (`id`),
  CONSTRAINT `FKmxhxwtmpquxbaxdes0n3bdn0f` FOREIGN KEY (`donor`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,'\0','\0','2017-10-23 00:00:00.000000',2,'\0',2,12,'\0',5,1),(2,'','\0','2018-01-10 00:00:00.000000',2,'\0',2,12,'',5,1),(3,'\0','\0','2018-03-16 00:00:00.000000',2,'\0',4,15,'\0',5,1),(4,'\0','\0','2018-04-24 00:00:00.000000',2,'\0',2,13,'\0',5,1),(5,'\0','\0','2018-04-17 00:00:00.000000',2,'\0',2,20,'\0',6,2),(6,'\0','\0','2018-03-04 00:00:00.000000',2,'\0',4,12,'\0',7,3),(7,'\0','\0','2018-04-09 00:00:00.000000',2,'\0',2,20,'\0',7,3),(8,'\0','\0','2018-05-10 00:00:00.000000',2,'\0',2,20,'\0',7,3),(9,'\0','\0','2017-03-12 00:00:00.000000',2,'\0',2,12,'\0',8,4),(10,'','','2017-12-08 00:00:00.000000',2,'',4,10,'',8,4),(11,'\0','\0','2018-05-01 00:00:00.000000',2,'\0',2,20,'\0',9,5),(12,'\0','\0','2018-05-20 00:00:00.000000',2,'\0',2,23,'\0',8,4);
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donorprofile`
--

DROP TABLE IF EXISTS `donorprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `donorprofile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP` varchar(255) DEFAULT NULL,
  `aboBloodGroup` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `rhBloodGroup` int(11) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donorprofile`
--

LOCK TABLES `donorprofile` WRITE;
/*!40000 ALTER TABLE `donorprofile` DISABLE KEYS */;
INSERT INTO `donorprofile` VALUES (22,'1925264556633',NULL,'Strada Gheorghe Doja 7B, Întorsura Buzăului 525300','1995-03-19 00:00:00.000000','hedling4@webnode.com','Twitching',180,9,'Hazel','Romania','0723556894','Strada Gheorghe Doja 7B, Întorsura Buzăului 525300',NULL,80),(24,'1970826548848',0,'Strada Iuliu Maniu 1, Cluj-Napoca','2002-05-29 00:00:00.000000','cchadburn5@amazon.de','	Alex',213,5,'	Cawtheray','Romania','0278554887','Strada Iuliu Maniu 1, Cluj-Napoca',0,59),(26,'1970825252525',1,'Strada Matei Corvin 10, Cluj-Napoca 400000','1996-10-10 00:00:00.000000','	cduckinfieldd@macromedia.com','Godfry',180,8,'	Whyffen','Romania','0741564851','Strada Matei Corvin 10, Cluj-Napoca 400000',0,50);
/*!40000 ALTER TABLE `donorprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalquestionnaire`
--

DROP TABLE IF EXISTS `medicalquestionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicalquestionnaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `STD` bit(1) DEFAULT NULL,
  `alcohol` varchar(255) DEFAULT NULL,
  `alcoholQuantity` int(11) DEFAULT NULL,
  `asthma` bit(1) DEFAULT NULL,
  `bLTFWhen` varchar(255) DEFAULT NULL,
  `bLTFWhere` varchar(255) DEFAULT NULL,
  `beenRefusedDelayed` bit(1) DEFAULT NULL,
  `birthdayOfChild` datetime(6) DEFAULT NULL,
  `bloodPressure` varchar(255) DEFAULT NULL,
  `bornLivedTravelledForeign` bit(1) DEFAULT NULL,
  `convulsionsNervousDisease` bit(1) DEFAULT NULL,
  `registrationDate` datetime(6) DEFAULT NULL,
  `dentalTreatment` bit(1) DEFAULT NULL,
  `detention` bit(1) DEFAULT NULL,
  `diabetisUlcerCancer` bit(1) DEFAULT NULL,
  `drugTreatment` bit(1) DEFAULT NULL,
  `healthy` bit(1) DEFAULT NULL,
  `heartDiseaseHLBloodPressure` bit(1) DEFAULT NULL,
  `hepatitis` bit(1) DEFAULT NULL,
  `icterusTuberculosisReumaticFeverMalaria` bit(1) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `inexplicableFever` bit(1) DEFAULT NULL,
  `intravenousDrugs` bit(1) DEFAULT NULL,
  `lastAlcoholConsume` datetime(6) DEFAULT NULL,
  `lastMenstruation` datetime(6) DEFAULT NULL,
  `multipleSexualPartners` bit(1) DEFAULT NULL,
  `numberOfSexualPartners` int(11) DEFAULT NULL,
  `postDonationAttention` bit(1) DEFAULT NULL,
  `pregnancy` bit(1) DEFAULT NULL,
  `receivedBlood` bit(1) DEFAULT NULL,
  `sexWorker` bit(1) DEFAULT NULL,
  `sexualPartnerChanged` bit(1) DEFAULT NULL,
  `sexualPartnerHIVHepatitis` bit(1) DEFAULT NULL,
  `sexualPartnerIntravenousDrug` bit(1) DEFAULT NULL,
  `sexualPartnerSexWorker` bit(1) DEFAULT NULL,
  `smoker` bit(1) DEFAULT NULL,
  `stroke` bit(1) DEFAULT NULL,
  `surgery` bit(1) DEFAULT NULL,
  `tattoos` bit(1) DEFAULT NULL,
  `unexpectedLossWeight` bit(1) DEFAULT NULL,
  `vaccines` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalquestionnaire`
--

LOCK TABLES `medicalquestionnaire` WRITE;
/*!40000 ALTER TABLE `medicalquestionnaire` DISABLE KEYS */;
INSERT INTO `medicalquestionnaire` VALUES (1,'\0','',0,'\0','','','\0',NULL,'10/6','\0','\0','2018-05-10 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',5,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(2,'\0','',0,'\0','','','\0',NULL,'12/8','\0','\0','2018-05-11 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',6,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(3,'\0','',0,'\0','','','\0',NULL,'12/8','\0','\0','2018-05-11 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',7,'\0','\0','2018-05-11 00:00:00.000000','2018-05-07 00:00:00.000000','\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(4,'\0','',0,'\0','','','\0',NULL,'10/2','\0','\0','2018-05-11 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',8,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(5,'\0','',0,'\0','','','\0',NULL,'12/8','\0','\0','2018-05-11 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',9,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','','\0','\0','','\0','\0');
/*!40000 ALTER TABLE `medicalquestionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passHash` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'doctor',2,'doctor'),(2,'center',3,'center'),(3,'doctor2',2,'doctor2'),(4,'center2',3,'center2'),(5,'donor',1,'donor'),(8,'donor2',1,'donor2'),(9,'donor3',1,'donor3');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-29 22:59:05
