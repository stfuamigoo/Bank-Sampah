<?php 
	require_once "koneksi.php";

	$sql = "SELECT * FROM user ORDER BY nama ASC";
	if(!$con->query($sql)){
		echo "Error in connection database";
	} else {
		$result = $con->query($sql);
		if($result->num_rows > 0){
			$return_arr['user'] = array();
			while($row = $result->fetch_array()){
				array_push($return_arr['user'], array(
					'id' =>$row['id'],
					'nik' =>$row['nik'],
					'password' =>$row['password'],
					'nama' =>$row['nama'],
					'alamat' =>$row['alamat'],
					'rt' =>$row['rt'],
					'telepon' =>$row['telepon'],
					'email' =>$row['email'],
					'saldo' =>$row['saldo'],
					'role' =>$row['role']
				));
			}
			echo json_encode($return_arr);
		}
	}
?>