package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.OfferDay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfferDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferDayRepository extends JpaRepository<OfferDay, Long> {

}
