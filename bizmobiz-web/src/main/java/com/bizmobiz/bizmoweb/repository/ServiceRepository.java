package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.LevelService;
import com.bizmobiz.bizmoweb.domain.Service;
import com.bizmobiz.bizmoweb.value.ServiceBasic;

public interface ServiceRepository extends CrudRepository<Service, Integer> {

	@Query("SELECT s FROM Service s WHERE s.levelService = :levelService")
	public List<Service> findByLevelService(@Param("levelService") LevelService levelService);
	
	public List<ServiceBasic> findByTagIds(@Param("tagIds") List<Integer> tagIds);
}
