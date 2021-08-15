<?php

 //Mendapatkan Nilai ID
 $id = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM tarik WHERE id='$id'";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Transaksi Tarik';
 }else{
 echo 'Gagal Menghapus Transaksi Tarik';
 }

 mysqli_close($con);
 ?>
