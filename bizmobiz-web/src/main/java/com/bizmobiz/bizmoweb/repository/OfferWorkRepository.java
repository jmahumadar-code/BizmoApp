package com.bizmobiz.bizmoweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.OfferWork;
import com.bizmobiz.bizmoweb.domain.OfferWorkState;
import com.bizmobiz.bizmoweb.value.OfferWorkDistanceValue;

public interface OfferWorkRepository extends CrudRepository<OfferWork, Integer> {
	
	@Query(value = "SELECT * FROM offer_work o WHERE o.client_id = :client_id ORDER BY o.idoffer_work DESC LIMIT 1", nativeQuery = true)
	public OfferWork findLastByClient(@Param("client_id") Integer client_id);

	@Query("SELECT o FROM OfferWork o WHERE o.workDate <= :now AND o.state = :state")
	public List<OfferWork> findAllByMinorWorkDateState(@Param("now") Date now, @Param("state") OfferWorkState state);

	@Query(nativeQuery = true, value = "SELECT * FROM offer_work o WHERE (o.work_date + INTERVAL :minutes MINUTE) <= :now AND o.offer_work_state_id = :state_id")
	public List<OfferWork> findAllByProposalTimeout(@Param("minutes") Integer minutes, @Param("now") String now,
			@Param("state_id") Integer state_id);

	@Query(nativeQuery = true)
	public List<OfferWorkDistanceValue> findAllInProposal(@Param("latitud") Double latitud,
			@Param("longitud") Double longitud, @Param("radius") Integer radius,
			@Param("country_id") Integer country_id, @Param("service_id") Integer service_id,
			@Param("state_id") Integer state_id);
}
