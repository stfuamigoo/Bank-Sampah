<?php
require_once('koneksi.php');

$key = $_POST['key'];

if( $key == "update") {
	$id = $_POST['id'];
	$tanggal_tarik = $_POST['tanggal_tarik'];
	$nama_user = $_POST['nama_user'];
	$saldo_user = $_POST['saldo_user'];
	$jumlah_tarik = $_POST['jumlah_tarik'];
	$keterangan = $_POST['keterangan'];

	//Membuat SQL Query
	$sql = "UPDATE tarik 
	SET tanggal_tarik = '$tanggal_tarik',
	nama_user = '$nama_user',
	saldo_user = '$saldo_user' ,
	jumlah_tarik = '$jumlah_tarik',
	keterangan = '$keterangan'
	WHERE id = '$id'";

	//Meng-update Database
	if(mysqli_query($con,$sql)){
		$result["value"] = "1";
        $result["message"] = "Success";
    
        echo json_encode($result);
        mysqli_close($con);
	}else{
		$response["value"] = "0";
        $response["message"] = "Error! ".mysqli_error($con);
        echo json_encode($response);
		mysqli_close($con);
	}
}
?>