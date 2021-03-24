-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2020 at 02:58 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `numberfindgame`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `leaderboard`
-- (See below for the actual view)
--
CREATE TABLE `leaderboard` (
`ranking` bigint(21)
,`username` varchar(255)
,`total_matches` bigint(21)
,`sum_rp` decimal(32,0)
,`winrate` decimal(26,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` int(11) NOT NULL,
  `time_start` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `duration` double NOT NULL,
  `found_count` int(11) NOT NULL,
  `number_quantity` int(11) NOT NULL,
  `time` double NOT NULL,
  `max_player` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`id`, `time_start`, `duration`, `found_count`, `number_quantity`, `time`, `max_player`) VALUES
(21, '2020-11-22 16:45:57', 7818, 3, 3, 150000, 3),
(22, '2020-11-22 17:20:36', 4113, 3, 3, 150000, 3);

-- --------------------------------------------------------

--
-- Table structure for table `match_player`
--

CREATE TABLE `match_player` (
  `player_id` int(11) NOT NULL,
  `match_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `placing` int(11) NOT NULL,
  `avg_time` double NOT NULL,
  `player_count` int(11) NOT NULL,
  `ranking_point` int(11) GENERATED ALWAYS AS (`player_count` - `placing`) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `match_player`
--

INSERT INTO `match_player` (`player_id`, `match_id`, `score`, `placing`, `avg_time`, `player_count`) VALUES
(1, 21, 4, 1, 2.5065, 2),
(1, 22, 0, 2, 0, 2),
(3, 21, 1, 2, 2.796, 2),
(3, 22, 5, 1, 1.1335, 2);

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birthday` date NOT NULL DEFAULT current_timestamp(),
  `gender` varchar(255) NOT NULL DEFAULT 'Male'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `username`, `password`, `email`, `first_name`, `last_name`, `birthday`, `gender`) VALUES
(1, 'saidan00', '$2a$12$t16ofQxH1IaJIQMwZs2/muJqNCeqNEau7jUpNNU8kiA9sE2l0fBn.', 'jaysgh94@email.com', 'Huy', 'Võ', '2020-11-23', 'Male'),
(3, 'supersaidan00', '$2a$12$t16ofQxH1IaJIQMwZs2/muJqNCeqNEau7jUpNNU8kiA9sE2l0fBn.', 'jaysgh96@email.com', 'Huy', 'Võ', '2020-11-23', 'Male'),
(4, 'an00', '$2a$12$DWka38qjW1JT9GZLakkANOqjhZ3WYjAd3b.c6700gr4F64Mw0WBc.', 'an00@gmail.com', 'An', 'Thuy', '2020-11-23', 'Male');

-- --------------------------------------------------------

--
-- Structure for view `leaderboard`
--
DROP TABLE IF EXISTS `leaderboard`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `leaderboard`  AS 
SELECT
	`tmp2`.`ranking` AS `ranking`,
	`tmp2`.`username` AS `username`,
	`tmp2`.`total_matches` AS `total_matches`,
	ifnull(`tmp2`.`sum_rp`, 0) AS `sum_rp`,
	ifnull(`tmp2`.`winrate`, 0) AS `winrate`
FROM (
	SELECT
		row_number() OVER (ORDER BY `tmp`.`sum_rp` DESC,
			round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2)
			DESC) AS `ranking`,
		`tmp`.`username` AS `username`,
		`tmp`.`total_matches` AS `total_matches`,
		`tmp`.`sum_rp` AS `sum_rp`,
		round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2) AS `winrate`
	FROM (
	SELECT
		`p`.`username` AS `username`,
		sum(`mp`.`ranking_point`) AS `sum_rp`,
		count( CASE `mp`.`placing`
		WHEN 1 THEN
			1
		ELSE
			NULL
		END) AS `win_matches`,
		count(`mp`.`placing`) AS `total_matches`
	FROM (`numberfindgamedb`.`players` `p`
	LEFT JOIN `numberfindgamedb`.`match_player` `mp` ON (`p`.`id` = `mp`.`player_id`))
GROUP BY
	`p`.`username`) `tmp` ORDER BY
	`tmp`.`sum_rp` DESC,
	round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2)
	DESC, `username`) `tmp2` ;

-- 2020-11-25 15:26:25.5270
SELECT ordinal_position as ordinal_position,column_name as column_name,data_type AS data_type,is_nullable as is_nullable,column_type as column_type FROM information_schema.columns WHERE table_schema='NumberFindGameDB'AND table_name='leaderboard';

-- 2020-11-25 15:26:25.5360
SELECT VIEW_DEFINITION as create_statement FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA='NumberFindGameDB'AND TABLE_NAME='leaderboard';

-- 2020-11-25 15:26:27.8730
SELECT * FROM `NumberFindGameDB`.`leaderboard` LIMIT 300 OFFSET 0;

-- 2020-11-25 15:26:27.8820
SELECT table_rows as count FROM information_schema.TABLES WHERE TABLE_SCHEMA='NumberFindGameDB'AND TABLE_NAME='leaderboard';

-- 2020-11-25 16:24:30.3610
CREATE OR REPLACE VIEW `NumberFindGameDB`.`leaderboard` AS SELECT
	`tmp2`.`ranking` AS `ranking`,
	`tmp2`.`username` AS `username`,
	`tmp2`.`total_matches` AS `total_matches`,
	ifnull(`tmp2`.`sum_rp`, 0) AS `sum_rp`,
	ifnull(`tmp2`.`winrate`, 0) AS `winrate`
FROM (
	SELECT
		row_number() OVER (ORDER BY `tmp`.`sum_rp` DESC,
			round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2)
			DESC, `username`) AS `ranking`,
		`tmp`.`username` AS `username`,
		`tmp`.`total_matches` AS `total_matches`,
		`tmp`.`sum_rp` AS `sum_rp`,
		round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2) AS `winrate`
	FROM (
	SELECT
		`p`.`username` AS `username`,
		sum(`mp`.`ranking_point`) AS `sum_rp`,
		count( CASE `mp`.`placing`
		WHEN 1 THEN
			1
		ELSE
			NULL
		END) AS `win_matches`,
		count(`mp`.`placing`) AS `total_matches`
	FROM (`numberfindgamedb`.`players` `p`
	LEFT JOIN `numberfindgamedb`.`match_player` `mp` ON (`p`.`id` = `mp`.`player_id`))
GROUP BY
	`p`.`username`) `tmp` ORDER BY
	`tmp`.`sum_rp` DESC,
	round(`tmp`.`win_matches` / `tmp`.`total_matches` * 100, 2)
	DESC,
	`tmp`.`username`) `tmp2`
 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `match_player`
--
ALTER TABLE `match_player`
  ADD PRIMARY KEY (`player_id`,`match_id`),
  ADD KEY `match_id` (`match_id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `match_player`
--
ALTER TABLE `match_player`
  ADD CONSTRAINT `match_player_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`),
  ADD CONSTRAINT `match_player_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`),
  ADD CONSTRAINT `match_player_ibfk_3` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
