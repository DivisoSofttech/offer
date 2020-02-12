package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.OfferDayDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OfferDay.
 */
public interface OfferDayService {

    /**
     * Save a offerDay.
     *
     * @param offerDayDTO the entity to save
     * @return the persisted entity
     */
    OfferDayDTO save(OfferDayDTO offerDayDTO);

    /**
     * Get all the offerDays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferDayDTO> findAll(Pageable pageable);


    /**
     * Get the "id" offerDay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OfferDayDTO> findOne(Long id);

    /**
     * Delete the "id" offerDay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the offerDay corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferDayDTO> search(String query, Pageable pageable);
}
