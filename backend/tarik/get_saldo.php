<?php
require_once ('koneksi.php');

if(isset($_GET['nama_user'])){
    $sql = "SELECT id_user, nama_user, saldo_user FROM tarik WHERE id_user = (SELECT id_user FROM user WHERE nama = '".$_GET['nama_user']."')";
    if(!$con->query($sql)){
        echo "Error in connection database";
    } else {
        $result = $con->query($sql);
        if($result->num_rows > 0){
            $return_arr['user_saldo'] = array();
            while($row = $result->fetch_array()){
                array_push($return_arr['user_saldo'], array(
                    'id_user' =>$row['id_user'],
                    'nama_user' =>$row['nama_user'],
                    'saldo_user' =>$row['saldo_user']
                ));
            }
            echo json_encode($return_arr);
        } else{
            echo "id tidak ada";
        }
    }
}
?>