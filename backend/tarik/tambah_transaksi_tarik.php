<?php 

require_once 'koneksi.php';

$key = $_POST['key'];

$tanggal_tarik       = $_POST['tanggal_tarik'];
$id_user    = $_POST['id_user'];
$nama_user    = $_POST['nama_user'];
$saldo_user    = $_POST['saldo_user'];
$jumlah_tarik      = $_POST['jumlah_tarik'];
$keterangan     = $_POST['keterangan'];

if ( $key == "insert" ){
    $birth_newformat = date('Y-m-d', strtotime($tanggal_tarik));

    $query = "INSERT INTO tarik (tanggal_tarik, id_user, nama_user, saldo_user, jumlah_tarik, keterangan)
    VALUES ('$birth_newformat', '$id_user', '$nama_user', '$saldo_user', '$jumlah_tarik', '$keterangan') ";

        if ( mysqli_query($con, $query) ){
            $response["value"] = "1";
            $response["message"] = "Success";

            echo json_encode($response);
            mysqli_close($con);
        } else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($con);
            echo json_encode($response);

            mysqli_close($con);
        }
}

?>