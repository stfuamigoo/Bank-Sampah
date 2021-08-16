<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'koneksi.php';

$query = "SELECT * FROM sampah ORDER BY jenissampah DESC ";
$result = mysqli_query($conn, $query);
$response = array();

$servername = '192.168.1.6';

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'id'        =>$row['id'], 
        'jenissampah'      =>$row['jenissampah'], 
        'satuan'   =>$row['satuan'],
        'harga'     =>$row['harga'],
        'keterangan'    =>$row['keterangan'],
        'picture'   =>"http://$servername" . $row['picture'])
    );
}

echo json_encode($response);

mysqli_close($conn);

?>