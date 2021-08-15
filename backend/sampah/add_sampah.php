<?php 

require_once 'koneksi.php';

$key = $_POST['key'];

$jenissampah       = $_POST['jenissampah'];
$satuan    = $_POST['satuan'];
$harga      = $_POST['harga'];
$keterangan     = $_POST['keterangan'];
$picture    = $_POST['picture'];

if ( $key == "insert" ){

    $query = "INSERT INTO sampah (jenissampah,satuan,harga,keterangan)
    VALUES ('$jenissampah', '$satuan', '$harga', '$keterangan') ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $finalPath = "/Bank-Sampah/backend/sampah/fotosampah/bg/"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

                $id = mysqli_insert_id($conn);
                $path = "fotosampah/".$jenissampah.".jpeg";
                $finalPath = "/Bank-Sampah/backend/sampah/$path";

                $insert_picture = "UPDATE sampah SET picture='$finalPath' WHERE id='$id' ";
            
                if (mysqli_query($conn, $insert_picture)) {
            
                    if ( file_put_contents( $path, base64_decode($picture) ) ) {
                        
                        $result["value"] = "1";
                        $result["message"] = "Success!";
            
                        echo json_encode($result);
                        mysqli_close($conn);
            
                    } else {
                        
                        $response["value"] = "0";
                        $response["message"] = "Error! ".mysqli_error($conn);
                        echo json_encode($response);

                        mysqli_close($conn);
                    }

                }
            }

        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}

?>