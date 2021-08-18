<?php 

require_once 'koneksi.php';

$key = $_POST['key'];

    $tanggalsetor       = $_POST['tanggalsetor'];
    $id_user    = $_POST['id_user'];
    $nama      = $_POST['nama'];
    $saldo_user      = $_POST['saldo_user'];
    $id_sampah      = $_POST['id_sampah'];
    $jenissampah      = $_POST['jenissampah'];
    $satuan     = $_POST['satuan'];
    $harga    = $_POST['harga'];
    $jumlah     = $_POST['jumlah'];
    $total    = $_POST['total'];
    $keterangan    = $_POST['keterangan'];

if ( $key == "insert" ){

    $birth_newformat = date('Y-m-d', strtotime($tanggalsetor));

    $query = "INSERT INTO setor (tanggalsetor, id_user, nama, saldo_user, id_sampah, jenissampah, satuan, harga, jumlah, total, keterangan)
    VALUES ('$birth_newformat', '$id_user', '$nama', '$saldo_user', '$id_sampah', '$jenissampah', '$satuan', '$harga', '$jumlah', '$total', '$keterangan') ";

        if ( mysqli_query($conn, $query) ){

                $id = mysqli_insert_id($conn);
                        
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