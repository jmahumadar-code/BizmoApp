package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.User;
import com.bizmobiz.bizmoweb.domain.OfferJob;
import com.bizmobiz.bizmoweb.value.OfferJobDistanceValue;
import com.bizmobiz.bizmoweb.value.OfferJobOfferDistanceValue;

public interface OfferJobRepository extends CrudRepository<OfferJob, Integer> {
	
	@Query("SELECT o FROM OfferJob o WHERE o.offer = :offer")
	public OfferJob findOneByOffer(@Param("offer") User offer);

	@Query(nativeQuery = true)
	public List<OfferJobDistanceValue> find10ByPositionRadiusCountry(@Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("radius") Integer radius, @Param("country_id") Integer country_id);
	
	@Query(nativeQuery = true)
	public List<OfferJobOfferDistanceValue> findAllByServicePositionRadiusCountry(@Param("service_id") Integer service_id, @Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("radius") Integer radius, @Param("country_id") Integer country_id);
}
