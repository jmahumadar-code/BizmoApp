<?php require_once("../cnx/setup.php");
header('Content-type: application/json');
header('Cache-Control: no-cache, must-revalidate');
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

session_start();  

$sUID = strip_tags($_GET["sUID"]);
$sType = strip_tags($_GET["sType"]);
$flat = strip_tags($_GET["lat"]);
$flon = strip_tags($_GET["lon"]);
$bmovil = strip_tags($_GET["mmovil"]);

$sql ="call  sp_set_login('$sUID', '$sType', $flat, $flon, $bmovil)";
$rsDP= $mysqli->query($sql);

if (isset($rsDP) && $rsDP->num_rows>0){
	while ($row = $rsDP->fetch_object()) {
		$data= $row;
	}
    clearStoredResults($mysqli);
    if (isset($data)){	
         // echo json_encode($data,JSON_PRETTY_PRINT);	
         echo json_encode($data, JSON_UNESCAPED_UNICODE);	
	}
    else 
       echo '{"hres":"-1"}'; 
}
else 
   echo '{"hres":"-1"}';
?>
