package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.OfferDayService;
import com.diviso.graeshoppe.offer.domain.OfferDay;
import com.diviso.graeshoppe.offer.repository.OfferDayRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferDaySearchRepository;
import com.diviso.graeshoppe.offer.service.dto.OfferDayDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OfferDay.
 */
@Service
@Transactional
public class OfferDayServiceImpl implements OfferDayService {

    private final Logger log = LoggerFactory.getLogger(OfferDayServiceImpl.class);

    private final OfferDayRepository offerDayRepository;

    private final OfferDayMapper offerDayMapper;

    private final OfferDaySearchRepository offerDaySearchRepository;

    public OfferDayServiceImpl(OfferDayRepository offerDayRepository, OfferDayMapper offerDayMapper, OfferDaySearchRepository offerDaySearchRepository) {
        this.offerDayRepository = offerDayRepository;
        this.offerDayMapper = offerDayMapper;
        this.offerDaySearchRepository = offerDaySearchRepository;
    }

    /**
     * Save a offerDay.
     *
     * @param offerDayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfferDayDTO save(OfferDayDTO offerDayDTO) {
        log.debug("Request to save OfferDay : {}", offerDayDTO);

        OfferDay offerDay = offerDayMapper.toEntity(offerDayDTO);
        offerDay = offerDayRepository.save(offerDay);
        OfferDayDTO result = offerDayMapper.toDto(offerDay);
        offerDaySearchRepository.save(offerDay);
        return result;
    }

    /**
     * Get all the offerDays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfferDays");
        return offerDayRepository.findAll(pageable)
            .map(offerDayMapper::toDto);
    }


    /**
     * Get one offerDay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfferDayDTO> findOne(Long id) {
        log.debug("Request to get OfferDay : {}", id);
        return offerDayRepository.findById(id)
            .map(offerDayMapper::toDto);
    }

    /**
     * Delete the offerDay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfferDay : {}", id);
        offerDayRepository.deleteById(id);
        offerDaySearchRepository.deleteById(id);
    }

    /**
     * Search for the offerDay corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfferDays for query {}", query);
        return offerDaySearchRepository.search(queryStringQuery(query), pageable)
            .map(offerDayMapper::toDto);
    }
}
