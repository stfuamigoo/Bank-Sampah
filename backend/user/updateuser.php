<?php
require_once('koneksi.php');

$key = $_POST['key'];

if( $key == "update") {
	$id = $_POST['id'];
	$nik = $_POST['nik'];
	$password = $_POST['password'];
	$nama = $_POST['nama'];
	$alamat = $_POST['alamat'];
	$rt = $_POST['rt'];
	$telepon = $_POST['telepon'];
	$email = $_POST['email'];
	$saldo = $_POST['saldo'];
	$role = $_POST['role'];

	//Membuat SQL Query
	$sql = "UPDATE user 
	SET nik = '$nik',
	password = '$password',
	nama = '$nama',
	alamat = '$alamat' ,
	rt = '$rt',
	telepon = '$telepon',
	email = '$email',
	saldo = '$saldo',
	role = '$role'
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
