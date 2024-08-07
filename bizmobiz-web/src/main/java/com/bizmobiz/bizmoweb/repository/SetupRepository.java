package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.Country;
import com.bizmobiz.bizmoweb.domain.Setup;

public interface SetupRepository extends CrudRepository<Setup, Integer> {

	public List<Setup> findByCountry(Country country);
	
	@Query("SELECT DISTINCT s FROM Setup s WHERE country = :country AND key = :key")
	public Setup findOneByCountryKey(@Param("country") Country country, @Param("key") String key);
}
