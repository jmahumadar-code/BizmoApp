-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: bizmo_db
-- ------------------------------------------------------
-- Server version	5.6.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping events for database 'bizmo_db'
--

--
-- Dumping routines for database 'bizmo_db'
--
/*!50003 DROP FUNCTION IF EXISTS `dist_between_points` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE FUNCTION `dist_between_points`(LATITUD_1 float, LONGITUD_1 float, LATITUD_2 float, LONGITUD_2 float, Units varchar(15)) RETURNS float
BEGIN

/*Si quieres el resultado el millas
cambia el valor 6371 en la función
por 3959*/

if Units='MILLE' then
		return (
	(acos(sin(radians(LATITUD_1)) * sin(radians(LATITUD_2)) +
	cos(radians(LATITUD_1)) * cos(radians(LATITUD_2)) *
	cos(radians(LONGITUD_1) - radians(LONGITUD_2))) * 3959) );
else
		return (
	(acos(sin(radians(LATITUD_1)) * sin(radians(LATITUD_2)) +
	cos(radians(LATITUD_1)) * cos(radians(LATITUD_2)) *
	cos(radians(LONGITUD_1) - radians(LONGITUD_2))) * 6371) );
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `haversine` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  FUNCTION `haversine`(
        lat1 FLOAT, lon1 FLOAT,
        lat2 FLOAT, lon2 FLOAT
     ) RETURNS float
    NO SQL
    DETERMINISTIC
    COMMENT 'Returns the distance in degrees on the Earth\n             between two known points of latitude and longitude'
BEGIN
    RETURN DEGREES(ACOS(
              COS(RADIANS(lat1)) *
              COS(RADIANS(lat2)) *
              COS(RADIANS(lon2) - RADIANS(lon1)) +
              SIN(RADIANS(lat1)) * SIN(RADIANS(lat2))
            ));
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_create_login`(in semail varchar(250), in spwd varchar(45))
BEGIN

if exists(select 1 from tusers t where t.email=semail) then
	select '-2' as hres;
else
	insert into  tusers(uid, email, password, date_in)
    values(UUID(), semail, md5(spwd), now());

    select uid as hres
    from tusers t
    where t.email=semail and t.password=md5(spwd);
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_get_login`(in smail varchar(250), in spwd varchar(45))
BEGIN

select
uid,
username,
fname,
lname,
picture,
fk_country,
phone,
email,
payment_status,
type_card,
call_out,
date_in,
date_off,
ifnull(type_user,'CLIENTE') as type_user
from tusers tu
where tu.email=smail
and tu.password=md5(spwd);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_setup` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_get_setup`(in iso2country varchar(5))
BEGIN

if exists(select 1 from tcountry where idcountry=idcountry) then
	select a.name, a.value
    from tsetup a
    where a.fk_country= (select idcountry from tcountry where iso2=iso2country limit 1)  and
		a.date=(select max(b.date) from
			tsetup b
            where b.name=a.name
            and b.fk_country=a.fk_country);
 else
	 select -2 as hres;
end if;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ins_pservice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_ins_pservice`(
								    in sfrom varchar(45),
								    in ilogin int,
									in fk_service int,
									in lat float,
                                    in lon float,
                                    in sdesc mediumtext,
                                    in radius float,
                                    in keywords varchar(250))
BEGIN
declare spoint varchar(250);

if exists(select 1 from tlogin where idtlogin=ilogin and fk_uid=sfrom and date_logout is null) then

	set spoint = concat('POINT(', cast(lon as char), ' ', cast(lat as char) ,')');

	insert into tservices (date_service,
						  `location`,
						  `status`,
						  `fk_from`,
						  `fk_service`,
						  `description`,
                          `radius`,
						  `keywords`)
      values(now(), GeomFromText( spoint), 'CREATED', sfrom, fk_service, sdesc,radius, keywords);

     select last_insert_id() as hres;
else
	select -2 as hres; -- no está logeado
end if;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_list_fservices` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_list_fservices`()
BEGIN

select *
from tfamily_services a, tmaster_services b
where a.idtfamily_services=b.fk_family
order by a.idtfamily_services asc, b.name asc;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_lis_country` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_lis_country`()
BEGIN

select *
from tcountry;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_lis_fam_services` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_lis_fam_services`()
BEGIN

select * from  tfamily_services;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_lis_sub_services` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_lis_sub_services`(in idtfamily_services int)
BEGIN

select tms.idtmaster_services,
	tms.name,
	tms.description,
	tms.more_detail
from tmaster_services tms
where fk_family=idtfamily_services
order by idtmaster_services;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_set_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_set_login`(in uid varchar(45), in stype varchar(5), in lat float, in lon float, in mode_movil bit)
BEGIN
declare hres int;
declare spoint varchar(250);

