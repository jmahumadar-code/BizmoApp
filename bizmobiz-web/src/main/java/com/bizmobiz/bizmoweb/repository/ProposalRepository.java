package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.OfferJob;
import com.bizmobiz.bizmoweb.domain.OfferWork;
import com.bizmobiz.bizmoweb.domain.Proposal;
import com.bizmobiz.bizmoweb.value.ProposalBasicValue;

public interface ProposalRepository extends CrudRepository<Proposal, Integer> {

	@Query("SELECT p FROM Proposal p WHERE p.offerWork = :offerWork")
	public List<Proposal> findByOfferWork(@Param("offerWork") OfferWork offerWork);
	
	@Query("SELECT p FROM Proposal p WHERE p.offerJob = :offerJob")
	public List<Proposal> findByOfferJob(@Param("offerJob") OfferJob offerJob);
	
	@Query(nativeQuery = true)
	public List<ProposalBasicValue> findByOfferWorkNative(@Param("offer_work_id") Integer offer_work_id);
}
