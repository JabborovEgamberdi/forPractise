package com.example.forsearch.service.elasticSearch;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.repository.PrisonElasticSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final PrisonElasticSearchRepository prisonRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    public List<Prison> fetchPrisonNames(final String name) {
        return prisonRepository.findByCityAndPrisonName(name, "");
    }

    public List<Prison> fetchPrisonNamesContaining(final String name) {
        return prisonRepository.findByCountryContaining(name);
    }

}
