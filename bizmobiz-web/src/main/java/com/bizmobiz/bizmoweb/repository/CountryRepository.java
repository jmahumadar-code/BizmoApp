package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {

	@Query("SELECT c FROM Country c WHERE c.name = :name")
	public Country findOneByName(@Param("name") String name);
	
	@Query("SELECT c FROM Country c WHERE c.code = :code")
	public Country findOneByCode(@Param("code") String code);
	
	@Query("SELECT c FROM Country c WHERE c.isEnabled = :isEnabled")
	public List<Country> findByIsEnabled(@Param("isEnabled") Boolean isEnabled);
}
