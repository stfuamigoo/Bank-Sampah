# Host: localhost  (Version 5.7.24)
# Date: 2021-08-17 16:17:10
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "sampah"
#

DROP TABLE IF EXISTS `sampah`;
CREATE TABLE `sampah` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jenissampah` varchar(25) NOT NULL DEFAULT '',
  `satuan` varchar(255) NOT NULL DEFAULT '',
  `harga` varchar(11) NOT NULL DEFAULT '0',
  `keterangan` varchar(40) NOT NULL DEFAULT '',
  `picture` varchar(150) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

#
# Data for table "sampah"
#

INSERT INTO `sampah` VALUES (1,'Buku Putih / HVS','KG','1000','Semua Jenis kertas (Putih)','/Bank-Sampah/backend/sampah/fotosampah/buku.jpeg'),(2,'Alumunium','KG','6000','Semua Jenis Besi Alumunium','/Bank-Sampah/backend/sampah/fotosampah/alumunium.jpeg'),(3,'Kardus','KG','8000','Semua Jenis Kardus','/Bank-Sampah/backend/sampah/fotosampah/kardus.jpeg'),(5,'Gelas Mineral (Bersih)','KG','200','Semua Jenis Gelas','/Bank-Sampah/backend/sampah/fotosampah/gelasbersih.jpeg'),(6,'Besi ','KG','2000','Semua Jenis Besi','/Bank-Sampah/backend/sampah/fotosampah/besi.jpeg'),(7,'Babet/Kran ','KG','5000','Semua Jenis Besi Babet','/Bank-Sampah/backend/sampah/fotosampah/babet.jpeg'),(8,'Kaleng','KG','800','Semua Jenis Kaleng','/Bank-Sampah/backend/sampah/fotosampah/kaleng.jpeg'),(9,'Kabel','KG','1000','Semua Jenis Kabel','/Bank-Sampah/backend/sampah/fotosampah/kabel.jpeg'),(10,'Kuningan ','KG','22000','Semua Jenis Besi Kuningan','/Bank-Sampah/backend/sampah/fotosampah/kuningan.jpeg'),(11,'Stainless','KG','1500','Semua Jenis Besi Stainless','/Bank-Sampah/backend/sampah/fotosampah/stenlis.jpeg'),(12,'Tembaga','KG','35000','Semua Jenis Kabel Tembaga','/Bank-Sampah/backend/sampah/fotosampah/mbaga.jpeg'),(13,'ASOY(Plastik Kresek)','KG','300','Semua Jenis Kresek (Kering)','/Bank-Sampah/backend/sampah/fotosampah/asoy.jpeg'),(14,'Botol Putih (Bersih)','KG','3000','Semua Jenis Botol (Bersih)','/Bank-Sampah/backend/sampah/fotosampah/botolbersih.jpeg'),(15,'Botol Warna','KG','500','Semua Jenis Botol ','/Bank-Sampah/backend/sampah/fotosampah/botolwarna.jpeg'),(16,'Gelas Mineral Kotor','KG','1500','Semua Jenis Gelas Mineral','/Bank-Sampah/backend/sampah/fotosampah/gelaskotor.jpeg'),(17,'Tutup Botol','KG','1500','Semua Jenis Tutup Botol','/Bank-Sampah/backend/sampah/fotosampah/tutupbotol.jpeg'),(18,'Tutup Galon','KG','2000','Semua Jenis Tutup Galon','/Bank-Sampah/backend/sampah/fotosampah/tutupgalon.jpeg'),(19,'emberan ','KG','1000','Semua Jenis Prabotan Plastik','/Bank-Sampah/backend/sampah/fotosampah/mberan1.jpeg'),(20,'Majalah','KG','600','Semua Jenis Majalah','/Bank-Sampah/backend/sampah/fotosampah/majalah.jpeg'),(21,'Buku','KG','1000','Semua Jenis Buku','/Bank-Sampah/backend/sampah/fotosampah/buku.jpeg'),(22,'Koran','KG','2000','Semua Jenis Koran','/Bank-Sampah/backend/sampah/fotosampah/koran.jpeg'),(23,'Botol Beling','PC','100','Semua Jenis Botol Beling','/Bank-Sampah/backend/sampah/fotosampah/botol.jpeg'),(24,'Keping Cd','KG','2500','Semua Jenis Keping Cd','/Bank-Sampah/backend/sampah/fotosampah/kepingcd.jpeg'),(25,'Minyak Curah','LT','1000','Semua Jenis Minyak Jelanta','/Bank-Sampah/backend/sampah/fotosampah/minyak.jpeg'),(26,'Tali Besi','KG','1000','Semua Jenis Besi','/Bank-Sampah/backend/sampah/fotosampah/Seto.jpeg');

#
# Structure for table "tarik"
#

DROP TABLE IF EXISTS `tarik`;
CREATE TABLE `tarik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal_tarik` date NOT NULL,
  `nik_user` varchar(255) NOT NULL,
  `saldo` int(11) NOT NULL,
  `jumlah_tarik` int(11) NOT NULL,
  `keterangan` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Data for table "tarik"
#

INSERT INTO `tarik` VALUES (1,'2020-11-28','NSB201101',45000,5000,'-'),(2,'2020-11-28','NSB201101',40000,20000,'-'),(3,'2021-01-27','NSB210104',77000,15000,'-'),(4,'2021-01-27','NSB210104',62000,17000,'.');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nik` varchar(255) NOT NULL,
  `password` varchar(16) NOT NULL,
  `nama` varchar(128) NOT NULL,
  `alamat` text NOT NULL,
  `rt` varchar(11) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `email` varchar(128) NOT NULL,
  `saldo` int(128) NOT NULL,
  `role` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'admin','admin','admin','admin','2','123456789','admin@admin',0,'Admin'),(14,'ucup','ucup','ucup','asdasd','2','12312','asdasdasd',0,'Nasabah');

#
# Structure for table "setor"
#

DROP TABLE IF EXISTS `setor`;
CREATE TABLE `setor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggalsetor` varchar(255) NOT NULL DEFAULT '0000-00-00',
  `id_user` int(11) NOT NULL,
  `id_sampah` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `jenissampah` varchar(255) DEFAULT NULL,
  `harga` varchar(255) DEFAULT NULL,
  `berat` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `keterangan` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK_id_user` (`id_user`) USING BTREE,
  KEY `FK_id_sampah` (`id_sampah`) USING BTREE,
  CONSTRAINT `FK_id_sampah` FOREIGN KEY (`id_sampah`) REFERENCES `sampah` (`id`),
  CONSTRAINT `FK_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

#
# Data for table "setor"
#

INSERT INTO `setor` VALUES (5,'2021-07-26',14,6,NULL,NULL,NULL,3,6000,'-');
