package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.OfferDayService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.OfferDayDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OfferDay.
 */
@RestController
@RequestMapping("/api")
public class OfferDayResource {

    private final Logger log = LoggerFactory.getLogger(OfferDayResource.class);

    private static final String ENTITY_NAME = "offerOfferDay";

    private final OfferDayService offerDayService;

    public OfferDayResource(OfferDayService offerDayService) {
        this.offerDayService = offerDayService;
    }

    /**
     * POST  /offer-days : Create a new offerDay.
     *
     * @param offerDayDTO the offerDayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offerDayDTO, or with status 400 (Bad Request) if the offerDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offer-days")
    @Timed
    public ResponseEntity<OfferDayDTO> createOfferDay(@RequestBody OfferDayDTO offerDayDTO) throws URISyntaxException {
        log.debug("REST request to save OfferDay : {}", offerDayDTO);
        if (offerDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new offerDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferDayDTO result = offerDayService.save(offerDayDTO);
        return ResponseEntity.created(new URI("/api/offer-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offer-days : Updates an existing offerDay.
     *
     * @param offerDayDTO the offerDayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offerDayDTO,
     * or with status 400 (Bad Request) if the offerDayDTO is not valid,
     * or with status 500 (Internal Server Error) if the offerDayDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offer-days")
    @Timed
    public ResponseEntity<OfferDayDTO> updateOfferDay(@RequestBody OfferDayDTO offerDayDTO) throws URISyntaxException {
        log.debug("REST request to update OfferDay : {}", offerDayDTO);
        if (offerDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferDayDTO result = offerDayService.save(offerDayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offerDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offer-days : get all the offerDays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offerDays in body
     */
    @GetMapping("/offer-days")
    @Timed
    public ResponseEntity<List<OfferDayDTO>> getAllOfferDays(Pageable pageable) {
        log.debug("REST request to get a page of OfferDays");
        Page<OfferDayDTO> page = offerDayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offer-days");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /offer-days/:id : get the "id" offerDay.
     *
     * @param id the id of the offerDayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offerDayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offer-days/{id}")
    @Timed
    public ResponseEntity<OfferDayDTO> getOfferDay(@PathVariable Long id) {
        log.debug("REST request to get OfferDay : {}", id);
        Optional<OfferDayDTO> offerDayDTO = offerDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerDayDTO);
    }

    /**
     * DELETE  /offer-days/:id : delete the "id" offerDay.
     *
     * @param id the id of the offerDayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offer-days/{id}")
    @Timed
    public ResponseEntity<Void> deleteOfferDay(@PathVariable Long id) {
        log.debug("REST request to delete OfferDay : {}", id);
        offerDayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/offer-days?query=:query : search for the offerDay corresponding
     * to the query.
     *
     * @param query the query of the offerDay search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/offer-days")
    @Timed
    public ResponseEntity<List<OfferDayDTO>> searchOfferDays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfferDays for query {}", query);
        Page<OfferDayDTO> page = offerDayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/offer-days");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
