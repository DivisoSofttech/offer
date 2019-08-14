package com.diviso.graeshoppe.offer.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CustomerSelectionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CustomerSelectionSearchRepositoryMockConfiguration {

    @MockBean
    private CustomerSelectionSearchRepository mockCustomerSelectionSearchRepository;

}