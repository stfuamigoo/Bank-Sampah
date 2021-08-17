<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'koneksi.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $id         = $_POST['id'];
    $tanggalsetor       = $_POST['tanggalsetor'];
    $id_user    = $_POST['id_user'];
    $id_sampah      = $_POST['id_sampah'];
    $nama      = $_POST['nama'];
    $jenissampah      = $_POST['jenissampah'];
    $harga    = $_POST['harga'];
    $berat     = $_POST['berat'];
    $total    = $_POST['total'];
    $keterangan    = $_POST['keterangan'];

    $query = "UPDATE setor SET 
    tanggalsetor='$tanggalsetor',
    id_user='$id_user',
    id_sampah='$id_sampah', 
    nama='$nama', 
    jenissampah='$jenissampah',
    hargasampah='$hargasampah',
    beratsampah='$beratsampah',
    total='$total',
    keterangan='$keterangan'
    WHERE id='$id' ";

        if ( mysqli_query($conn, $query) ){
                        
                        $result["value"] = "1";
                        $result["message"] = "Success!";
            
                        echo json_encode($result);
                        mysqli_close($conn);

        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}

?>