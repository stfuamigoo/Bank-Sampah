<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'koneksi.php';

$query = "SELECT * FROM setor ORDER BY id DESC ";
$result = mysqli_query($conn, $query);
$response = array();

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'id'        =>$row['id'], 
        'tanggalsetor'      =>$row['tanggalsetor'],
        'id_user'      =>$row['id_user'],
        'nama'      =>$row['nama'],
        'saldo_user'      =>$row['saldo_user'],
        'id_sampah'      =>$row['id_sampah'],
        'jenissampah'      =>$row['jenissampah'],
        'satuan'      =>$row['satuan'],
        'harga'      =>$row['harga'], 
        'jumlah'   =>$row['jumlah'],
        'total'     =>$row['total'],
        'keterangan'    =>$row['keterangan'])
    );
}

echo json_encode($response);

mysqli_close($conn);

?>