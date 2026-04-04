-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: observacao
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `cat_id` bigint NOT NULL AUTO_INCREMENT,
  `cat_descricao` varchar(255) NOT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Iluminação'),(2,'Buraco'),(3,'Limpeza');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicostatussolicitacao`
--

DROP TABLE IF EXISTS `historicostatussolicitacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historicostatussolicitacao` (
  `hss_id` bigint NOT NULL AUTO_INCREMENT,
  `hss_solicitacao` bigint NOT NULL,
  `hss_gerente` bigint NOT NULL,
  `hss_status` enum('ATENDIMENTO','TRIAGEM','EM EXECUÇÃO','RESOLVIDO','ENCERRADO') NOT NULL,
  `hss_dataMudanca` datetime NOT NULL,
  PRIMARY KEY (`hss_id`),
  KEY `rel_histstatussolic_solicitacao` (`hss_solicitacao`),
  KEY `rel_histstatussolic_gerente` (`hss_gerente`),
  CONSTRAINT `rel_histstatussolic_gerente` FOREIGN KEY (`hss_gerente`) REFERENCES `usuarios` (`usu_id`),
  CONSTRAINT `rel_histstatussolic_solicitacao` FOREIGN KEY (`hss_solicitacao`) REFERENCES `solicitacoes` (`slc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicostatussolicitacao`
--

LOCK TABLES `historicostatussolicitacao` WRITE;
/*!40000 ALTER TABLE `historicostatussolicitacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicostatussolicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacoes`
--

DROP TABLE IF EXISTS `solicitacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitacoes` (
  `slc_id` bigint NOT NULL AUTO_INCREMENT,
  `slc_usuario` bigint NOT NULL,
  `slc_protocolo` varchar(20) NOT NULL,
  `slc_categoria` bigint NOT NULL,
  `slc_descricao` varchar(255) NOT NULL,
  `slc_anexo` longblob,
  `slc_prioridade` int NOT NULL,
  `slc_resposta` varchar(255) DEFAULT NULL,
  `slc_status` enum('ATENDIMENTO','TRIAGEM','EM EXECUÇÃO','RESOLVIDO','ENCERRADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`slc_id`),
  KEY `rel_solicitacoes_usuario` (`slc_usuario`),
  KEY `rel_solicitacoes_categoria` (`slc_categoria`),
  CONSTRAINT `rel_solicitacoes_categoria` FOREIGN KEY (`slc_categoria`) REFERENCES `categorias` (`cat_id`),
  CONSTRAINT `rel_solicitacoes_usuario` FOREIGN KEY (`slc_usuario`) REFERENCES `usuarios` (`usu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacoes`
--

LOCK TABLES `solicitacoes` WRITE;
/*!40000 ALTER TABLE `solicitacoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `usu_id` bigint NOT NULL AUTO_INCREMENT,
  `usu_nome` varchar(255) DEFAULT NULL,
  `usu_senha` varchar(255) DEFAULT NULL,
  `usu_tipo` enum('CIDADAO','GERENTE') DEFAULT NULL,
  PRIMARY KEY (`usu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,NULL,NULL,NULL),(2,NULL,NULL,NULL),(3,NULL,NULL,NULL),(4,NULL,NULL,NULL),(5,NULL,NULL,NULL),(6,'Fernando','123','GERENTE'),(7,'Julia','123','CIDADAO'),(8,'teste','123','CIDADAO'),(9,'teste','1','GERENTE');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'observacao'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-04  4:06:29
