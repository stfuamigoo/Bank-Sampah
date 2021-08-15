<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT setor.id, setor.tanggal_setor, user.id, user.nama, user.saldo, sampah.id, sampah.jenis_sampah, sampah.harga, setor.berat, setor.total, setor.keterangan 
	FROM `setor` 
	INNER JOIN user ON setor.id_user = user.id 
	INNER JOIN sampah ON setor.id_sampah = sampah.id";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	$arraydata = array();

	while($row = mysqli_fetch_assoc($r)){

		$arraydata[] = $row;
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array("histori_setor"=> $arraydata));

	mysqli_close($con);
?>
<!-- 192.168.43.233 -->
