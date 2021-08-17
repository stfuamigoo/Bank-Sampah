<?php 
	require_once "koneksi.php";

	$sql = "SELECT * FROM sampah ORDER BY jenissampah ASC";
	if(!$con->query($sql)){
		echo "Error in connection database";
	} else {
		$result = $con->query($sql);
		if($result->num_rows > 0){
			$return_arr['sampah'] = array();
			while($row = $result->fetch_array()){
				array_push($return_arr['sampah'], array(
					'id' =>$row['id'],
					'jenissampah' =>$row['jenissampah'],
					'satuan' =>$row['satuan'],
					'harga' =>$row['harga'],
					'keterangan' =>$row['keterangan'],
					'picture' =>$row['picture']
				));
			}
			echo json_encode($return_arr);
		}
	}
?>