-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 09, 2021 at 09:53 AM
-- Server version: 5.6.51-cll-lve
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `events_cegep`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `aid` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`aid`, `email`, `pass`) VALUES
(1, 'admin@gmail.com', '123'),
(2, 'sample@gmail.com', '123');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `cid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `msg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`cid`, `eid`, `name`, `email`, `msg`) VALUES
(3, 1, 'sravani', 'sravani@gmail.com', 'good event'),
(6, 2, 'sravan', 'sravan@gmail.com', 'This event gives information about creating apps which helps the beginners');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `eid` int(11) NOT NULL,
  `category` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `dat` varchar(100) NOT NULL,
  `venue` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eid`, `category`, `name`, `dat`, `venue`, `description`, `email`, `image`) VALUES
(1, 'Computer Event', 'HTML Designing', '21/07/2021', 'Cegep Gaspesie Campus', 'HTML events are things that happen to HTML elements. When JavaScript is used in HTML pages, JavaScript can react on these events.', 'sravani@gmail.com', 'images/html.png'),
(2, 'Android ', 'Project Overview ', '22/07/2021', 'Cegep Campus quebec', 'How to make Android app good class', 'sravani@gmail.com', 'images/project.jpeg'),
(5, 'Testing Tool', 'Testing on Selinium software', '22/07/2021', 'Cegep Campus', 'Testing tools concept on web applications\nso it would be useful for all students', 's@gmail.com', 'images/testing.png'),
(7, 'Computers', 'IOS Event', '2021-8-28', 'Cegep', 'iOS event for asking apps', 'ravi@gmail.com', 'images/ios.jpeg'),
(8, 'Computers and IT', 'Android Project Event', '2021-8-12', 'Cegep', 'How to make simple Android app', 'ravi@gmail.com', 'images/and.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `eventjoin`
--

CREATE TABLE `eventjoin` (
  `jid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `eventjoin`
--

INSERT INTO `eventjoin` (`jid`, `eid`, `email`, `status`) VALUES
(1, 1, 'sravani@gmail.com', 'Joined'),
(2, 5, 'sravani@gmail.com', 'Joined'),
(3, 1, 'reza@gmail.com', 'Joined'),
(4, 2, 'sravani@gmail.com', 'Joined'),
(5, 1, 'sasank@gmail.com', 'Joined'),
(6, 2, 'sravan@gmail.com', 'Joined'),
(7, 7, 'ravi@gmail.com', 'Joined');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `sid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`sid`, `name`, `email`, `phone`, `pass`) VALUES
(1, 'sravani', 'sravani@gmail.com', '5149634123', 'sravani123'),
(8, 'Nikhil Deekonda', 'Nikhil@gmail.com', 'Nikhil123', '5148965231'),
(9, 'Manoj Goud', 'manoj@gmail.com', 'manoj123', '4385617894'),
(5, 'sravan', 'sravan@gmail.com', '123', 'sravan123'),
(10, 'Rajesh', 'rajesh@gmail.com', 'rajesh123', '5147896541'),
(11, 'Sravan', 'sravan@gmail.com', 'Sravan@123', '4123123123'),
(12, 'Ravi', 'ravi@gmail.com', '412123123', 'Ravi@123');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `tid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`tid`, `name`, `email`, `phone`, `pass`) VALUES
(1, 'Reza', 'R@gmail.com', '123', '512312312'),
(7, 'silviya', 'silviya@gmail.com', 'silviya123', '4389654123'),
(6, 'Sakshi sharma', 'sakshi@gmail.com', 'sakshi123', '5147896321'),
(8, 'pargol', 'pargol@gmail.com', 'adsf', '8888888888'),
(9, 'lucus', 'lucus@gmail.com', '123', '1231231231'),
(10, 'john', 'john@gmaail.com', 'John@123', '1231231231');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`aid`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`cid`),
  ADD KEY `eid` (`eid`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `eventjoin`
--
ALTER TABLE `eventjoin`
  ADD PRIMARY KEY (`jid`),
  ADD KEY `eid` (`eid`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`sid`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`tid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `aid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `eid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `eventjoin`
--
ALTER TABLE `eventjoin`
  MODIFY `jid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `tid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `event` (`eid`);

--
-- Constraints for table `eventjoin`
--
ALTER TABLE `eventjoin`
  ADD CONSTRAINT `eventjoin_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `event` (`eid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
