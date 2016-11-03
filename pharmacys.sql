-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 192.168.1.20    Database: pharmacys
-- ------------------------------------------------------
-- Server version	5.5.50-0ubuntu0.14.04.1

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
-- Table structure for table `CATEGORY`
--

DROP TABLE IF EXISTS `CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CATEGORY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `IMG` varchar(500) DEFAULT 'http://localhost:8080/pharmacys/img/img_no_aviable.png',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORY`
--

LOCK TABLES `CATEGORY` WRITE;
/*!40000 ALTER TABLE `CATEGORY` DISABLE KEYS */;
INSERT INTO `CATEGORY` VALUES (1,'BABY','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/baby_cat.png'),(2,'NUTRITION','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/nutrition_cat.png'),(3,'HEALTH','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/health_cat.png'),(4,'NATURAL','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/natural_cat.png'),(5,'HYGIENE','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/hygiene_cat.png'),(6,'COSMETIC','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/cosmetic_cat.png'),(7,'VETERINARY','http://hugomaldonado.ddns.net:8080/pharmacys/img/categories/veterinary_cat.png');
/*!40000 ALTER TABLE `CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `INVENTORY`
--

DROP TABLE IF EXISTS `INVENTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INVENTORY` (
  `PHARMACYID` varchar(9) NOT NULL,
  `PRODUCTID` int(11) NOT NULL,
  `PRICE` float DEFAULT '0',
  `STOCK` int(11) DEFAULT '0',
  `QUERYCOUNT` int(11) DEFAULT '0',
  PRIMARY KEY (`PHARMACYID`,`PRODUCTID`),
  KEY `INVENTORY_PRODUCTID_idx` (`PRODUCTID`),
  CONSTRAINT `INVENTORY_CIF` FOREIGN KEY (`PHARMACYID`) REFERENCES `PHARMACY` (`CIF`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `INVENTORY_PRODUCTID` FOREIGN KEY (`PRODUCTID`) REFERENCES `PRODUCT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INVENTORY`
--

LOCK TABLES `INVENTORY` WRITE;
/*!40000 ALTER TABLE `INVENTORY` DISABLE KEYS */;
INSERT INTO `INVENTORY` VALUES ('12378965B',14,6.15,0,2),('12378965B',17,4.38,1,1),('12378965B',18,24.2,8,0),('12378965B',19,13.85,18,0),('12378965B',20,6.38,21,0),('12378965B',21,1.93,74,0),('12378965B',22,5.45,34,0),('12378965B',23,20,7,0),('12378965B',24,11.19,1,2),('47581526G',15,3.45,0,3);
/*!40000 ALTER TABLE `INVENTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_PRODUCT`
--

DROP TABLE IF EXISTS `ORDER_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_PRODUCT` (
  `ORDERID` int(11) NOT NULL,
  `PRODUCTID` int(11) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  PRIMARY KEY (`ORDERID`,`PRODUCTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_PRODUCT`
--

LOCK TABLES `ORDER_PRODUCT` WRITE;
/*!40000 ALTER TABLE `ORDER_PRODUCT` DISABLE KEYS */;
INSERT INTO `ORDER_PRODUCT` VALUES (50,14,1),(51,14,3),(52,15,4),(53,14,1),(54,15,2),(55,14,4),(56,14,5),(57,14,1),(58,14,1),(59,15,4),(60,15,4),(61,14,4),(62,15,4),(63,14,4),(64,14,4),(65,14,5),(66,14,1),(67,14,1),(68,14,1),(69,14,3),(70,14,3),(71,14,3),(72,14,4),(73,14,3),(74,14,1),(75,14,4),(76,14,1),(77,14,1),(78,14,1),(79,14,1),(80,19,4),(81,18,4),(81,23,4),(82,17,3),(82,24,1),(83,18,1),(83,23,1),(84,17,1);
/*!40000 ALTER TABLE `ORDER_PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PHARMACY`
--

DROP TABLE IF EXISTS `PHARMACY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PHARMACY` (
  `CIF` varchar(9) NOT NULL,
  `NAME` varchar(120) NOT NULL,
  `PHONE_NUMBER` int(11) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `START_SCHEDULE` tinyint(2) DEFAULT '0',
  `END_SCHEDULE` tinyint(2) DEFAULT '0',
  `LATITUDE` double DEFAULT '0',
  `LONGITUDE` double DEFAULT '0',
  `ADDRESS` varchar(200) DEFAULT '',
  `URL_IMG` varchar(500) DEFAULT 'http://localhost:8080/pharmacys/img/img_no_aviable.png',
  PRIMARY KEY (`CIF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PHARMACY`
--

LOCK TABLES `PHARMACY` WRITE;
/*!40000 ALTER TABLE `PHARMACY` DISABLE KEYS */;
INSERT INTO `PHARMACY` VALUES ('12378965B','Farmacia Santamaría',958154949,'Somos una de las farmacias con mayor proyección y nivel de actividad en el sector farmacéutico granadino y andaluz. Líderes en formulación magistral, ofreciendo servicios de laboratorio a farmacias de toda España y en venta de medicamentos y productos de parafarmacia.',0,24,37.19628838536527,-3.6236281089843487,'Calle Periodista Eugenio Selles, 6, 18014 Granada, Granada, España','http://hugomaldonado.ddns.net:8080/pharmacys/data/santamaria.jpg'),('47581526G','Farmacia Virgen de la Cabeza',958204709,'Los servicios que ofrecemos cuentan con el respaldo y la seriedad de profesionales sanitarios. Contamos con productos eleborados en nuestra propia farmacia. Disponemos de servicio a domicilio y contamos con laboratorio propio donde realizamos las fórmulas magistrales elaboradas por farmacéuticos especializados',9,21,37.1920368,-3.6262307,'C/ Doctor Medina Olmos nº 17, 18015 Granada','http://hugomaldonado.ddns.net:8080/pharmacys/data/virgendelacabeza.jpg');
/*!40000 ALTER TABLE `PHARMACY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCT`
--

DROP TABLE IF EXISTS `PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(160) NOT NULL,
  `DESCRIPTION` longtext NOT NULL,
  `LABORATORY` varchar(80) NOT NULL,
  `UNITS` varchar(5) NOT NULL,
  `EXPIRATION_DATE` date NOT NULL,
  `SIZE` int(11) NOT NULL,
  `LOT` varchar(45) NOT NULL,
  `URL_IMG` varchar(500) DEFAULT 'http://localhost:8080/pharmacys/img/img_no_aviable.png',
  `CATEGORYID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_idx` (`CATEGORYID`),
  CONSTRAINT `ID` FOREIGN KEY (`CATEGORYID`) REFERENCES `CATEGORY` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT`
--

LOCK TABLES `PRODUCT` WRITE;
/*!40000 ALTER TABLE `PRODUCT` DISABLE KEYS */;
INSERT INTO `PRODUCT` VALUES (14,'Papilla 8 Cereales con Galleta María ','SANUTRI elabora sus Papillas con los mejores cereales, utilizando un exclusivo proceso de hidrólisis enzimática que garantiza una excelente digestibilidad y tolerancia, así como un elevado valor nutricional. Con fructooligosacáridos que estimulan el desarrollo de la flora bacteriana beneficiosa y favorecen el tránsito intestinal. Además, aumentan la absorción del calcio. Están enriquecidas en Calcio, Hierro, Fósforo y 12 vitaminas, siguiendo las últimas recomendaciones del Comité de la Sociedad Europea de Gastroenterología, Hepatología y Nutrición Pediátrica (ESPGHAN). No hace falta añadir azúcar, ya que su proceso de elaboración potencia el dulzor de los cereales.','Sanutri','gr','2017-06-15',300,'48dawef5d8s5','http://hugomaldonado.ddns.net:8080/pharmacys/data/sanutri.jpg',1),(15,'Papilla 8 Cereales y Miel 4 Frutas ','Papilla de 8 cereales con miel, que le aporta, además de su suave sabor, todas las propiedades de la miel. Además incorpora frutas que aportan vitaminas y minerales además de un agradable sabor. Está enriquecida con calcio y vitaminas. Fácil digestión para el bebé. Rápida disolución en leche.','Nutribén','gr','2019-02-07',600,'5125d8a6ew2d8','http://hugomaldonado.ddns.net:8080/pharmacys/data/nutriben.jpg',1),(17,'Xhekpon crema facial 40ml','El uso de una crema facial te aporta el aspecto que tanto deseas. Si deseas aplicar un tratamiento anti-edad para combatir las arrugas y otras imperfecciones, Xhekpon® crema hidratante facial te garantiza una piel joven e hidratada.\r\n\r\nGracias a una composición a base de colágeno encargado de hidratar y regenerar las células de tu piel, aloe vera y centella asiática, esta crema facial reduce y previene la aparición de arrugas. Además, funciona como reafirmante de los tejidos cutáneos y rellena las líneas de expresión.','Xhekpon','ml','2020-03-30',40,'8F746S','http://hugomaldonado.ddns.net:8080/pharmacys/data/xhecapon.jpg',6),(18,'Eucerin loción enriquecida pH5 1l + REGALO 200ml','Cuando la piel suele enrojecerse con mucha facilidad, mostrar irregularidades en su textura, adquiere una apariencia áspera, presenta descamaciones e incluso sufre de cuperosis, suele implicar que se trata de una piel sensible. Por eso, es my importante que si tienes estos síntomas, ofrezcas a tu piel cuidados diarios que mejoren su hidratación, la calmen y suavicen.\r\n\r\nEucerin® pH5 loción enriquecida está específicamente preparada y diseñada para estimular la regeneración cutánea, protegera frente la deshidratación y devolverle su bienestar natural.\r\n\r\nLa fórmula de esta solución está enriquecida con pH5 enzyme protect, una sustancia que mejora las defensas naturales de la piel y le ayudan a combatir las agresiones externas.\r\n\r\n¡Máxima hidratación y protección para tu piel!','Eucerin','l','2028-01-31',1,'F876AH','http://hugomaldonado.ddns.net:8080/pharmacys/data/eucerin.jpg',1),(19,' Vichy Homme bálsamo aftershave piel sensible','Las irritaciones y las rojeces configuran dos de los problemas más habituales que sufren los hombres cuando se afeitan. El uso de un bálsamo reparador que equilibre el estado del cutis después de rasurar el pelo es fundamental para devolverle al rostro un aspecto saludable e hidratado. \r\n\r\nVichy Homme bálsamo after shave, enriquecido con Agua Termal y Calcio, configura un tratamiento muy calmante y dermo-reparador, ideal para proteger a las pieles masculinas más sensibles de tu casa. Utiliza este fantástico producto y garantiza la frescura y la pureza de un afeitado sin irritaciones. \r\n\r\nCuida la piel tras el afeitado.','Vichy','ml','2042-04-13',750,'8765FV','http://hugomaldonado.ddns.net:8080/pharmacys/data/vichy.jpg',6),(20,'Vaginesil gel hidratante vaginal 30g','Aunque parezca increíble, las mujeres de todas las edades pueden sufrir las molestias de la sequedad vaginal. El estrés, las hormonas y ciertos medicamentos son causas comunes de esta sequedad.\r\n\r\nSi este es tu caso, Vaginesil gel hidratante vaginal es un gel ligero y nada graso que está especialmente formulado para el cuerpo de la mujer y que proporciona alivio instantáneo de las molestias producidas por la sequedad vaginal. También facilita las relaciones sexuales y tiene un pH fisiológico que respetará tu mucosa.\r\n\r\nAdemás, Vaginesil gel hidratante vaginal está enriquecido con vitamina E y aloe vera, sustancias que hidratarán en profundidad tu cuerpo pero que no dejarán ningún sabor ni olor en tu zona vaginal.\r\n\r\n*Inodoro e incoloro.\r\n\r\n¡Disfruta de una salud íntima perfecta!','Avon','gr','2017-03-02',30,'87896A','http://hugomaldonado.ddns.net:8080/pharmacys/data/vaginesil.jpg',5),(21,'Salvelox apósito textil elástico 100cmx6cm 1ud','Cuando nuestra piel sufre pequeños cortes de afeitado, granitos, piercings, punciones de análisis, etc... hay que protegerla para evitar una infección. Los apósitos de Salvelox nos ayudarán a resguardar la herida de bacterias, contaminación u otros agentes externos que pueden provocar que empeore. \r\n\r\nFormada por una tira de 100cmx6cm recortable, puede ser cómodamente recortada, para dividirla en tiritas del tamaño concreto de la herida. Además, su tela téxtil es flexible y adaptable, cubriendo de forma precisa a la herida.\r\n\r\nLos apósitos textil elástico demuestran unas características diferenciales muy claras, pues su extrema flexibilidad y elasticidad los hace ideales para adaptarse a zonas de constante flexión como codos o rodillas. Su compresa tiene una alta capacidad de absorción y transpiración.\r\n\r\nElige los apósitos de Salvelox.','Salvelox','units','2040-03-19',30,'879865F','http://hugomaldonado.ddns.net:8080/pharmacys/data/salvelox.jpg',3),(22,'Compeed Extreme ampollas 5uds','dsaddadas','Compeed','units','2060-03-09',5,'868767U','http://hugomaldonado.ddns.net:8080/pharmacys/img/img_no_aviable.png',3),(23,'Aposán humidificador ultrasónico 1ud','-','PosanLab','units','2222-02-22',1,'979879B','http://hugomaldonado.ddns.net:8080/pharmacys/data/aposan.jpg',1),(24,'Pilexil champú anticaída 500ml','ok','HeadLexil','ml','2023-02-22',500,'G678AF','http://hugomaldonado.ddns.net:8080/pharmacys/data/pilexil.jpg',1);
/*!40000 ALTER TABLE `PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESERVATION`
--

DROP TABLE IF EXISTS `RESERVATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESERVATION` (
  `PHARMACYID` varchar(9) NOT NULL,
  `PRODUCTID` int(11) NOT NULL,
  `USERID` varchar(100) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  PRIMARY KEY (`PHARMACYID`,`PRODUCTID`,`USERID`),
  KEY `ID_idx` (`PRODUCTID`),
  KEY `EMAIL_RESERVATION_idx` (`USERID`),
  CONSTRAINT `CIF_RESERVATION` FOREIGN KEY (`PHARMACYID`) REFERENCES `PHARMACY` (`CIF`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `EMAIL_RESERVATION` FOREIGN KEY (`USERID`) REFERENCES `USER` (`EMAIL`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ID_RESERVATION` FOREIGN KEY (`PRODUCTID`) REFERENCES `PRODUCT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVATION`
--

LOCK TABLES `RESERVATION` WRITE;
/*!40000 ALTER TABLE `RESERVATION` DISABLE KEYS */;
INSERT INTO `RESERVATION` VALUES ('12378965B',14,'hugomc92@gmail.com',1),('47581526G',15,'hugomc92@gmail.com',2);
/*!40000 ALTER TABLE `RESERVATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `EMAIL` varchar(100) NOT NULL,
  `NAME` varchar(80) NOT NULL DEFAULT '',
  `SURNAME` varchar(180) DEFAULT NULL,
  `PASSWORD` tinytext NOT NULL,
  `RESETHASH` tinytext,
  `ROLE` tinyint(1) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `CIFPHARMACY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES ('a@a.com','Aaa','Aaaa','fa585d89c851dd338a70dcf535aa2a92fee7836dd6aff1226583e88e0996293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe',NULL,0,1,NULL),('hugomc92@gmail.com','Hugo','Maldonado','cee94e077a15a51332f21fb92f654d8456f1767348b4c5fd139ecbf8233a3d041b5b738264954e5ef573694453b6c28da3040c939314ee871c749ff9ff84c4b4','4b6317f7c8cd66f37604',1,1,'12378965B'),('lsusns@slid.dkdb','Lshdbs','Kshzbzl','cee94e077a15a51332f21fb92f654d8456f1767348b4c5fd139ecbf8233a3d041b5b738264954e5ef573694453b6c28da3040c939314ee871c749ff9ff84c4b4',NULL,0,1,NULL),('mcapeltu@gmail.com','Manuel','Capel','940660b356ec51bbd8ab337363b4d45d5cc24c628c81f9c11cbc3f937cdebb6328ee293d791bb034e26fe105f8fbd1f1623e924c5c4729436c2bb009bd889da2',NULL,0,1,NULL),('roman@gmail.com','Román','Arranz','cee94e077a15a51332f21fb92f654d8456f1767348b4c5fd139ecbf8233a3d041b5b738264954e5ef573694453b6c28da3040c939314ee871c749ff9ff84c4b4',NULL,1,1,'47581526G');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ORDER`
--

DROP TABLE IF EXISTS `USER_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ORDER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERID` varchar(100) NOT NULL,
  `PHARMACYID` varchar(9) NOT NULL,
  `DATE` date NOT NULL,
  `PRICE` float NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `EMAIL_idx` (`USERID`),
  KEY `CIF_idx` (`PHARMACYID`),
  CONSTRAINT `CIF` FOREIGN KEY (`PHARMACYID`) REFERENCES `PHARMACY` (`CIF`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `EMAIL` FOREIGN KEY (`USERID`) REFERENCES `USER` (`EMAIL`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ORDER`
--

LOCK TABLES `USER_ORDER` WRITE;
/*!40000 ALTER TABLE `USER_ORDER` DISABLE KEYS */;
INSERT INTO `USER_ORDER` VALUES (50,'hugomc92@gmail.com','12378965B','2016-05-12',5.93),(51,'hugomc92@gmail.com','12378965B','2016-05-12',18.45),(52,'hugomc92@gmail.com','47581526G','2016-05-12',13.8),(53,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(54,'hugomc92@gmail.com','47581526G','2016-05-12',6.9),(55,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(56,'hugomc92@gmail.com','12378965B','2016-05-12',30.75),(57,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(58,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(59,'hugomc92@gmail.com','47581526G','2016-05-12',13.8),(60,'hugomc92@gmail.com','47581526G','2016-05-12',13.8),(61,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(62,'hugomc92@gmail.com','47581526G','2016-05-12',13.8),(63,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(64,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(65,'hugomc92@gmail.com','12378965B','2016-05-12',30.75),(66,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(67,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(68,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(69,'hugomc92@gmail.com','12378965B','2016-05-12',18.45),(70,'hugomc92@gmail.com','12378965B','2016-05-12',18.45),(71,'hugomc92@gmail.com','12378965B','2016-05-12',18.45),(72,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(73,'hugomc92@gmail.com','12378965B','2016-05-12',18.45),(74,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(75,'hugomc92@gmail.com','12378965B','2016-05-12',24.6),(76,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(77,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(78,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(79,'hugomc92@gmail.com','12378965B','2016-05-12',6.15),(80,'hugomc92@gmail.com','12378965B','2016-05-13',55.4),(81,'hugomc92@gmail.com','12378965B','2016-05-13',176.8),(82,'hugomc92@gmail.com','12378965B','2016-05-13',24.33),(83,'hugomc92@gmail.com','12378965B','2016-05-13',44.2),(84,'a@a.com','12378965B','2016-06-10',4.38);
/*!40000 ALTER TABLE `USER_ORDER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-02 18:12:29
