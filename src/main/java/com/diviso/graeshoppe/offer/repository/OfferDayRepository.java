package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.OfferDay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfferDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferDayRepository extends JpaRepository<OfferDay, Long> {

	List<OfferDay> findAllByOfferId(Long offerId);
	
}
