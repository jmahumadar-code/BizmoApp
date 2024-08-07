package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.ImageWork;
import com.bizmobiz.bizmoweb.domain.OfferWork;

public interface ImageWorkRepository extends CrudRepository<ImageWork, Integer> {

	@Query("SELECT i FROM ImageWork i WHERE offerWork = :offerWork")
	public List<ImageWork> findByOfferWork(@Param("offerWork") OfferWork offerWork);
}
