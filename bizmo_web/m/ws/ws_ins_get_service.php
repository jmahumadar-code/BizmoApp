<?php require_once("../cnx/setup.php");
header('Content-type: application/json');
header('Cache-Control: no-cache, must-revalidate');
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

session_start();  

$sfrom= strip_tags($_GET["sFrom"]);
$ilogin= strip_tags($_GET["iLogin"]);
$fk_service= strip_tags($_GET["fk_service"]);
$flat= strip_tags($_GET["fLat"]);
$flon= strip_tags($_GET["fLon"]);
$sdesc =strip_tags($_GET["sDesc"]);
$fradius =strip_tags($_GET["fradius"]);
$skeys =strip_tags($_GET["skeys"]);

$sql ="call sp_ins_pservice('$sfrom', $ilogin, $fk_service,$flat, $flon, '$sdesc', $fradius, '$skeys');";

$rsDP= $mysqli->query($sql);
if (isset($rsDP) && $rsDP->num_rows>0){
	while ($row = $rsDP->fetch_object()) {
		$data= $row;
	}
    clearStoredResults($mysqli);
    if (isset($data)){	
         //echo json_encode($data,JSON_PRETTY_PRINT);	
         echo json_encode($data, JSON_UNESCAPED_UNICODE);	
	}
    else 
       echo '{"hres":"-1"}'; 
}
else 
   echo '{"hres":"-1"}';
?>