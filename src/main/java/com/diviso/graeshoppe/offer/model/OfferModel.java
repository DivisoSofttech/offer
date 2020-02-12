package com.diviso.graeshoppe.offer.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.diviso.graeshoppe.offer.domain.OfferDay;

/**
 * A Model for the Offer entity.
 */
public class OfferModel {
	
	private Long id;
	
	private String promoCode;
	
	private String description;
	
	private Long deduction_value_type_id;
	
    private Long deductionValue;

    private Long prerequisiteOrderNumber;
    
    private Instant startDate;

    private Instant endDate;
    
    private String storeId;
    
    private List<String> offerDays=new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDeduction_value_type_id() {
		return deduction_value_type_id;
	}

	public void setDeduction_value_type_id(Long deduction_value_type_id) {
		this.deduction_value_type_id = deduction_value_type_id;
	}

	public Long getDeductionValue() {
		return deductionValue;
	}

	public void setDeductionValue(Long deductionValue) {
		this.deductionValue = deductionValue;
	}

	public Long getPrerequisiteOrderNumber() {
		return prerequisiteOrderNumber;
	}

	public void setPrerequisiteOrderNumber(Long prerequisiteOrderNumber) {
		this.prerequisiteOrderNumber = prerequisiteOrderNumber;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public List<String> getOfferDays() {
		return offerDays;
	}

	public void setOfferDays(List<String> offerDays) {
		this.offerDays = offerDays;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	
	
}
