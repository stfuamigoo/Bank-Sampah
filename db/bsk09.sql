-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 19, 2021 at 10:12 PM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bsk09`
--

-- --------------------------------------------------------

--
-- Table structure for table `sampah`
--

CREATE TABLE `sampah` (
  `id` int(11) NOT NULL,
  `jenissampah` varchar(25) NOT NULL DEFAULT '',
  `satuan` varchar(255) NOT NULL DEFAULT '',
  `harga` varchar(11) NOT NULL DEFAULT '0',
  `keterangan` varchar(40) NOT NULL DEFAULT '',
  `picture` varchar(150) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sampah`
--

INSERT INTO `sampah` (`id`, `jenissampah`, `satuan`, `harga`, `keterangan`, `picture`) VALUES
(1, 'Buku Putih / HVS', 'KG', '1000', 'Semua Jenis kertas (Putih)', '/Bank-Sampah/backend/sampah/fotosampah/buku.jpeg'),
(2, 'Alumunium', 'KG', '6000', 'Semua Jenis Besi Alumunium', '/Bank-Sampah/backend/sampah/fotosampah/alumunium.jpeg'),
(3, 'Kardus', 'KG', '8000', 'Semua Jenis Kardus', '/Bank-Sampah/backend/sampah/fotosampah/kardus.jpeg'),
(5, 'Gelas Mineral (Bersih)', 'KG', '200', 'Semua Jenis Gelas', '/Bank-Sampah/backend/sampah/fotosampah/gelasbersih.jpeg'),
(6, 'Besi ', 'KG', '2000', 'Semua Jenis Besi', '/Bank-Sampah/backend/sampah/fotosampah/besi.jpeg'),
(7, 'Babet/Kran ', 'KG', '5000', 'Semua Jenis Besi Babet', '/Bank-Sampah/backend/sampah/fotosampah/babet.jpeg'),
(8, 'Kaleng', 'KG', '800', 'Semua Jenis Kaleng', '/Bank-Sampah/backend/sampah/fotosampah/kaleng.jpeg'),
(9, 'Kabel', 'KG', '1000', 'Semua Jenis Kabel', '/Bank-Sampah/backend/sampah/fotosampah/kabel.jpeg'),
(10, 'Kuningan ', 'KG', '22000', 'Semua Jenis Besi Kuningan', '/Bank-Sampah/backend/sampah/fotosampah/kuningan.jpeg'),
(11, 'Stainless', 'KG', '1500', 'Semua Jenis Besi Stainless', '/Bank-Sampah/backend/sampah/fotosampah/stenlis.jpeg'),
(12, 'Tembaga', 'KG', '35000', 'Semua Jenis Kabel Tembaga', '/Bank-Sampah/backend/sampah/fotosampah/mbaga.jpeg'),
(13, 'ASOY(Plastik Kresek)', 'KG', '300', 'Semua Jenis Kresek (Kering)', '/Bank-Sampah/backend/sampah/fotosampah/asoy.jpeg'),
(14, 'Botol Putih (Bersih)', 'KG', '3000', 'Semua Jenis Botol (Bersih)', '/Bank-Sampah/backend/sampah/fotosampah/botolbersih.jpeg'),
(15, 'Botol Warna', 'KG', '500', 'Semua Jenis Botol ', '/Bank-Sampah/backend/sampah/fotosampah/botolwarna.jpeg'),
(16, 'Gelas Mineral Kotor', 'KG', '1500', 'Semua Jenis Gelas Mineral', '/Bank-Sampah/backend/sampah/fotosampah/gelaskotor.jpeg'),
(17, 'Tutup Botol', 'KG', '1500', 'Semua Jenis Tutup Botol', '/Bank-Sampah/backend/sampah/fotosampah/tutupbotol.jpeg'),
(18, 'Tutup Galon', 'KG', '2000', 'Semua Jenis Tutup Galon', '/Bank-Sampah/backend/sampah/fotosampah/tutupgalon.jpg'),
(19, 'emberan ', 'KG', '1000', 'Semua Jenis Prabotan Plastik', '/Bank-Sampah/backend/sampah/fotosampah/mberan1.jpeg'),
(20, 'Majalah', 'KG', '600', 'Semua Jenis Majalah', '/Bank-Sampah/backend/sampah/fotosampah/majalah.jpeg'),
(21, 'Buku', 'KG', '1000', 'Semua Jenis Buku', '/Bank-Sampah/backend/sampah/fotosampah/buku.jpeg'),
(22, 'Koran', 'KG', '2000', 'Semua Jenis Koran', '/Bank-Sampah/backend/sampah/fotosampah/koran.jpeg'),
(23, 'Botol Beling', 'PC', '100', 'Semua Jenis Botol Beling', '/Bank-Sampah/backend/sampah/fotosampah/botol.jpeg'),
(24, 'Keping Cd', 'KG', '2500', 'Semua Jenis Keping Cd', '/Bank-Sampah/backend/sampah/fotosampah/kepingcd.jpeg'),
(25, 'Minyak Curah', 'LT', '1000', 'Semua Jenis Minyak Jelanta', '/Bank-Sampah/backend/sampah/fotosampah/minyak.jpeg'),
(26, 'Tali Besi', 'KG', '1000', 'Semua Jenis Besi', '/Bank-Sampah/backend/sampah/fotosampah/talibesi.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `setor`
--

CREATE TABLE `setor` (
  `id` int(11) NOT NULL,
  `tanggalsetor` varchar(255) NOT NULL DEFAULT '0000-00-00',
  `id_user` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `saldo_user` int(11) NOT NULL,
  `id_sampah` int(11) NOT NULL,
  `jenissampah` varchar(255) DEFAULT NULL,
  `satuan` varchar(64) NOT NULL,
  `harga` varchar(255) DEFAULT NULL,
  `jumlah` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `keterangan` varchar(30) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `setor`
--

INSERT INTO `setor` (`id`, `tanggalsetor`, `id_user`, `nama`, `saldo_user`, `id_sampah`, `jenissampah`, `satuan`, `harga`, `jumlah`, `total`, `keterangan`) VALUES
(7, '2021-08-03', 14, 'ucup', 1000, 2, 'Alumunium', 'KG', '6000', 3, 18000, '-'),
(8, '2021-08-02', 14, 'ucup', 0, 21, 'Buku', 'KG', '1000', 10, 10000, '-'),
(9, '2021-08-04', 15, 'Audrey', 10000, 15, 'Botol Warna', 'KG', '500', 5, 2500, '-');

-- --------------------------------------------------------

--
-- Table structure for table `tarik`
--

CREATE TABLE `tarik` (
  `id` int(11) NOT NULL,
  `tanggal_tarik` varchar(255) NOT NULL DEFAULT '0000-00-00',
  `id_user` int(11) NOT NULL,
  `nama_user` varchar(255) NOT NULL,
  `saldo_user` int(11) NOT NULL,
  `jumlah_tarik` int(11) NOT NULL,
  `keterangan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nik` varchar(255) NOT NULL,
  `password` varchar(16) NOT NULL,
  `nama` varchar(128) NOT NULL,
  `alamat` text NOT NULL,
  `rt` varchar(11) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `email` varchar(128) NOT NULL,
  `saldo` int(128) NOT NULL,
  `role` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nik`, `password`, `nama`, `alamat`, `rt`, `telepon`, `email`, `saldo`, `role`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', '2', '123456789', 'admin@admin', 0, 'Admin'),
(14, 'ucup', 'ucup', 'ucup', 'asdasd', '2', '12312', 'asdasdasd', 10000, 'Nasabah'),
(15, '321', '321', 'Audrey', 'gang jambu', '2', '321123', 'audreyuyeee@gmail.com', 12500, 'Nasabah');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sampah`
--
ALTER TABLE `sampah`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `setor`
--
ALTER TABLE `setor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_id_user` (`id_user`) USING BTREE,
  ADD KEY `FK_id_sampah` (`id_sampah`) USING BTREE;

--
-- Indexes for table `tarik`
--
ALTER TABLE `tarik`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKid_user` (`id_user`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sampah`
--
ALTER TABLE `sampah`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `setor`
--
ALTER TABLE `setor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tarik`
--
ALTER TABLE `tarik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `setor`
--
ALTER TABLE `setor`
  ADD CONSTRAINT `FK_id_sampah` FOREIGN KEY (`id_sampah`) REFERENCES `sampah` (`id`),
  ADD CONSTRAINT `FK_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Constraints for table `tarik`
--
ALTER TABLE `tarik`
  ADD CONSTRAINT `FKid_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
