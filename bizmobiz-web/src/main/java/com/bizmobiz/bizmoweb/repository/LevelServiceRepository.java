package com.bizmobiz.bizmoweb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.LevelService;

public interface LevelServiceRepository extends CrudRepository<LevelService, Integer> {

	@Query("SELECT l FROM LevelService l WHERE l.level = :level")
	public LevelService findOneByLevel(@Param("level") Integer level);
}
