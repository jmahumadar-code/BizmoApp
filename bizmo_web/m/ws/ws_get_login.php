<?php require_once("../cnx/setup.php");
header('Content-type: application/json');
header('Cache-Control: no-cache, must-revalidate');
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

session_start();  

$sMail = strip_tags($_GET["sMail"]);
$sPWD = strip_tags($_GET["sPWD"]);
$sPais= strip_tags($_GET["sPais"]);

$sql ="call  sp_get_login('$sMail','$sPWD');";
$rsDP= $mysqli->query($sql);

if (isset($rsDP) && $rsDP->num_rows>0){
    $row = mysqli_fetch_array($rsDP, MYSQLI_ASSOC);
    $info_campo = $rsDP->fetch_fields();
    $str='';
    foreach ($info_campo as $valor) {
		$str=$str. "\"$valor->name\": \"". $row[$valor->name] ."\",\n";
     }	 
    clearStoredResults($mysqli);
    if (strlen($str)>0){	
// creamos  token
     $_SESSION["token"] = md5(uniqid(mt_rand(), true));
  	 $sql ="call  sp_get_setup('$sPais');";
     $rsDP= $mysqli->query($sql);
       if (isset($rsDP) && $rsDP->num_rows>0){
			while ($row = $rsDP->fetch_object()) {
				$clave=$row->name;
				$valor=$row->value;
				$str=$str. "\"$clave\" : \"$valor\",\n"; 
			}
		$data="{" . substr($str,0,-2) ."}";
		 echo $data;
    	 }
		 else 
		   echo '{"hres":"-1"}'; 
	}
    else 
       echo '{"hres":"-1"}'; 
}
else 
   echo '{"hres":"-1"}';
?>

   