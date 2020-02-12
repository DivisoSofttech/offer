package com.diviso.graeshoppe.offer.service.impl;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diviso.graeshoppe.offer.domain.DeductionValueType;
import com.diviso.graeshoppe.offer.domain.Offer;
import com.diviso.graeshoppe.offer.domain.OfferDay;
import com.diviso.graeshoppe.offer.domain.OrderRule;
import com.diviso.graeshoppe.offer.domain.PriceRule;
import com.diviso.graeshoppe.offer.domain.Store;
import com.diviso.graeshoppe.offer.model.ClaimedOfferModel;
import com.diviso.graeshoppe.offer.model.OfferModel;
import com.diviso.graeshoppe.offer.model.OrderModel;
import com.diviso.graeshoppe.offer.repository.DeductionValueTypeRepository;
import com.diviso.graeshoppe.offer.repository.OfferDayRepository;
import com.diviso.graeshoppe.offer.repository.OfferRepository;
import com.diviso.graeshoppe.offer.repository.OrderRuleRepository;
import com.diviso.graeshoppe.offer.repository.PriceRuleRepository;
import com.diviso.graeshoppe.offer.repository.StoreRepository;
import com.diviso.graeshoppe.offer.repository.search.DeductionValueTypeSearchRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferSearchRepository;
import com.diviso.graeshoppe.offer.repository.search.OrderRuleSearchRepository;
import com.diviso.graeshoppe.offer.repository.search.PriceRuleSearchRepository;
import com.diviso.graeshoppe.offer.repository.search.StoreSearchRepository;
import com.diviso.graeshoppe.offer.service.AggregateCommandService;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.DeductionValueTypeMapper;
import com.diviso.graeshoppe.offer.service.mapper.OfferMapper;
import com.diviso.graeshoppe.offer.service.mapper.OrderRuleMapper;
import com.diviso.graeshoppe.offer.service.mapper.PriceRuleMapper;
import com.diviso.graeshoppe.offer.service.mapper.StoreMapper;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing Offer commands.
 */
@Service
@Transactional
public class AggregateCommandServiceImpl implements AggregateCommandService{
	 private final Logger log = LoggerFactory.getLogger(AggregateCommandServiceImpl.class);
	 
	 private final OfferRepository offerRepository;
	 
	 private final OfferMapper offerMapper;
	 
	 private final OfferSearchRepository offerSearchRepository;
	 
	 private final PriceRuleRepository priceRuleRepository;
	 
	 private final PriceRuleMapper priceRuleMapper;

	 private final PriceRuleSearchRepository priceRuleSearchRepository;
	 
	 private final OrderRuleRepository orderRuleRepository;

	 private final OrderRuleMapper orderRuleMapper;

	 private final OrderRuleSearchRepository orderRuleSearchRepository;
	 
	 private final DeductionValueTypeRepository deductionValueTypeRepository;

	 private final DeductionValueTypeMapper deductionValueTypeMapper;

     private final DeductionValueTypeSearchRepository deductionValueTypeSearchRepository;
     
     private final StoreRepository storeRepository;

     private final StoreMapper storeMapper;

     private final StoreSearchRepository storeSearchRepository;
     
     private final OfferDayRepository offerDayRepository;

	 private static final String drlFile = "Offerrule.drl";

	 private KieContainer kieContainer;
	

