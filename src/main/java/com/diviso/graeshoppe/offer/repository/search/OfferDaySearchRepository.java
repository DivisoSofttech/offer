package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.OfferDay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OfferDay entity.
 */
public interface OfferDaySearchRepository extends ElasticsearchRepository<OfferDay, Long> {
}
