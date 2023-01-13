package com.example.forsearch.controller.elasticSearch;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.forsearch.repository")
@ComponentScan(basePackages = {"com.example.forsearch"})
public class ElasticsearchClientConfig {

}
