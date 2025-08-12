-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 08, 2025 alle 16:30
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `san_martino_friendship_day`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `fiscalCode` varchar(17) DEFAULT '-',
  `phoneNumber` varchar(13) NOT NULL,
  `residencyCountry` varchar(20) NOT NULL,
  `residencyAddress` varchar(200) NOT NULL,
  `residencyProvince` varchar(5) NOT NULL,
  `residencyZipCode` varchar(5) NOT NULL,
  `shipCountry` varchar(50) NOT NULL,
  `shipAddress` varchar(200) NOT NULL,
  `shipProvince` varchar(200) NOT NULL,
  `shipZipCode` varchar(5) NOT NULL,
  `insertDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `confirmedDate` timestamp NULL DEFAULT NULL,
  `privacy` tinyint(1) NOT NULL DEFAULT 0,
  `rules` tinyint(1) NOT NULL DEFAULT 0,
  `tokenId` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tokenId` (`tokenId`),
  UNIQUE KEY `Email` (`email`),
  KEY `Confirmed_Date` (`confirmedDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

