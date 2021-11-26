-- phpMyAdmin SQL Dump
-- version 4.6.6deb5ubuntu0.5
-- https://www.phpmyadmin.net/
--
-- 主機: sophia
-- 產生時間： 2021 年 11 月 26 日 13:28
-- 伺服器版本: 5.7.35-0ubuntu0.18.04.1
-- PHP 版本： 7.2.24-0ubuntu0.18.04.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `knchu`
--

-- --------------------------------------------------------

--
-- 資料表結構 `course`
--

CREATE TABLE `course` (
  `id` int(5) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `course`
--

INSERT INTO `course` (`id`, `code`, `name`) VALUES
(1, 'COMP1117A', 'Computer Programming'),
(2, 'COMP1117B', 'Computer Programming'),
(3, 'ENGG1330A', 'Computer Programming I'),
(4, 'ENGG1340A', 'Computer Programming II'),
(5, 'ENGG1340B', 'Computer Programming II'),
(6, 'ENGG1340C', 'Computer Programming II'),
(7, 'FITE1010', 'Introduction to Financial Technologies'),
(8, 'FITE2000A', 'Foundations of FinTech Programming'),
(9, 'STAT1005', 'Essential skills for undergraduates: Foundations of Data Science'),
(10, 'COMP2113A', 'Programming Technologies'),
(11, 'COMP2113B', 'Programming Technologies'),
(12, 'COMP2113C', 'Programming Technologies'),
(13, 'COMP2119A', 'Introduction to Data Structures and Algorithms'),
(14, 'COMP2119B', 'Introduction to Data Structures and Algorithms'),
(15, 'COMP2119C', 'Introduction to Data Structures and Algorithms'),
(16, 'COMP2119D', 'Introduction to Data Structures and Algorithms'),
(17, 'COMP2120A', 'Computer Organization'),
(18, 'COMP2120B', 'Computer Organization'),
(19, 'COMP2121A', 'Discrete Mathematics'),
(20, 'COMP2121B', 'Discrete Mathematics'),
(21, 'COMP2121C', 'Discrete Mathematics'),
(22, 'COMP2396A', 'Object-oriented Programming and Java'),
(23, 'COMP2396B', 'Object-oriented Programming and Java'),
(24, 'COMP3230A', 'Principles of Operating Systems'),
(25, 'COMP3230B', 'Principles of Operating Systems'),
(26, 'COMP3234A', 'Computer and Communication Networks'),
(27, 'COMP3234B', 'Computer and Communication Networks'),
(28, 'COMP3250A', 'Design and Analysis of Algorithms'),
(29, 'COMP3250B', 'Design and Analysis of Algorithms'),
(30, 'COMP3278A', 'Intro. to Database Management Systems'),
(31, 'COMP3278B', 'Intro. to Database Management Systems'),
(32, 'COMP3297A', 'Software Engineering'),
(33, 'COMP3297B', 'Software Engineering'),
(34, 'COMP3311', 'Legal Aspects of Computing'),
(35, 'COMP3410A', 'Internship'),
(36, 'COMP3410B', 'Internship'),
(37, 'COMP3412A', 'Internship'),
(38, 'COMP3412B', 'Internship'),
(39, 'COMP4801', 'Final Year Project full year'),
(40, 'COMP4802', 'Extended Final Year Project'),
(41, 'COMP4802', 'Extended Final Year Project'),
(42, 'COMP4804', 'Computing and Data Analytics Project'),
(43, 'COMP4805', 'Project (2nd Major)'),
(44, 'FITE4801', 'Project full year'),
(45, 'COMP2501', 'Introduction to data science and engineering'),
(46, 'COMP2502', 'Computing Fundamentals (Minor in DA&E only)'),
(47, 'COMP3231', 'Computer Architecture'),
(48, 'COMP3258', 'Functional Programming'),
(49, 'COMP3259', 'Principles of Programming Languages'),
(50, 'COMP3270A', 'Artificial Intelligence'),
(51, 'COMP3270B', 'Artificial Intelligence'),
(52, 'COMP3271', 'Computer Graphics'),
(53, 'COMP3314A', 'Machine Learning'),
(54, 'COMP3314B', 'Machine Learning'),
(55, 'COMP3316', 'Quantum Information and Computation'),
(56, 'COMP3317', 'Computer Vision'),
(57, 'COMP3320', 'Electronic Commerce Technology'),
(58, 'COMP3322A', 'Modern Technologies on World Wide Web'),
(59, 'COMP3322B', 'Modern Technologies on World Wide Web'),
(60, 'COMP3329', 'Computer Game Design and Programming'),
(61, 'COMP3330', 'Interactive Mobile Application Design and Programming'),
(62, 'COMP3340', 'Applied Deep Learning'),
(63, 'COMP3352', 'Algorithmic Game Theory'),
(64, 'COMP3355', 'Cyber Security'),
(65, 'COMP3356', 'Robotics'),
(66, 'COMP3357', 'Cryptography'),
(67, 'COMP3358', 'Distributed and Parallel Computing'),
(68, 'COMP3360', 'Data-driven computer animation'),
(69, 'COMP3361', 'Natural language processing'),
(70, 'COMP3362', 'Hands-on AI: experimentation & applications'),
(71, 'COMP3407', 'Scientific Computing'),
(72, 'FITE2010', 'Distributed Ledger & Blockchain'),
(73, 'FITE3010', 'Big Data and Data Mining'),
(74, 'FITE3012', 'E-payment and Crypto-currency'),
(75, 'FITE7407', 'Securities transaction banking'),
(76, 'FITE7409', 'Blockchain and cryptocurrency');

-- --------------------------------------------------------

--
-- 資料表結構 `enroll`
--

CREATE TABLE `enroll` (
  `id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- 資料表結構 `rate`
--

CREATE TABLE `rate` (
  `id` int(11) NOT NULL,
  `rating_user_id` int(11) NOT NULL,
  `rated_user_id` int(11) NOT NULL,
  `score` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- 資料表結構 `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `requester_user_id` int(11) NOT NULL,
  `requested_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `id` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` int(30) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `user`
--

INSERT INTO `user` (`id`, `name`, `phone_number`, `nationality`, `language`, `email`, `password`) VALUES
(1, 'Kin', NULL, NULL, NULL, 'u3557110@connect.hku.hk', '123456'),
(2, 'winki', NULL, NULL, NULL, 'try', '123');

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `enroll`
--
ALTER TABLE `enroll`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `rate`
--
ALTER TABLE `rate`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- 在匯出的資料表使用 AUTO_INCREMENT
--

--
-- 使用資料表 AUTO_INCREMENT `course`
--
ALTER TABLE `course`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;
--
-- 使用資料表 AUTO_INCREMENT `enroll`
--
ALTER TABLE `enroll`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用資料表 AUTO_INCREMENT `rate`
--
ALTER TABLE `rate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用資料表 AUTO_INCREMENT `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用資料表 AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
