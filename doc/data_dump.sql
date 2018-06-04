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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodcomponentquantity`
--

LOCK TABLES `bloodcomponentquantity` WRITE;
/*!40000 ALTER TABLE `bloodcomponentquantity` DISABLE KEYS */;
INSERT INTO `bloodcomponentquantity` VALUES (44,2,13,9,0,0,0,'2018-02-10 00:00:00.000000',0,0),(45,2,13,0,0,2,0,'2018-05-06 00:00:00.000000',150,0),(46,2,13,9,0,1,0,'2018-03-19 00:00:00.000000',0,0),(47,2,14,0,0,0,0,'2018-03-23 00:00:00.000000',150,0),(48,2,14,0,0,2,0,'2018-06-16 00:00:00.000000',150,0),(49,2,14,9,0,1,0,'2018-04-29 00:00:00.000000',0,0),(50,2,15,9,0,0,0,'2017-10-22 00:00:00.000000',0,0),(51,2,15,0,0,2,0,'2018-01-15 00:00:00.000000',150,0),(52,2,15,9,0,1,0,'2017-11-28 00:00:00.000000',0,0),(53,2,16,0,0,0,0,'2018-01-15 00:00:00.000000',150,0),(54,2,16,0,0,2,0,'2018-04-10 00:00:00.000000',150,0),(55,2,16,9,0,1,0,'2018-02-21 00:00:00.000000',0,0),(56,2,17,0,1,0,0,'2017-11-12 00:00:00.000000',150,1),(57,2,17,0,1,2,0,'2018-02-05 00:00:00.000000',150,1),(58,2,17,0,1,1,0,'2017-12-19 00:00:00.000000',150,1),(59,2,18,0,0,0,0,'2018-06-08 00:00:00.000000',150,0),(60,2,18,0,0,2,0,'2018-09-01 00:00:00.000000',150,0),(61,2,18,0,0,1,0,'2018-07-15 00:00:00.000000',150,0),(62,2,19,0,1,0,0,'2018-06-06 00:00:00.000000',150,0),(63,2,19,0,1,2,0,'2018-08-30 00:00:00.000000',150,0),(64,2,19,0,1,1,0,'2018-07-13 00:00:00.000000',150,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodrequest`
--

LOCK TABLES `bloodrequest` WRITE;
/*!40000 ALTER TABLE `bloodrequest` DISABLE KEYS */;
INSERT INTO `bloodrequest` VALUES (9,0,2,'2018-06-02 10:39:15.000000',0,'John Catherpillar',0,0,0,2,1),(10,1,0,'2018-06-02 10:39:51.000000',150,'Yohn Snow',450,0,0,4,3),(11,1,0,'2018-06-02 10:47:16.000000',0,'Cineva departe',150,1,0,2,1),(12,1,1,'2018-06-03 16:57:47.000000',300,'Ma boi',0,0,150,4,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (13,'\0','\0','2018-02-05 00:00:00.000000',2,'\0',2,20,'\0',9,6),(14,'\0','\0','2018-03-18 00:00:00.000000',2,'\0',2,18,'\0',9,6),(15,'\0','\0','2017-10-17 00:00:00.000000',2,'\0',2,21,'\0',5,7),(16,'\0','\0','2018-01-10 00:00:00.000000',2,'\0',2,18,'\0',5,7),(17,'\0','\0','2017-11-07 00:00:00.000000',2,'\0',2,20,'\0',8,8),(18,'\0','\0','2018-06-03 00:00:00.000000',2,'\0',2,5,'\0',5,9),(19,'\0','\0','2018-06-01 00:00:00.000000',2,'\0',2,32,'\0',8,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donorprofile`
--

LOCK TABLES `donorprofile` WRITE;
/*!40000 ALTER TABLE `donorprofile` DISABLE KEYS */;
INSERT INTO `donorprofile` VALUES (22,'1925264556633',0,'Strada Gheorghe Doja 7B, Întorsura Buz?ului 525300','1995-03-19 00:00:00.000000','hedling4@webnode.com','Twitching',180,9,'Hazel','Romania','0723556894','Strada Gheorghe Doja 7B, Întorsura Buz?ului 525300',0,80),(24,'1970826548848',0,'Strada Iuliu Maniu 1, Cluj-Napoca','2002-05-29 00:00:00.000000','cchadburn5@amazon.de','Alex',213,5,'Cawtheray','Romania','0278554887','Strada Iuliu Maniu 1, Cluj-Napoca',0,59),(26,'1970825252525',1,'Strada Matei Corvin 10, Cluj-Napoca 400000','1996-10-10 00:00:00.000000','raresik97@gmail.com','Godfry',180,8,'Whyffen','Romania','0741564851','Strada Matei Corvin 10, Cluj-Napoca 400000',0,50);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalquestionnaire`
--

LOCK TABLES `medicalquestionnaire` WRITE;
/*!40000 ALTER TABLE `medicalquestionnaire` DISABLE KEYS */;
INSERT INTO `medicalquestionnaire` VALUES (6,'\0','',0,'\0','','','\0',NULL,'12/8','\0','\0','2018-06-02 00:00:00.000000','','\0','\0','\0','','\0','\0','\0',9,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(7,'\0','',0,'\0','','','\0',NULL,'12/7','\0','\0','2018-06-02 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',5,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0',''),(8,'\0','',0,'\0','','','\0',NULL,'11/8','\0','\0','2018-06-02 00:00:00.000000','\0','\0','\0','\0','','\0','\0','\0',8,'\0','\0',NULL,NULL,'\0',0,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0',''),(9,'','',0,'','','','\0',NULL,'456','\0','\0','2018-06-03 00:00:00.000000','','\0','','','\0','','','\0',5,'\0','\0',NULL,NULL,'',0,'\0','','','\0','\0','\0','','\0','','\0','','','\0','\0');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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

-- Dump completed on 2018-06-04 16:38:10
