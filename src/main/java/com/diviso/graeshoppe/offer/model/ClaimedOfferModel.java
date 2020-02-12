package com.diviso.graeshoppe.offer.model;

import java.time.Instant;

/**
 * A Model for the User Claimed Offer entity.
 */
public class ClaimedOfferModel {

	 private String description;

	 private String promoCode;
	 
	 private Double offerDiscountAmount;
	 
	 private String offerProvider;
	 
	 private String deductionValueType;
		
	 private Long deductionValue;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Double getOfferDiscountAmount() {
		return offerDiscountAmount;
	}

	public void setOfferDiscountAmount(Double offerDiscountAmount) {
		this.offerDiscountAmount = offerDiscountAmount;
	}

	public String getOfferProvider() {
		return offerProvider;
	}

	public void setOfferProvider(String offerProvider) {
		this.offerProvider = offerProvider;
	}

	public Long getDeductionValue() {
		return deductionValue;
	}

	public void setDeductionValue(Long deductionValue) {
		this.deductionValue = deductionValue;
	}

	public String getDeductionValueType() {
		return deductionValueType;
	}

	public void setDeductionValueType(String deductionValueType) {
		this.deductionValueType = deductionValueType;
	}
}
