package com.example.forsearch.repository;

import com.example.forsearch.entity.Prison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrisonElasticSearchRepository extends ElasticsearchRepository<Prison, Integer> {

    Page<Prison> findByPrisonCountry(String country, Pageable pageable);

    @Query("{\"bool\": {\"must\" [{\"match\": {\"prison.country\": \"?0\"}}]}}")
    Page<Prison> findByCountryUsingCustomQuery(String country, Pageable pageable);

    List<Prison> findByCountry(String country);

    List<Prison> findByCountryContaining(String name);

    List<Prison> findByCityAndPrisonName(String city, String prisonName);

}