<?php
require_once('koneksi.php');

$sql = "SELECT * FROM tarik";

$result = mysqli_query($con, $sql);
$response = array();

while($row = mysqli_fetch_assoc($result)){
    array_push($response,
    array(
        'id' => $row['id'],
        'tanggal_tarik' => $row['tanggal_tarik'],
        'id_user' => $row['id_user'],
        'nama_user' => $row['nama_user'],
        'saldo_user' => $row['saldo_user'],
        'jumlah_tarik' => $row['jumlah_tarik'],
        'keterangan' => $row['keterangan'])
    );
}
echo json_encode($response);
mysqli_close($con);
?>