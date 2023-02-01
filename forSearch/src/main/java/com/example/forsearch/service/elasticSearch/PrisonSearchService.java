//package com.example.forsearch.service.elasticSearch;
//
//import com.example.forsearch.entity.Prison;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
//import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
//import org.springframework.data.elasticsearch.core.query.IndexQuery;
//import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.data.elasticsearch.core.query.StringQuery;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PrisonSearchService {
//
//    private static final String PRISON_INDEX = "prisonindex";
//
//    private final ElasticsearchOperations elasticsearchOperations;
//
//    public List<IndexedObjectInformation> createPrisonIndexBulk(final List<Prison> prisons) {
//
//        List<IndexQuery> queries =
//                prisons.stream()
//                        .map(prison ->
//                                new IndexQueryBuilder()
//                                        .withId(prison.getId().toString())
//                                        .withObject(prison).build())
//                        .collect(Collectors.toList());
//
//        return elasticsearchOperations
//                .bulkIndex(queries, IndexCoordinates.of(PRISON_INDEX));
//
//    }
//
//    public String createPrisonIndex(Prison prison) {
//
//        IndexQuery indexQuery =
//                new IndexQueryBuilder()
//                        .withId(prison.getId().toString())
//                        .withObject(prison).build();
//
//        String documentId =
//                elasticsearchOperations
//                        .index(indexQuery, IndexCoordinates.of(PRISON_INDEX));
//
//        return documentId;
//    }
//
//    public void findCityByCountry(final String countryName) {
//
//        QueryBuilder queryBuilder =
//                QueryBuilders
//                        .matchQuery("country", countryName);
//
//        Query searchQuery =
//                new NativeSearchQueryBuilder()
//                        .withQuery(queryBuilder)
//                        .build();
//
//        SearchHits<Prison> prisonHits =
//                elasticsearchOperations
//                        .search(searchQuery,
//                                Prison.class,
//                                IndexCoordinates.of(PRISON_INDEX));
//
//    }
//
//    public void findByPrisonName(final String prisonName) {
//
//        Query searchQuery =
//                new StringQuery(
//                        "{\"match\":{\"name\":{\"query\":\"" + prisonName + "\"}}}\""
//                );
//
//        SearchHits<Prison> prison =
//                elasticsearchOperations.search(
//                        searchQuery,
//                        Prison.class,
//                        IndexCoordinates.of(PRISON_INDEX)
//                );
//
//    }
//
//    public List<Prison> processSearch(final String query) {
//        log.info("Search with query {}", query);
//
//        QueryBuilder queryBuilder =
//                QueryBuilders
//                        .multiMatchQuery(query, "name", "description")
//                        .fuzziness(Fuzziness.AUTO);
//
//        Query searchQuery =
//                new NativeSearchQueryBuilder()
//                        .withFilter(queryBuilder)
//                        .build();
//
//        SearchHits<Prison> prisonHits =
//                elasticsearchOperations
//                        .search(searchQuery, Prison.class,
//                                IndexCoordinates.of(PRISON_INDEX));
//
//        List<Prison> prisonMatches = new ArrayList<Prison>();
//        prisonHits.forEach(searchHit -> {
//            prisonMatches.add(searchHit.getContent());
//        });
//
//        return prisonMatches;
//    }
//
//    public List<String> fetchSuggestions(String query) {
//
//        QueryBuilder queryBuilder =
//                QueryBuilders
//                        .wildcardQuery("name", query + "*");
//
//        Query searchquery =
//                new NativeSearchQueryBuilder()
//                        .withFilter(queryBuilder)
//                        .withPageable(PageRequest.of(0, 5))
//                        .build();
//
//        SearchHits<Prison> searchSuggestions =
//                elasticsearchOperations.search(
//                        searchquery,
//                        Prison.class,
//                        IndexCoordinates.of(PRISON_INDEX));
//
//        List<String> suggestions = new ArrayList<String>();
//
//        searchSuggestions.getSearchHits().forEach(searchHit -> {
//            suggestions.add(searchHit.getContent().getPrisonName());
//        });
//
//        return suggestions;
//    }
//
//}