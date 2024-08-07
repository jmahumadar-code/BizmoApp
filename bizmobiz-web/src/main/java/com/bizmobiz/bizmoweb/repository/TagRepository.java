package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {

	@Query("SELECT DISTINCT t FROM Tag t WHERE UPPER(t.value) LIKE CONCAT('%',UPPER(:tagValue),'%')")
	public List<Tag> findByLikeTagValue(@Param("tagValue") String tagValue);
}
