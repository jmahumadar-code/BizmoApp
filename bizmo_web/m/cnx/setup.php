<?php
$host	= "localhost";
$dbase	= "bizmo_db";
$user	= "root";
$pass	= "adminsql";
$mysqli = new mysqli($host, $user, $pass, $dbase);
if(mysqli_connect_errno()){
	echo mysqli_connect_error();
	}
if (!$mysqli->set_charset("utf8")) {
    printf("Error cargando el conjunto de caracteres utf8: %s\n", $mysqli->error);
} 

#------------------------------------------
function clearStoredResults($mysqli_link){
#------------------------------------------
    while($mysqli_link->next_result()){
      if($l_result = $mysqli_link->store_result()){
              $l_result->free();
      }
    }
}
?>