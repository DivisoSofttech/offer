package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.OfferDay;
import com.diviso.graeshoppe.offer.repository.OfferDayRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferDaySearchRepository;
import com.diviso.graeshoppe.offer.service.OfferDayService;
import com.diviso.graeshoppe.offer.service.dto.OfferDayDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferDayMapper;
import com.diviso.graeshoppe.offer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.diviso.graeshoppe.offer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OfferDayResource REST controller.
 *
 * @see OfferDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class OfferDayResourceIntTest {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    @Autowired
    private OfferDayRepository offerDayRepository;

    @Autowired
    private OfferDayMapper offerDayMapper;

    @Autowired
    private OfferDayService offerDayService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.OfferDaySearchRepositoryMockConfiguration
     */
    @Autowired
    private OfferDaySearchRepository mockOfferDaySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfferDayMockMvc;

    private OfferDay offerDay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfferDayResource offerDayResource = new OfferDayResource(offerDayService);
        this.restOfferDayMockMvc = MockMvcBuilders.standaloneSetup(offerDayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferDay createEntity(EntityManager em) {
        OfferDay offerDay = new OfferDay()
            .day(DEFAULT_DAY);
        return offerDay;
    }

    @Before
    public void initTest() {
        offerDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferDay() throws Exception {
        int databaseSizeBeforeCreate = offerDayRepository.findAll().size();

        // Create the OfferDay
        OfferDayDTO offerDayDTO = offerDayMapper.toDto(offerDay);
        restOfferDayMockMvc.perform(post("/api/offer-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDayDTO)))
            .andExpect(status().isCreated());

        // Validate the OfferDay in the database
        List<OfferDay> offerDayList = offerDayRepository.findAll();
        assertThat(offerDayList).hasSize(databaseSizeBeforeCreate + 1);
        OfferDay testOfferDay = offerDayList.get(offerDayList.size() - 1);
        assertThat(testOfferDay.getDay()).isEqualTo(DEFAULT_DAY);

        // Validate the OfferDay in Elasticsearch
        verify(mockOfferDaySearchRepository, times(1)).save(testOfferDay);
    }

    @Test
    @Transactional
    public void createOfferDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerDayRepository.findAll().size();

        // Create the OfferDay with an existing ID
        offerDay.setId(1L);
        OfferDayDTO offerDayDTO = offerDayMapper.toDto(offerDay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferDayMockMvc.perform(post("/api/offer-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferDay in the database
        List<OfferDay> offerDayList = offerDayRepository.findAll();
        assertThat(offerDayList).hasSize(databaseSizeBeforeCreate);

        // Validate the OfferDay in Elasticsearch
        verify(mockOfferDaySearchRepository, times(0)).save(offerDay);
    }

    @Test
    @Transactional
    public void getAllOfferDays() throws Exception {
        // Initialize the database
        offerDayRepository.saveAndFlush(offerDay);

        // Get all the offerDayList
        restOfferDayMockMvc.perform(get("/api/offer-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())));
    }
    
    @Test
    @Transactional
    public void getOfferDay() throws Exception {
        // Initialize the database
        offerDayRepository.saveAndFlush(offerDay);

        // Get the offerDay
        restOfferDayMockMvc.perform(get("/api/offer-days/{id}", offerDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offerDay.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfferDay() throws Exception {
        // Get the offerDay
        restOfferDayMockMvc.perform(get("/api/offer-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferDay() throws Exception {
        // Initialize the database
        offerDayRepository.saveAndFlush(offerDay);

        int databaseSizeBeforeUpdate = offerDayRepository.findAll().size();

        // Update the offerDay
        OfferDay updatedOfferDay = offerDayRepository.findById(offerDay.getId()).get();
        // Disconnect from session so that the updates on updatedOfferDay are not directly saved in db
        em.detach(updatedOfferDay);
        updatedOfferDay
            .day(UPDATED_DAY);
        OfferDayDTO offerDayDTO = offerDayMapper.toDto(updatedOfferDay);

        restOfferDayMockMvc.perform(put("/api/offer-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDayDTO)))
            .andExpect(status().isOk());

        // Validate the OfferDay in the database
        List<OfferDay> offerDayList = offerDayRepository.findAll();
        assertThat(offerDayList).hasSize(databaseSizeBeforeUpdate);
        OfferDay testOfferDay = offerDayList.get(offerDayList.size() - 1);
        assertThat(testOfferDay.getDay()).isEqualTo(UPDATED_DAY);

        // Validate the OfferDay in Elasticsearch
        verify(mockOfferDaySearchRepository, times(1)).save(testOfferDay);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferDay() throws Exception {
        int databaseSizeBeforeUpdate = offerDayRepository.findAll().size();

        // Create the OfferDay
        OfferDayDTO offerDayDTO = offerDayMapper.toDto(offerDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferDayMockMvc.perform(put("/api/offer-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferDay in the database
        List<OfferDay> offerDayList = offerDayRepository.findAll();
        assertThat(offerDayList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OfferDay in Elasticsearch
        verify(mockOfferDaySearchRepository, times(0)).save(offerDay);
    }

    @Test
    @Transactional
    public void deleteOfferDay() throws Exception {
        // Initialize the database
        offerDayRepository.saveAndFlush(offerDay);

        int databaseSizeBeforeDelete = offerDayRepository.findAll().size();

        // Get the offerDay
        restOfferDayMockMvc.perform(delete("/api/offer-days/{id}", offerDay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OfferDay> offerDayList = offerDayRepository.findAll();
        assertThat(offerDayList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OfferDay in Elasticsearch
        verify(mockOfferDaySearchRepository, times(1)).deleteById(offerDay.getId());
    }

    @Test
    @Transactional
    public void searchOfferDay() throws Exception {
        // Initialize the database
        offerDayRepository.saveAndFlush(offerDay);
        when(mockOfferDaySearchRepository.search(queryStringQuery("id:" + offerDay.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(offerDay), PageRequest.of(0, 1), 1));
        // Search the offerDay
        restOfferDayMockMvc.perform(get("/api/_search/offer-days?query=id:" + offerDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferDay.class);
        OfferDay offerDay1 = new OfferDay();
        offerDay1.setId(1L);
        OfferDay offerDay2 = new OfferDay();
        offerDay2.setId(offerDay1.getId());
        assertThat(offerDay1).isEqualTo(offerDay2);
        offerDay2.setId(2L);
        assertThat(offerDay1).isNotEqualTo(offerDay2);
        offerDay1.setId(null);
        assertThat(offerDay1).isNotEqualTo(offerDay2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferDayDTO.class);
        OfferDayDTO offerDayDTO1 = new OfferDayDTO();
        offerDayDTO1.setId(1L);
        OfferDayDTO offerDayDTO2 = new OfferDayDTO();
        assertThat(offerDayDTO1).isNotEqualTo(offerDayDTO2);
        offerDayDTO2.setId(offerDayDTO1.getId());
        assertThat(offerDayDTO1).isEqualTo(offerDayDTO2);
        offerDayDTO2.setId(2L);
        assertThat(offerDayDTO1).isNotEqualTo(offerDayDTO2);
        offerDayDTO1.setId(null);
        assertThat(offerDayDTO1).isNotEqualTo(offerDayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offerDayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offerDayMapper.fromId(null)).isNull();
    }
}