set spoint = concat('POINT(', cast(lon as char), ' ', cast(lat as char) ,')');

if exists(select 1 from tlogin where fk_uid=uid and date_logout is null) then
	update tlogin
    set date_logout=now()
    where fk_uid=uid and date_logout is null;
end if;

insert into tlogin(fk_uid, date_login, type_login, location, movil)
values(uid, now(), stype, GeomFromText( spoint), mode_movil);

set hres= last_insert_id();

insert into tgeolocations(geo_date, fk_idlogin, location)
values(now(), hres, GeomFromText( spoint));

select hres as hres;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_set_logout` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_set_logout`(in fk_idlogin int)
BEGIN

update  tlogin
set date_logout = now()
where idtlogin=fk_idlogin
and date_logout is null;

select ifnull( ROW_COUNT() ,0) as hres;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_set_position` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_set_position`(in ilogin int, in lat float, in lon float)
BEGIN
declare spoint varchar(250);

set spoint = concat('POINT(', cast(lon as char), ' ', cast(lat as char) ,')');

select spoint;

if exists(select 1 from tlogin where idtlogin=ilogin and date_logout is null) then
	insert into tgeolocations(geo_date, fk_idlogin, location)
    -- values(now(), ilogin,PointFromText( spoint, 4326));
    values(now(), ilogin,GeomFromText( spoint));

    select last_insert_id() as hres;
else
	select -2 as hres;
end if;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_spam_service` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_spam_service`(in idtservice int)
BEGIN
declare radius float;
declare keywords varchar(500);
declare fid_service int;
declare fk_uid varchar(45);
declare lat float;
declare lon float;

select ts.fk_from, ts.fk_service, ts.radius, ts.keywords, y(ts.location), x(ts.location)
into fk_uid, fid_service, radius, keywords, lat, lon
from tservices ts
where ts.idtservices=idtservice
and ts.status='CREATED';

drop temporary table if exists hres;
create temporary table hres(
	uid varchar(45),
    fname varchar(150),
    lname varchar(150),
    phone varchar(15),
    email varchar(150),
    gender varchar(15),
    role varchar(15),
    valuation smallint,
    profile_name varchar(150),
    description varchar(150),
    lat float,
    lon float,
    distancia float);

if keywords is not null  then
    insert into hres
	select  distinct tu.uid,
			tu.fname,
			tu.lname,
			tu.phone,
			tu.email,
            tu.gender,
            tu.role,
            tp.valuation,
			null,
            null,
            y(tg.location) as lat,
            x(tg.location) as lon,
			dist_between_points(lat, lon,y(tg.location), x(tg.location), 'kms' ) as distancia
	from tusers tu, tprofile tp, tlogin tl, tgeolocations tg
	where tu.uid=tp.fk_uid and tl.fk_uid=tu.uid and tl.idtlogin=tg.fk_idlogin
    and tl.date_logout is null
    and (tp.name like concat('%', keywords,'%') or tp.description like concat('%', keywords,'%'))
    and tg.idtgeolocations=(select x.idtgeolocations from tgeolocations x where x.fk_idlogin=tl.idtlogin
							order by x.geo_date desc limit 1);

    -- and st_within(tg.location, envelope(linestring(point(rlon1, rlat1), point(rlon2, rlat2))))
    update hres
    set profile_name= (select x.name from tprofile x
                             where x.fk_uid=hres.uid
							 and (x.name like concat('%', keywords,'%') or x.description like concat('%', keywords,'%'))
                             limit 1);
    update hres
    set description= (select x.description from tprofile x
                             where x.fk_uid=hres.uid
							 and (x.name like concat('%', keywords,'%') or x.description like concat('%', keywords,'%'))
                             limit 1);
   select *
   from hres
   order by distancia asc;

else
	select  distinct tu.uid,
			tu.fname,
			tu.lname,
			tu.phone,
			tu.email,
			tp.name as profile_name,
            tu.gender,
            tu.role,
            tp.valuation,
            tp.description,
            y(tg.location) as lat,
            x(tg.location) as lon,
			dist_between_points(lat, lon,y(tg.location), x(tg.location), 'kms' ) as distancia
	from tusers tu, tprofile tp, tlogin tl, tgeolocations tg
	where tu.uid=tp.fk_uid and tl.fk_uid=tu.uid and tl.idtlogin=tg.fk_idlogin
    and tp.fk_type_service=fid_service
    and tl.date_logout is null
    and tg.idtgeolocations=(select x.idtgeolocations from tgeolocations x where x.fk_idlogin=tl.idtlogin
							order by x.geo_date desc limit 1)
    -- and st_within(tg.location, envelope(linestring(point(rlon1, rlat1), point(rlon2, rlat2))))
	order by distancia limit 10;
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-28 12:58:27
