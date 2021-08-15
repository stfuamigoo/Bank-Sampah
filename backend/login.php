<?php
	if ($_SERVER['REQUEST_METHOD']=='POST') {
		
		$nik = $_POST['nik'];
		$password = $_POST['password'];
		
		require_once 'koneksi.php';
		
		$sql = "SELECT * FROM user WHERE nik='$nik'";
		
		$response = mysqli_query($con, $sql);
		
		$result = array();
		$result['login'] = array();
		
		if ( mysqli_num_rows($response) === 1 ) {
			
			$row = mysqli_fetch_assoc($response);
			
			if ( $password == $row['password'] ) {
				
				$index['id'] = $row['id'];
				$index['nik'] = $row['nik'];
				$index['nama'] = $row['nama'];
				$index['password'] = $row['password'];
				$index['alamat'] = $row['alamat'];
				$index['rt'] = $row['rt'];
				$index['telepon'] = $row['telepon'];
				$index['email'] = $row['email'];
				$index['saldo'] = $row['saldo'];
				$index['role'] = $row['role'];
				
				array_push($result['login'], $index);
				
				$result['success'] = "1";
				$result['message'] = "success";
				echo json_encode($result);

				mysqli_close($con);
				
			} else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($con);
			
        }
		
    }
	
}
?>