<?php require_once("../cnx/setup.php");
header('Content-type: application/json');
header('Cache-Control: no-cache, must-revalidate');
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

session_start();  
$iFam = strip_tags($_GET["iFam"]);

$sql ="call sp_lis_sub_services($iFam)";
$rsDP= $mysqli->query($sql);
if (isset($rsDP) && $rsDP->num_rows>0){
	while ($row = $rsDP->fetch_object()) {
		$data[]= $row;
	}
    clearStoredResults($mysqli);
    if (isset($data)){	
         echo json_encode($data,JSON_PRETTY_PRINT);	
         //echo json_encode($data, JSON_UNESCAPED_UNICODE);	
	}
    else 
       echo '{"hres":"-1"}'; 
}
else 
   echo '{"hres":"-1"}';
?>