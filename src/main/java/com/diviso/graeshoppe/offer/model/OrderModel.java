package com.diviso.graeshoppe.offer.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
	
	private String promoCode;

	private Long orderNumber;
	
	private Instant claimedDate;

	private Double orderTotal;
	
	//private Double orderDiscountAmount;
	
	private Double orderDiscountTotal;
	
	private Double ruleDiscountAmount;
	
	private double totalDiscount=0;
	
	private String storeId;
	
	private List<ClaimedOfferModel> appliedOffers=new ArrayList<ClaimedOfferModel>();
	
	private String offerClaimedDay;
	
	private String offerProvider;
	
	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Double getOrderDiscountTotal() {
		return orderDiscountTotal;
	}

	public void setOrderDiscountTotal(Double orderDiscountTotal) {
		this.orderDiscountTotal = orderDiscountTotal;
	}

	public Instant getClaimedDate() {
		return claimedDate;
	}

	public void setClaimedDate(Instant claimedDate) {
		this.claimedDate = claimedDate;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Double getRuleDiscountAmount() {
		return ruleDiscountAmount;
	}

	public void setRuleDiscountAmount(Double ruleDiscountAmount) {
		this.ruleDiscountAmount = ruleDiscountAmount;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public List<ClaimedOfferModel> getAppliedOffers() {
		return appliedOffers;
	}

	public void setAppliedOffers(List<ClaimedOfferModel> appliedOffers) {
		this.appliedOffers = appliedOffers;
	}

	public String getOfferClaimedDay() {
		return offerClaimedDay;
	}

	public void setOfferClaimedDay(String offerClaimedDay) {
		this.offerClaimedDay = offerClaimedDay;
	}

	public String getOfferProvider() {
		return offerProvider;
	}

	public void setOfferProvider(String offerProvider) {
		this.offerProvider = offerProvider;
	}

	
}
