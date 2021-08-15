<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'koneksi.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $id         = $_POST['id'];
    $jenissampah       = $_POST['jenissampah'];
    $satuan    = $_POST['satuan'];
    $harga      = $_POST['harga'];
    $keterangan     = $_POST['keterangan'];
    $picture    = $_POST['picture'];

    $query = "UPDATE sampah SET 
    jenissampah='$jenissampah', 
    satuan='$satuan', 
    harga='$harga',
    keterangan='$keterangan'
    WHERE id='$id' ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

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