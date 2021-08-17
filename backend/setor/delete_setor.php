<?php 

$id = $_GET['id'];

	require_once 'koneksi.php';
	
	$query = "DELETE FROM setor WHERE id = '$id'";

	if (mysqli_query($conn,$query)) {
		echo "Berhasil di hapus.";
	}else{
		echo mysqli_error();
	}

	mysqli_close($conn);


?>