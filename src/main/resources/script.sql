-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: fifa_wc
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(1) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `groups_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,'a'),(2,'b'),(3,'c'),(4,'d'),(5,'e'),(6,'f'),(7,'g'),(8,'h');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `home_id` int(11) DEFAULT NULL,
  `away_id` int(11) DEFAULT NULL,
  `home_result` int(11) DEFAULT NULL,
  `away_result` int(11) DEFAULT NULL,
  `home_penalty` int(11) DEFAULT NULL,
  `away_penalty` int(11) DEFAULT NULL,
  `winner_id` int(11) DEFAULT NULL,
  `round_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `match_teams_id_fk` (`home_id`),
  KEY `match_teams_id_fk_2` (`away_id`),
  KEY `match_teams_id_fk_3` (`winner_id`),
  KEY `matches_rounds_id_fk` (`round_id`),
  CONSTRAINT `match_teams_id_fk` FOREIGN KEY (`home_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `match_teams_id_fk_2` FOREIGN KEY (`away_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `match_teams_id_fk_3` FOREIGN KEY (`winner_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `matches_rounds_id_fk` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=607 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` text COLLATE utf8mb4_bin,
  `team_id` int(11) DEFAULT NULL,
  `goal_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `players_teams_id_fk` (`team_id`),
  CONSTRAINT `players_teams_id_fk` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=735 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'Igor Akinfeev',1,50),(2,'Vladimir Gabulov',1,47),(3,'Andrey Lunev',1,41),(4,'Sergei Ignashevich',1,35),(5,'Mario Fernandes',1,37),(6,'Vladimir Granat',1,53),(7,'Fyodor Kudryashov',1,52),(8,'Andrei Semyonov',1,53),(9,'Igor Smolnikov',1,47),(10,'Ilya Kutepov',1,40),(11,'Aleksandr Yerokhin',1,31),(12,'Yuri Zhirkov',1,36),(13,'Daler Kuzyaev',1,46),(14,'Aleksandr Golovin',1,49),(15,'Alan Dzagoev',1,51),(16,'Roman Zobnin',1,48),(17,'Aleksandr Samedov',1,41),(18,'Yuri Gazinsky',1,52),(19,'Anton Miranchuk',1,50),(20,'Denis Cheryshev',1,46),(21,'Artyom Dzyuba',1,44),(22,'Aleksei Miranchuk',1,35),(23,'Fyodor Smolov',1,46),(24,'Mohammed Al-Owais',2,48),(25,'Yasser Al-Musailem',2,27),(26,'Abdullah Al-Mayuf',2,37),(27,'Mansoor Al-Harbi',2,45),(28,'Yasser Al-Shahrani',2,41),(29,'Mohammed Al-Burayk',2,45),(30,'Motaz Hawsawi',2,33),(31,'Osama Hawsawi',2,33),(32,'Ali Al-Bulaihi',2,29),(33,'Omar Othman',2,33),(34,'Abdullah Alkhaibari',2,46),(35,'Abdulmalek Alkhaibri',2,30),(36,'Abdullah Otayf',2,41),(37,'Taiseer Al-Jassam',2,30),(38,'Hussain Al-Moqahwi',2,45),(39,'Salman Al-Faraj',2,44),(40,'Mohamed Kanno',2,41),(41,'Hatan Bahbir',2,39),(42,'Salem Al-Dawsari',2,40),(43,'Yahia Al-Shehri',2,44),(44,'Fahad Al-Muwallad',2,35),(45,'Mohammad Al-Sahlawi',2,43),(46,'Muhannad Assiri',2,30),(47,'Essam El Hadary',3,32),(48,'Mohamed El-Shennawy',3,52),(49,'Sherif Ekramy',3,40),(50,'Ahmed Fathi',3,36),(51,'Abdallah Said',3,42),(52,'Saad Samir',3,49),(53,'Ayman Ashraf',3,41),(54,'Mohamed Abdel-Shafy',3,32),(55,'Ahmed Hegazi',3,57),(56,'Ali Gabr',3,51),(57,'Ahmed Elmohamady',3,34),(58,'Omar Gaber',3,34),(59,'Tarek Hamed',3,39),(60,'Mahmoud Shikabala',3,38),(61,'Sam Morsy',3,47),(62,'Mohamed Elneny',3,59),(63,'Mahmoud Kahraba',3,53),(64,'Ramadan Sobhi',3,46),(65,'Trezeguet',3,47),(66,'Amr Warda',3,41),(67,'Marwan Mohsen',3,35),(68,'Mohamed Salah',3,35),(69,'Mahmoud Elwensh',3,39),(70,'Fernando Muslera',4,49),(71,'Martin Silva',4,33),(72,'Martin Campana',4,39),(73,'Diego Godin',4,31),(74,'Sebastian Coates',4,45),(75,'Jose Maria Gimenez',4,32),(76,'Maximiliano Pereira',4,38),(77,'Gaston Silva',4,43),(78,'Martin Caceres',4,28),(79,'Guillermo Varela',4,35),(80,'Nahitan Nandez',4,50),(81,'Lucas Torreira',4,40),(82,'Matias Vecino',4,43),(83,'Rodrigo Bentancur',4,52),(84,'Carlos Sanchez',4,45),(85,'Giorgian De Arrascaeta',4,59),(86,'Diego Laxalt',4,47),(87,'Cristian Rodriguez',4,40),(88,'Jonathan Urretaviscaya',4,48),(89,'Cristhian Stuani',4,51),(90,'Maximiliano Gomez',4,38),(91,'Edinson Cavani',4,41),(92,'Luis Suarez',4,43),(93,'Anthony Lopes',5,44),(94,'Beto',5,40),(95,'Rui Patricio',5,45),(96,'Bruno Alves',5,36),(97,'Cedric Soares',5,43),(98,'Jose Fonte',5,45),(99,'Mario Rui',5,39),(100,'Pepe',5,31),(101,'Raphael Guerreiro',5,37),(102,'Ricardo Pereira',5,46),(103,'Ruben Dias',5,48),(104,'Adrien Silva',5,45),(105,'Bruno Fernandes',5,35),(106,'Joao Mario',5,37),(107,'Joao Moutinho',5,50),(108,'Manuel Fernandes',5,39),(109,'William Carvalho',5,30),(110,'Andre Silva',5,42),(111,'Bernardo Silva',5,38),(112,'Cristiano Ronaldo',5,41),(113,'Gelson Martins',5,50),(114,'Goncalo Guedes',5,27),(115,'Ricardo Quaresma ',5,51),(116,'David de Gea',6,50),(117,'Pepe Reina',6,37),(118,'Kepa Arrizabalaga',6,33),(119,'Dani Carvajal',6,31),(120,'Alvaro Odriozola',6,30),(121,'Gerard Pique',6,48),(122,'Sergio Ramos',6,29),(123,'Nacho',6,33),(124,'Cesar Azpilicueta',6,38),(125,'Jordi Alba',6,38),(126,'Nacho Monreal',6,52),(127,'Sergio Busquets',6,29),(128,'Saul Niquez',6,39),(129,'Koke',6,34),(130,'Thiago Alcantara',6,50),(131,'Andres Iniesta',6,39),(132,'David Silva',6,32),(133,'Isco',6,43),(134,'Marcio Asensio',6,32),(135,'Lucas Vazquez',6,43),(136,'Iago Aspas',6,40),(137,'Rodrigo',6,40),(138,'Diego Costa',6,38),(139,'Mounir El Kajoui',7,51),(140,'Yassine Bounou',7,46),(141,'Ahmad Reda Tagnaouti',7,49),(142,'Mehdi Benatia',7,31),(143,'Romain Saiss',7,46),(144,'Manuel Da Costa',7,29),(145,'Badr Benoun',7,48),(146,'Nabil Dirar',7,28),(147,'Achraf Hakimi',7,39),(148,'Hamza Mendyl',7,28),(149,'M\'bark Boussoufa',7,34),(150,'Karim El Ahmadi',7,45),(151,'Youssef Ait Bennasser',7,32),(152,'Sofyan Amrabat',7,52),(153,'Younes Belhanda',7,45),(154,'Faycal Fajr',7,32),(155,'Amine Harit',7,52),(156,'Khalid Boutaib',7,47),(157,'Aziz Bouhaddouz',7,42),(158,'Ayoub El Kaabi',7,39),(159,'Nordin Amrabat',7,42),(160,'Mehdi Carcela',7,39),(161,'Hakim Ziyech',7,46),(162,'Alireza Beiranvand',8,35),(163,'Rashid Mazaheri',8,51),(164,'Amir Abedzadeh',8,42),(165,'Ramin Rezaeian',8,34),(166,'Mohammad Reza Khanzadeh',8,39),(167,'Morteza Pouraliganji',8,47),(168,'Pejman Montazeri',8,41),(169,'Seyed Majid Hosseini',8,53),(170,'Milad Mohammadi',8,50),(171,'Roozbeh Cheshmi',8,50),(172,'Saeid Ezatolahi',8,40),(173,'Masoud Shojaei',8,48),(174,'Saman Ghoddos',8,38),(175,'Mehdi Torabi',8,40),(176,'Ashkan Dejagah',8,32),(177,'Omid Ebrahimi',8,43),(178,'Ehsan Hajsafi',8,37),(179,'Vahid Amiri',8,33),(180,'Alireza Jahanbakhsh',8,41),(181,'Karim Ansarifard',8,38),(182,'Mahdi Taremi',8,34),(183,'Sardar Azmoun',8,46),(184,'Reza Ghoochannejhad',8,28),(185,'Alphonse Areola',9,39),(186,'Hugo Lloris',9,48),(187,'Steve Mandanda',9,34),(188,'Lucas Hernandez',9,54),(189,'Presnel Kimpembe',9,45),(190,'Benjamin Mendy',9,30),(191,'Benjamin Pavard',9,44),(192,'Adil Rami',9,41),(193,'Djibril Sidibe',9,41),(194,'Samuel Umtiti',9,54),(195,'Raphael Varane',9,36),(196,'N\'Golo Kante',9,33),(197,'Blaise Matuidi',9,35),(198,'Steven N\'Zonzi',9,19),(199,'Paul Pogba',9,32),(200,'Corentin Tolisso',9,38),(201,'Ousmane Dembele',9,39),(202,'Nabil Fekir',9,38),(203,'Olivier Giroud',9,34),(204,'Antoine Griezmann',9,34),(205,'Thomas Lemar',9,30),(206,'Kylian Mbappe',9,36),(207,'Florian Thauvin',9,37),(208,' Brad Jones',10,37),(209,'Mat Ryan',10,35),(210,'Danny Vukovic',10,39),(211,'Aziz Behich',10,39),(212,'Milos Degenek',10,35),(213,'Matthew Jurman',10,38),(214,'James Meredith',10,36),(215,'Josh Risdon',10,39),(216,'Trent Sainsbury',10,49),(217,'Jackson Irvine',10,38),(218,'Mile Jedinak',10,38),(219,'Robbie Kruse',10,38),(220,'Massimo Luongo',10,29),(221,'Mark Milligan',10,38),(222,'Aaron Mooy',10,39),(223,'Tom Rogic',10,41),(224,'Daniel Arzani',10,48),(225,'Tim Cahill',10,35),(226,'Tomi Juric',10,38),(227,'Mathew Leckie',10,33),(228,'Andrew Nabbout',10,32),(229,'Dimitri Petratos',10,36),(230,'Jamie Maclaren',10,42),(231,'Carlos Caceda',11,46),(232,'Jose Carvallo',11,51),(233,'Pedro Gallese',11,44),(234,'Luis Advincula',11,40),(235,'Pedro Aquino',11,36),(236,'Miguel Araujo',11,49),(237,'Andre Carrillo',11,38),(238,'Wilder Cartagena',11,48),(239,'Aldo Corzo',11,40),(240,'Christian Cueva',11,44),(241,'Jefferson Farfan',11,48),(242,'Edison Flores',11,53),(243,'Paolo Hurtado',11,44),(244,'Nilson Loyola',11,45),(245,'Andy Polo',11,29),(246,'Christian Ramos',11,39),(247,'Alberto Rodriguez',11,39),(248,'Raul Ruidiaz',11,51),(249,'Anderson Santamaria',11,27),(250,'Renato Tapia',11,34),(251,'Miguel Trauco',11,34),(252,'Yoshimar Yotun',11,49),(253,'Paolo Guerrero',11,53),(254,'Kasper Schmeichel',12,49),(255,'Jonas Lossl',12,36),(256,'Frederik Ronow',12,40),(257,'Simon Kjaer',12,34),(258,'Andreas Christensen',12,56),(259,'Mathias Jorgensen',12,35),(260,'Jannik Vestergaard',12,40),(261,'Henrik Dalsgaard',12,47),(262,'Jens Stryger',12,29),(263,'Jonas Knudsen',12,35),(264,'William Kvist',12,49),(265,'Thomas Delaney',12,48),(266,'Lukas Lerager',12,36),(267,'Lasse Schone',12,60),(268,'Christian Eriksen',12,29),(269,'Michael Krohn-Dehli',12,32),(270,'Pione Sisto',12,38),(271,'Martin Braithwaite',12,35),(272,'Andreas Cornelius',12,37),(273,'Viktor Fischer',12,44),(274,'Yussuf Poulsen',12,46),(275,'Nicolai Jorgensen',12,41),(276,'Kasper Dolberg',12,44),(277,'Nahuel Guzm',13,47),(278,' Hannes Thor Halldorsson',14,40),(279,'Runar Alex Runarsson',14,49),(280,'Frederik Schram',14,44),(281,'Kari Arnason',14,48),(282,'Ari Freyr Skulason',14,42),(283,'Birkir Mar Saevarsson',14,42),(284,'Sverrir Ingi Ingason',14,47),(285,'Hordur Magnusson',14,36),(286,'Holmar Orn Eyjolfsson',14,39),(287,'Ragnar Sigurdsson',14,36),(288,'Johann Berg Gudmundsson',14,38),(289,'Birkir Bjarnason',14,39),(290,'Arnor Ingvi Traustason',14,33),(291,'Emil Hallfredsson',14,47),(292,'Gylfi Sigurdsson',14,30),(293,'Olafur Ingi Skulason',14,54),(294,'Rurik Gislason',14,44),(295,'Samuel Fridjonsson',14,37),(296,'Aron Gunnarsson',14,45),(297,'Alfred Finnbogason',14,50),(298,'Bjorn Bergmann Sigurdarson',14,39),(299,'Jon Dadi Bodvarsson',14,43),(300,'Albert Gudmundsson',14,45),(301,'Danijel Subasic',15,47),(302,'Lovre Kalinic',15,49),(303,'Dominik Livakovic',15,38),(304,'Vedran Corluka',15,49),(305,'Domagoj Vida',15,39),(306,'Ivan Strinic',15,49),(307,'Dejan Lovren',15,48),(308,'Sime Vrsaljko',15,38),(309,'Josip Pivaric',15,42),(310,'Tin Jedvaj',15,33),(311,'Duje Caleta-Car',15,44),(312,'Luka Modric',15,45),(313,'Ivan Rakitic',15,41),(314,'Mateo Kovacic',15,40),(315,'Milan Badelj',15,36),(316,'Marcelo Brozovic',15,43),(317,'Filip Bradaric',15,41),(318,'Mario Mandzukic',15,32),(319,'Ivan Perisic',15,32),(320,'Nikola Kalinic',15,45),(321,'Andrej Kramaric',15,44),(322,'Marko Pjaca',15,40),(323,'Ante Rebic',15,42),(324,'Ikechukwu Ezenwa',16,50),(325,'Daniel Akpeyi',16,46),(326,'Francis Uzoho',16,44),(327,'William Troost-Ekong',16,50),(328,'Leon Balogun',16,44),(329,'Kenneth Omeruo',16,37),(330,'Bryan Idowu',16,45),(331,'Chidozie Awaziem',16,40),(332,'Abdullahi Shehu',16,37),(333,'Elderson Echiejile',16,40),(334,'Tyronne Ebuehi',16,42),(335,'John Obi Mikel',16,35),(336,'Ogenyi Onazi',16,35),(337,'John Ogu',16,50),(338,'Wilfred Ndidi',16,42),(339,'Oghenekaro Etebo',16,46),(340,'Joel Obi',16,37),(341,'Odion Ighalo',16,38),(342,'Ahmed Musa',16,29),(343,'Victor Moses',16,35),(344,'Alex Iwobi',16,34),(345,'Kelechi Iheanacho',16,45),(346,'Simeon Nwankwo',16,34),(347,'Alisson',17,56),(348,'Ederson',17,38),(349,'Cassio',17,42),(350,'Danilo',17,37),(351,'Fagner',17,42),(352,'Marcelo',17,37),(353,'Filipe Luis',17,39),(354,'Thiago Silva',17,38),(355,'Marquinhos',17,39),(356,'Miranda',17,35),(357,'Pedro Geromel',17,46),(358,'Casemiro',17,46),(359,'Fernandinho',17,31),(360,'Paulinho',17,36),(361,'Fred',17,33),(362,'Renato Augusto',17,33),(363,'Philippe Coutinho',17,35),(364,'Willian',17,40),(365,'Douglas Costa',17,37),(366,'Neymar',17,31),(367,'Taison',17,41),(368,'Gabriel Jesus',17,24),(369,'Roberto Firmino',17,34),(370,'Roman Buerki',18,50),(371,'Yvon Mvogo',18,50),(372,'Yann Sommer',18,53),(373,'Manuel Akanji',18,34),(374,'Johan Djourou',18,35),(375,'Nico Elvedi',18,35),(376,'Michael Lang',18,37),(377,'Stephan Lichtsteiner',18,35),(378,'Jacques-Francois Moubandje',18,36),(379,'Ricardo Rodriguez',18,40),(380,'Fabian Schaer',18,33),(381,'Valon Behrami',18,44),(382,'Blerim Dzemaili',18,41),(383,'Gelson Fernandes',18,48),(384,'Remo Freuler',18,38),(385,'Xherdan Shaqiri',18,43),(386,'Granit Xhaka',18,26),(387,'Steven Zuber',18,26),(388,'Denis Zakaria',18,29),(389,'Josip Drmic',18,35),(390,'Breel Embolo',18,45),(391,'Mario Gavranovic',18,30),(392,'Haris Seferovic',18,32),(393,'Keylor Navas',19,41),(394,'Patrick Pemberton',19,40),(395,'Leonel Moreira',19,36),(396,'Cristian Gamboa',19,38),(397,'Ian Smith',19,44),(398,'Ronald Matarrita',19,53),(399,'Bryan Oviedo',19,41),(400,'Oscar Duarte',19,33),(401,'Giancarlo Gonzalez',19,41),(402,'Francisco Calvo',19,38),(403,'Kendall Waston',19,37),(404,'Johnny Acosta',19,46),(405,'David Guzman',19,35),(406,'Yeltsin Tejeda',19,45),(407,'Celso Borges',19,38),(408,'Randall Azofeifa',19,36),(409,'Rodney Wallace',19,43),(410,'Bryan Ruiz',19,35),(411,'Daniel Colindres',19,50),(412,'Christian Bolanos',19,44),(413,'Johan Venegas',19,35),(414,'Joel Campbell',19,60),(415,'Marco Urena',19,42),(416,'Vladimir Stojkovic',20,53),(417,'Predrag Rajkovic',20,37),(418,'Marko Dmitrovic',20,42),(419,'Aleksandar Kolarov',20,52),(420,'Antonio Rukavina',20,36),(421,'Milan Rodic',20,55),(422,'Branislav Ivanovic',20,27),(423,'Uros Spajic',20,40),(424,'Milos Veljkovic',20,38),(425,'Dusko Tosic',20,26),(426,'Nikola Milenkovic',20,31),(427,'Nemanja Matic',20,34),(428,'Luka Milivojevic',20,54),(429,'Marko Grujic',20,28),(430,'Dusan Tadic',20,37),(431,'Andrija Zivkovic',20,45),(432,'Filip Kostic',20,42),(433,'Nemanja Radonjic',20,39),(434,'Sergej Milinkovic-Savic',20,31),(435,'Adem Ljajic',20,43),(436,'Aleksandar Mitrovic',20,46),(437,'Aleksandar Prijovic',20,42),(438,'Luka Jovic',20,39),(439,'Manuel Neuer',21,31),(440,'Marc-Andre ter Stegen',21,48),(441,'Kevin Trapp',21,47),(442,'Jerome Boateng',21,36),(443,'Matthias Ginter',21,38),(444,'Jonas Hector',21,38),(445,'Mats Hummels',21,29),(446,'Joshua Kimmich',21,28),(447,'Marvin Plattenhardt',21,34),(448,'Antonio Rudiger',21,36),(449,'Niklas Sule',21,39),(450,'Julian Brandt',21,39),(451,'Julian Draxler',21,35),(452,'Mario Gomez',21,44),(453,'Leon Goretzka',21,35),(454,'Ilkay Gundogan',21,40),(455,'Sami Khedira',21,45),(456,'Toni Kroos',21,37),(457,'Thomas Muller',21,22),(458,'Mesut Ozil',21,45),(459,'Marco Reus',21,34),(460,'Sebastian Rudy',21,36),(461,'Timo Werner',21,38),(462,'Jesus Corona',22,50),(463,'Alfredo Talavera',22,38),(464,'Guillermo Ochoa',22,46),(465,'Hugo Ayala',22,38),(466,'Carlos Salcedo',22,42),(467,'Diego Reyes',22,55),(468,'Miguel Layun',22,35),(469,'Hector Moreno',22,39),(470,'Edson Alvarez',22,37),(471,'Rafael Marquez',22,34),(472,'Jonathan dos Santos',22,47),(473,'Marco Fabian',22,28),(474,'Giovani dos Santos',22,49),(475,'Hector Herrera',22,53),(476,'Andres Guardado',22,42),(477,'Raul Jimenez',22,45),(478,'Carlos Vela',22,46),(479,'Javier Hernandez',22,42),(480,'Jesus Corona',22,49),(481,'Oribe Peralta',22,47),(482,'Javier Aquino',22,44),(483,'Hirving Lozano',22,48),(484,'Robin Olsen',23,41),(485,'Karl-Johan Johnsson',23,40),(486,'Kristoffer Nordfeldt',23,41),(487,'Mikael Lustig',23,37),(488,'Victor Lindelof',23,42),(489,'Andreas Granqvist',23,37),(490,'Martin Olsson',23,41),(491,'Ludwig Augustinsson',23,32),(492,'Filip Helander',23,40),(493,'Emil Krafth',23,46),(494,'Pontus Jansson',23,28),(495,'Sebastian Larsson',23,47),(496,'Albin Ekdal',23,36),(497,'Emil Forsberg',23,35),(498,'Gustav Svensson',23,40),(499,'Oscar Hiljemark',23,34),(500,'Viktor Claesson',23,45),(501,'Marcus Rohden',23,34),(502,'Jimmy Durmaz',23,51),(503,'Marcus Berg',23,47),(504,'John Guidetti',23,37),(505,'Ola Toivonen',23,34),(506,'Isaac Kiese Thelin',23,51),(507,'Kim Seunggyu',24,43),(508,'Kim Jinhyeon',24,46),(509,'Cho Hyeonwoo',24,42),(510,'Kim Younggwon',24,37),(511,'Jang Hyunsoo',24,24),(512,'Jeong Seunghyeon',24,41),(513,'Yun Yeongseon',24,46),(514,'Oh Bansuk',24,50),(515,'Kim Minwoo',24,48),(516,'Park Jooho',24,40),(517,'Hong Chul',24,39),(518,'Go Yohan',24,51),(519,'Lee Yong',24,40),(520,'Ki Sungyueng',24,38),(521,'Jeong Wooyoung',24,58),(522,'Ju Sejong',24,42),(523,'Koo Jacheol',24,47),(524,'Lee Jaesung',24,41),(525,'Lee Seungwoo',24,51),(526,'Moon Sunmin',24,36),(527,'Kim Shinwook',24,43),(528,'Son Heungmin',24,43),(529,'Hwang Heechan',24,58),(530,'Koen Casteels',25,51),(531,'Thibaut Courtois',25,45),(532,'Simon Mignolet',25,42),(533,'Toby Alderweireld',25,35),(534,'Dedryck Boyata',25,30),(535,'Vincent Kompany',25,50),(536,'Thomas Meunier',25,37),(537,'Thomas Vermaelen',25,45),(538,'Jan Vertonghen',25,44),(539,'Nacer Chadli',25,44),(540,'Kevin De Bruyne',25,37),(541,'Mousa Dembele',25,35),(542,'Leander Dendoncker',25,48),(543,'Marouane Fellaini',25,42),(544,'Youri Tielemans',25,36),(545,'Axel Witsel',25,32),(546,'Michy Batshuayi',25,37),(547,'Yannick Carrasco',25,52),(548,'Eden Hazard',25,27),(549,'Thorgan Hazard',25,40),(550,'Adnan Januzaj',25,46),(551,'Romelu Lukaku',25,50),(552,'Dries Mertens',25,41),(553,'Jose Calderon',26,48),(554,'Jaime Penedo',26,38),(555,'Alex Rodr',26,32),(556,'Farouk Ben Mustapha',27,39),(557,'Moez Hassen',27,31),(558,'Aymen Mathlouthi',27,32),(559,'Rami Bedoui',27,35),(560,'Yohan Benalouane',27,37),(561,'Syam Ben Youssef',27,34),(562,'Dylan Bronn',27,51),(563,'Oussama Haddadi',27,42),(564,'Ali Maaloul',27,40),(565,'Yassine Meriah',27,43),(566,'Hamdi Nagguez',27,44),(567,'Anice Badri',27,36),(568,'Mohamed Amine Ben Amor',27,43),(569,'Ghaylene Chaalali',27,40),(570,'Ahmed Khalil',27,42),(571,'Saifeddine Khaoui',27,29),(572,'Ferjani Sassi',27,52),(573,'Ellyes Skhiri',27,56),(574,'Naim Sliti',27,34),(575,'Bassem Srarfi',27,38),(576,'Fakhreddine Ben Youssef',27,45),(577,'Saber Khalifa',27,43),(578,'Wahbi Khazri',27,35),(579,'Jack Butland',28,59),(580,'Nick Pope',28,35),(581,'Jordan Pickford',28,35),(582,'Fabian Delph',28,38),(583,'Danny Rose',28,37),(584,'Eric Dier',28,48),(585,'Kyle Walker',28,53),(586,'Kieran Trippier',28,42),(587,'Trent Alexander-Arnold',28,43),(588,'Harry Maguire',28,54),(589,'John Stones',28,38),(590,'Phil Jones',28,41),(591,'Gary Cahill',28,38),(592,'Jordan Henderson',28,40),(593,'Jesse Lingard',28,35),(594,'Ruben Loftus-Cheek',28,43),(595,'Ashley Young',28,32),(596,'Dele Alli',28,43),(597,'Raheem Sterling',28,45),(598,'Harry Kane',28,38),(599,'Jamie Vardy',28,36),(600,'Marcus Rashford',28,33),(601,'Danny Welbeck',28,32),(602,'Bartosz Bialkowski',29,27),(603,'Lukasz Fabianski',29,34),(604,'Wojciech Szczesny',29,49),(605,'Jan Bednarek',29,43),(606,'Bartosz Bereszynski',29,32),(607,'Thiago Cionek',29,55),(608,'Kamil Glik',29,48),(609,'Artur Jedrzejczyk',29,46),(610,'Michal Pazdan',29,28),(611,'Lukasz Piszczek',29,37),(612,'Jakub Blaszczykowski',29,26),(613,'Jacek Goralski',29,33),(614,'Kamil Goricki',29,38),(615,'Grzegorz Krychowiak',29,39),(616,'Slawomir Peszko',29,34),(617,'Maciej Rybus',29,25),(618,'Piotr Zielinski',29,40),(619,'Rafal Kurzawa',29,22),(620,'Karol Linetty',29,32),(621,'Dawid Kownacki',29,46),(622,'Robert Lewandowski',29,47),(623,'Arkadiusz Milik',29,42),(624,'Lukasz Teodorczyk',29,40),(625,'Abdoulaye Diallo',30,27),(626,'Khadim Ndiaye',30,53),(627,'Alfred Gomis',30,40),(628,'Lamine Gassama',30,49),(629,'Moussa Wague',30,40),(630,'Saliou Ciss',30,40),(631,'Youssouf Sabaly',30,37),(632,'Kalidou Kalidou',30,42),(633,'Salif Sane',30,29),(634,'Cheikhou Kouyate',30,39),(635,'Kara Mbodji',30,41),(636,'Idrisa Gana Gueye',30,63),(637,'Cheikh Ndoye',30,46),(638,'Alfred Ndiaye',30,47),(639,'Pape Alioune Ndiaye',30,50),(640,'Moussa Sow',30,50),(641,'Moussa Konate',30,44),(642,'Diafra Sakho',30,39),(643,'Sadio Mane',30,53),(644,'Ismaila Sarr',30,39),(645,'Mame Biram Diouf',30,30),(646,'Mbaye Niang',30,36),(647,'Diao Keita Balde',30,43),(648,'David Ospina',31,32),(649,'Camilo Vargas',31,53),(650,'Jose Fernando Cuadrado',31,45),(651,'Cristian Zapata',31,42),(652,'Davinson Sanchez',31,45),(653,'Santiago Arias',31,37),(654,'Oscar Murillo',31,52),(655,'Frank Fabra',31,38),(656,'Johan Mojica',31,36),(657,'Yerry Mina',31,40),(658,'Wilmar Barrios',31,40),(659,'Carlos Sanchez',31,48),(660,'Jefferson Lerma',31,43),(661,'Jose Izquierdo',31,47),(662,'James Rodriguez',31,40),(663,'Abel Aguilar',31,29),(664,'Juan Fernando Quintero',31,46),(665,'Mateus Uribe',31,42),(666,'Juan Guillermo Cuadrado',31,34),(667,'Radamel Falcao Garcia',31,47),(668,'Miguel Borja',31,36),(669,'Carlos Bacca',31,49),(670,'Luis Fernando Muriel',31,38),(671,'Eiji Kawashima',32,47),(672,'Masaaki Higashiguchi',32,55),(673,'Kosuke Nakamura',32,39),(674,'Yuto Nagatomo',32,43),(675,'Tomoaki Makino',32,41),(676,'Maya Yoshida',32,44),(677,'Hiroki Sakai',32,56),(678,'Gotoku Sakai',32,31),(679,'Gen Shoji',32,51),(680,'Wataru Endo',32,37),(681,'Naomichi Ueda',32,50),(682,'Makoto Hasebe',32,42),(683,'Keisuke Honda',32,40),(684,'Takashi Inui',32,37),(685,'Shinji Kagawa',32,53),(686,'Hotaru Yamaguchi',32,45),(687,'Genki Haraguchi',32,39),(688,'Takashi Usami',32,33),(689,'Gaku Shibasaki',32,39),(690,'Ryota Oshima',32,34),(691,'Shinji Okazaki',32,43),(692,'Yuya Osako',32,31),(693,'Yoshinori Muto',32,48),(694,'Nahuel Guzm�n',13,36),(695,'Willy Caballero',13,44),(696,'Franco Armani',13,41),(697,'Gabriel Mercado',13,49),(698,'Nicolas Otamendi',13,42),(699,'Federico Fazio',13,35),(700,'Nicolas Tagliafico',13,34),(701,'Marcos Rojo',13,39),(702,'Marcos Acuna',13,43),(703,'Cristian Ansaldi',13,39),(704,'Angel Di Maria',13,49),(705,'Ever Banega',13,43),(706,'Lucas Biglia',13,47),(707,'Manuel Lanzini',13,56),(708,'Gio Lo Celso',13,47),(709,'Maximiliano Meza',13,43),(710,'Lionel Messi',13,40),(711,'Sergio Aguero',13,44),(712,'Gonzalo Higuain',13,41),(713,'Paulo Dybala',13,46),(714,'Cristian Pavon',13,47),(715,'Felipe Baloy',26,32),(716,'Hárold Cummings',26,43),(717,'Éric Davis',26,38),(718,'Fidel Escobar',26,50),(719,'Adolfo Machado',26,39),(720,'Michael Murillo',26,46),(721,'Román Torres',26,37),(722,'Ricardo Ávila',26,52),(723,'Yoel Bárcenas',26,36),(724,'Armando Cooper',26,42),(725,'Aníbal Godoy',26,39),(726,'Gabriel Gómez',26,52),(727,'Luis Ovalle',26,34),(728,'José Luis Rodríguez',26,41),(729,'Abdiel Arroyo',26,52),(730,'Ismael Díaz',26,32),(731,'Blas Pérez',26,25),(732,'Valentín Pimentel',26,41),(733,'Luis Tejada	',26,36),(734,'Gabriel Torres',26,44);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rounds`
--

DROP TABLE IF EXISTS `rounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rounds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rounds`
--

LOCK TABLES `rounds` WRITE;
/*!40000 ALTER TABLE `rounds` DISABLE KEYS */;
INSERT INTO `rounds` VALUES (1,'Group Stage','group_stage'),(2,'Round of Sixteen','round_of_16'),(3,'Quarter-final Round','quarterfinal_round'),(4,'Semi-final Round','semifinal_round'),(5,'Final','final');
/*!40000 ALTER TABLE `rounds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_performances`
--

DROP TABLE IF EXISTS `team_performances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_performances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `match_played` int(11) DEFAULT '0',
  `win_count` int(11) DEFAULT '0',
  `draw_count` int(11) DEFAULT '0',
  `lost_count` int(11) DEFAULT '0',
  `goal_for` int(11) DEFAULT '0',
  `goal_against` int(11) DEFAULT '0',
  `yellow_card` int(11) DEFAULT '0',
  `team_id` int(11) NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `team_performances_id_uindex` (`id`),
  KEY `group_performances_groups_id_fk` (`group_id`),
  KEY `group_performances_teams_id_fk` (`team_id`),
  CONSTRAINT `group_performances_groups_id_fk` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `group_performances_teams_id_fk` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_performances`
--

LOCK TABLES `team_performances` WRITE;
/*!40000 ALTER TABLE `team_performances` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_performances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `fifa_code` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL,
  `iso2` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `teams_id_uindex` (`id`),
  UNIQUE KEY `teams_fifacode_uindex` (`fifa_code`),
  UNIQUE KEY `teams_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'Russia','RUS','ru'),(2,'Saudi Arabia','KSA','sa'),(3,'Egypt','EGY','eg'),(4,'Uruguay','URU','uy'),(5,'Portugal','POR','pt'),(6,'Spain','ESP','es'),(7,'Morocco','MAR','ma'),(8,'Iran','IRN','ir'),(9,'France','FRA','fr'),(10,'Australia','AUS','au'),(11,'Peru','PER','pe'),(12,'Denmark','DEN','dk'),(13,'Argentina','ARG','ar'),(14,'Iceland','ISL','is'),(15,'Croatia','CRO','hr'),(16,'Nigeria','NGA','ng'),(17,'Brazil','BRA','br'),(18,'Switzerland','SUI','ch'),(19,'Costa Rica','CRC','cr'),(20,'Serbia','SRB','rs'),(21,'Germany','GER','de'),(22,'Mexico','MEX','mx'),(23,'Sweden','SWE','se'),(24,'South Korea','KOR','kr'),(25,'Belgium','BEL','be'),(26,'Panama','PAN','pa'),(27,'Tunisia','TUN','tn'),(28,'England','ENG','gb-eng'),(29,'Poland','POL','pl'),(30,'Senegal','SEN','sn'),(31,'Colombia','COL','co'),(32,'Japan','JPN','jp');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-08 12:40:24
