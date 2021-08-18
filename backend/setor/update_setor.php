<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'koneksi.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $id         = $_POST['id'];
    $tanggalsetor       = $_POST['tanggalsetor'];
    $nama      = $_POST['nama'];
    $saldo_user      = $_POST['saldo_user'];
    $jenissampah      = $_POST['jenissampah'];
    $satuan     = $_POST['satuan'];
    $harga    = $_POST['harga'];
    $jumlah    = $_POST['jumlah'];
    $total    = $_POST['total'];
    $keterangan    = $_POST['keterangan'];

    $query = "UPDATE setor SET 
    tanggalsetor='$tanggalsetor',
    nama='$nama', 
    saldo_user='$saldo_user',
    jenissampah='$jenissampah',
    satuan='$satuan',
    harga='$harga',
    jumlah='$jumlah',
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