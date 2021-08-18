<?php
require_once 'koneksi.php';

$key = $_POST['key'];

if( $key == "insert") {
    $id = $_POST['id'];
	$saldo = $_POST['saldo'];

	//Membuat SQL Query
	$sql = "UPDATE user 
	SET saldo = '$saldo'
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

if( $key == "update") {
    $id = $_POST['id'];
	$saldo = $_POST['saldo'];

	//Membuat SQL Query
	$sql = "UPDATE user 
	SET saldo = '$saldo'
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

if( $key == "delete") {
    $id = $_POST['id'];
	$saldo = $_POST['saldo'];

	//Membuat SQL Query
	$sql = "UPDATE user 
	SET saldo = '$saldo'
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