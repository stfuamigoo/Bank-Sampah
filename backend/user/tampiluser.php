<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['id'];

	//Importing database
	require_once('koneksi.php');

	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM user WHERE id=$id";
	//$sql = "SELECT * FROM kategori WHERE kdkategori=$kdkategori";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"nik"=>$row['nik'],
			"password"=>$row['password'],
			"nama"=>$row['nama'],
			"alamat"=>$row['alamat'],
			"rt"=>$row['rt'],
			"telepon"=>$row['telepon'],
			"email"=>$row['email'],
			"saldo"=>$row['saldo'],
			"role"=>$row['role']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>
