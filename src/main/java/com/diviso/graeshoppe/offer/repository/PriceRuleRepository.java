package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.PriceRule;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceRuleRepository extends JpaRepository<PriceRule, Long> {

	 @Query("SELECT p FROM PriceRule p where p.id = :priceRuleId") 
	 Optional<PriceRule> findAutomaticOfferPriceRule(@Param("priceRuleId") Long priceRuleId);
	
}
