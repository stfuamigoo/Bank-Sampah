<?php 

// header("Content-Type: application/json; charset=UTF-8");

// require_once 'koneksi.php';


// $id      = $_GET['id'];

//     $query = "DELETE FROM sampah WHERE id='$id' ";

//         if ( mysqli_query($conn, $query) ){
//             echo "Berhasil di hapus.";
//         } 
//         else {
//             echo mysqli_error();
//         }
//     mysqli_close($conn);

$id = $_GET['id'];

	require_once 'koneksi.php';
	
	$query = "DELETE FROM sampah WHERE id = '$id'";

	if (mysqli_query($conn,$query)) {
		echo "Berhasil di hapus.";
	}else{
		echo mysqli_error();
	}

	mysqli_close($conn);


?>