	public AggregateCommandServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper,
			OfferSearchRepository offerSearchRepository, PriceRuleRepository priceRuleRepository,
			PriceRuleMapper priceRuleMapper, PriceRuleSearchRepository priceRuleSearchRepository,
			OrderRuleRepository orderRuleRepository, OrderRuleMapper orderRuleMapper,
			OrderRuleSearchRepository orderRuleSearchRepository,
			DeductionValueTypeRepository deductionValueTypeRepository,
			DeductionValueTypeMapper deductionValueTypeMapper,
			DeductionValueTypeSearchRepository deductionValueTypeSearchRepository, StoreRepository storeRepository,
			StoreMapper storeMapper, StoreSearchRepository storeSearchRepository, OfferDayRepository offerDayRepository,
			KieContainer kieContainer) {
		super();
		this.offerRepository = offerRepository;
		this.offerMapper = offerMapper;
		this.offerSearchRepository = offerSearchRepository;
		this.priceRuleRepository = priceRuleRepository;
		this.priceRuleMapper = priceRuleMapper;
		this.priceRuleSearchRepository = priceRuleSearchRepository;
		this.orderRuleRepository = orderRuleRepository;
		this.orderRuleMapper = orderRuleMapper;
		this.orderRuleSearchRepository = orderRuleSearchRepository;
		this.deductionValueTypeRepository = deductionValueTypeRepository;
		this.deductionValueTypeMapper = deductionValueTypeMapper;
		this.deductionValueTypeSearchRepository = deductionValueTypeSearchRepository;
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
		this.storeSearchRepository = storeSearchRepository;
		this.offerDayRepository = offerDayRepository;
		this.kieContainer = kieContainer;
	}

	/**
	     * Save a offer.
	     *
	     * @param offerDTO the entity to save
	     * @return the persisted entity
	     */
	 @Override
	 public OfferModel saveOffer(OfferModel offerModel) {
		 log.debug("Request to save Offer : {}", offerModel);
		 
		    Offer offer=new Offer();
		    OrderRule orderRule=new OrderRule();
		 	orderRule.setPrerequisiteOrderNumber(offerModel.getPrerequisiteOrderNumber());
		 	OrderRule savedOrderRule=orderRuleRepository.save(orderRule);
		 	
		 	Optional<DeductionValueType> deductionValueType=deductionValueTypeRepository.findById(offerModel.getDeduction_value_type_id());
	
		 	PriceRule priceRule=new PriceRule();
		 	priceRule.setDeductionValueType(deductionValueType.get());
		 	priceRule.setDeductionValue(offerModel.getDeductionValue());
		 	priceRule.setStartDate(offerModel.getStartDate());
		 	priceRule.setEndDate(offerModel.getEndDate());
		 	PriceRule savedPriceRule=priceRuleRepository.save(priceRule);
		 	
		 	offer.setPromoCode(offerModel.getPromoCode());
		 	offer.setDescription(offerModel.getDescription());
		 	offer.setPriceRule(savedPriceRule);
		 	offer.setOrderRule(savedOrderRule);
		 	
		 	Offer savedOffer=offerRepository.save(offer);
		 	
		 	if(savedOffer.getId()!=null){
		 		Offer elasticOffer=offerSearchRepository.save(savedOffer);
		 	}
		 	
		 	if(offerModel.getStoreId()!=null){
		 	Store store=new Store();
		 	store.setStoreId(offerModel.getStoreId());
		 	store.setOffer(savedOffer);
		 	Store savedStore=storeRepository.save(store);
		 	offerModel.setStoreId(savedStore.getStoreId());
		 	}
		 	
		 	if(!offerModel.getOfferDays().isEmpty()) {
		 		log.info("offerday list{}",offerModel.getOfferDays());
		 		for(String day:offerModel.getOfferDays()) {
		 			OfferDay offerDay=new OfferDay();
			 		
		 			offerDay.setDay(day);
		 			offerDay.setOffer(savedOffer);
		 			OfferDay savedOfferDay=offerDayRepository.save(offerDay);
		 		}
		 	}
		 	
		 	offerModel.setId(savedOffer.getId());
		 	
	        return offerModel;
	 }

	/* *//**
	     * claim the offer.
	     *
	     * @param orderModel the entity to save
	     * @return the orderModel entity to claim the offer 
	     *//*
	@Override
	public OrderModel claimOffer(OrderModel orderModel) {
		
	     KieBase kBase=kieContainer.getKieBase();
	     KieSession ksession = kBase.newKieSession(); 
	     ksession.insert(orderModel);
	       
	     Instant instant=Instant.now();
	     orderModel.setClaimedDate(instant);
	     
	     Optional<Offer> offer=offerRepository.findByPromoCode(orderModel.getPromoCode());
	     
	     if(offer.isPresent()){
	    	 if(offer.get().getOrderRule()!=null){     
	    		 Optional<OrderRule> orderRule=orderRuleRepository.findById(offer.get().getOrderRule().getId());
	    		 ksession.insert(orderRule.get());
	    	 }
	     
	    	 if(offer.get().getPriceRule()!=null){
	    		 Optional<PriceRule> priceRule=priceRuleRepository.findById(offer.get().getPriceRule().getId());
	     		 ksession.insert(priceRule.get());
	     		 
	     		 Optional<DeductionValueType> deductionValueType=deductionValueTypeRepository.findById(priceRule.get().getDeductionValueType().getId());
	    	     if(deductionValueType.isPresent())
	    	     ksession.insert(deductionValueType.get());
	    	 }
		    
	     ksession.fireAllRules();
	     
	     if(orderModel.getOrderDiscountAmount()!=null){
	    	 orderModel.setOrderDiscountTotal(orderModel.getOrderTotal()-orderModel.getOrderDiscountAmount());
	     }
	     ksession.dispose(); 
	     }
	     else{
	    	 log.debug("****no valid offer");
	     }
	     return orderModel;	
	}*/
	    
	/**
     * claim the Automatic offer.
     *
     * @param orderModel the entity to save
     * @return the orderModel entity to claim the offer 
     */
    @Override
    public OrderModel claimAutomaticOffer(OrderModel orderModel) {
	
    	 KieBase kBase=kieContainer.getKieBase();
    	
    	 DayOfWeek day=orderModel.getClaimedDate().atOffset(ZoneOffset.UTC).getDayOfWeek();
    	 orderModel.setOfferClaimedDay(day.name());
    	 
    	 	List<Offer> offerList=new ArrayList<Offer>();
    	    List<ClaimedOfferModel> claimedOfferList=new ArrayList<ClaimedOfferModel>();
    		
    	 	//ClaimedOfferModel claimedOffer=new ClaimedOfferModel();
	    	List<Store> storeList=storeRepository.findByStoreId(orderModel.getStoreId());
	    	
	    	for(Store store:storeList) {	
	    		Optional<Offer> offer=offerRepository.findById(store.getOffer().getId());

	    		if(offer.isPresent()) {
	    			if(offer.get().getPriceRule()!=null){
	    		    	Optional<PriceRule> priceRule=priceRuleRepository.findAutomaticOfferPriceRule(offer.get().getPriceRule().getId());
	    		    	if(priceRule.isPresent()) {
	    		    		log.info("**********insert to session");
	    			    	
	    		    		KieSession ksession = kBase.newKieSession(); 
	    		    		ksession.insert(orderModel);
	    		    		
	    		    		Optional<OrderRule> orderRule=orderRuleRepository.findById(offer.get().getOrderRule().getId());
	    		    		//Optional<OfferType> offerType= offerTypeRepository.findById(offer.get().getOfferType().getId());
	    		    		
	    		    		Optional<DeductionValueType> deductionValueType=deductionValueTypeRepository.findById(priceRule.get().getDeductionValueType().getId());
	    		    		
	    		    		ksession.insert(offer.get());
	    	    			ksession.insert(priceRule.get());
	    		    		ksession.insert(orderRule.get());
	    		    		ksession.insert(deductionValueType.get());
	    		    		//ksession.insert(claimedOffer);
	    		    		
	    		    		List<OfferDay> offerDayList=offerDayRepository.findAllByOfferId(offer.get().getId());
	    		    		
	    		    		if(!offerDayList.isEmpty()) {
	    		    			for(OfferDay offerDay:offerDayList) {
	    		    				ksession.insert(offerDay);
	    		    			}
	    		    		}
	    	    		   
	    	    			int rulesFired=ksession.fireAllRules();	
	    	     		    
	    	    			log.info("******************rulesFired{}",rulesFired);
	    	    			if(orderModel.getRuleDiscountAmount()!=null && rulesFired!=0) {
	    	    				ClaimedOfferModel claimedOffer=new ClaimedOfferModel();
	    	    				
	    	    				claimedOffer.setDescription(offer.get().getDescription());
	    	    				claimedOffer.setPromoCode(offer.get().getPromoCode());
	    	    				claimedOffer.setOfferDiscountAmount(orderModel.getRuleDiscountAmount());
	    	    				claimedOffer.setOfferProvider(orderModel.getOfferProvider());
	    	    				claimedOffer.setDeductionValueType(deductionValueType.get().getDeductionValueType());
	    	    				claimedOffer.setDeductionValue(priceRule.get().getDeductionValue());
	    	    				
	    	    				claimedOfferList.add(claimedOffer);
	    	    				orderModel.setTotalDiscount(orderModel.getTotalDiscount()+orderModel.getRuleDiscountAmount());
	    	     					
	    	     			}
	    	     		 ksession.dispose(); 
	    		    	}    	
	    			}  
	    		}
	    	}
	    		if(orderModel.getTotalDiscount()!=0){
	    			orderModel.setOrderDiscountTotal(orderModel.getOrderTotal()-orderModel.getTotalDiscount());
	    			orderModel.setAppliedOffers(claimedOfferList);
     				
	    	    }
	     
	     return orderModel;	
    	
}

}
