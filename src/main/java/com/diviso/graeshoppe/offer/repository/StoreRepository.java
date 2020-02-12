package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.Store;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	 @Query("SELECT s FROM Store s where s.storeId = :storeId") 
	 List<Store> findByStoreId(@Param("storeId") String storeId);
	
}
