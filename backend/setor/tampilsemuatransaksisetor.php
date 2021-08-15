<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT * FROM setor ";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	while($row = mysqli_fetch_assoc($r)){

		$array[] = $row;
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array("transaksi_setor"=> $array));

	mysqli_close($con);
?>
<!-- 192.168.43.233 -->
