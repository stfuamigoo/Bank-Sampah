<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT tarik.id, tarik.tanggal_tarik, user.id, user.nama, tarik.jumlah_tarik, tarik.keterangan 
	FROM `tarik` 
	INNER JOIN user ON tarik.id_user = user.id;";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	while($row = mysqli_fetch_assoc($r)){

		$array[] = $row;
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array("histori_tarik"=> $array));

	mysqli_close($con);
?>
<!-- 192.168.43.233 -->
