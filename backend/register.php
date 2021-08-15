<?php
	/* ========= KALAU PAKAI MYSQLI YANG ATAS SEMUA DI REMARK, TERUS YANG INI DI UNREMARK ======== */
	 include_once "koneksi.php";

	$nik = $_POST["nik"];
	$password = $_POST["password"];
	$nama = $_POST["nama"];
	$alamat = $_POST["alamat"];
	$rt = $_POST["rt"];
	$telepon = $_POST["telepon"];
	$email = $_POST["email"];
	$saldo = $_POST["saldo"];
	$role = $_POST["role"];
	
	$sql = "INSERT INTO user(nik ,password, nama, alamat, rt, telepon, email, saldo, role) VALUES ('$nik', '$password', '$nama', '$alamat', '$rt', '$telepon', '$email', '$saldo', '$role')";

	$result = mysqli_query($con, $sql);

	if($result){
		echo "Register Berhasil";
	} else {
		echo "Register Gagal";
	}
?>	