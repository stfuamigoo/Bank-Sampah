<?php

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT * FROM user";

	//Mendapatkan Hasil
	$result = mysqli_query($con,$sql);
	$response = array();

	while($row = mysqli_fetch_assoc($result)){
		array_push($response, 
		array(
			'id'        =>$row['id'], 
			'nik'      =>$row['nik'], 
			'password'   =>$row['password'],
			'nama'     =>$row['nama'],
			'alamat'    =>$row['alamat'],
			'rt'    =>$row['rt'],
			'telepon'    =>$row['telepon'],
			'email'    =>$row['email'],
			'saldo'    =>$row['saldo'],
			'role'   =>$row['role'])
		);
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode($response);

	mysqli_close($con);
?>
<!-- 192.168.43.233 -->
