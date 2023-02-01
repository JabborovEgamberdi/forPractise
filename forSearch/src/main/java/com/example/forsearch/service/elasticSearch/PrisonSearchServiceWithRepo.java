//package com.example.forsearch.service.elasticSearch;
//
//import com.example.forsearch.entity.Prison;
//import com.example.forsearch.repository.PrisonElasticSearchRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PrisonSearchServiceWithRepo {
//
//    private final PrisonElasticSearchRepository prisonElasticSearchRepository;
//
//    public void createPrisonIndexBulk(final List<Prison> prisonList) {
//        prisonElasticSearchRepository.saveAll(prisonList);
//    }
//
//    public void createPrisonIndex(final Prison prison) {
//        prisonElasticSearchRepository.save(prison);
//    }
//
//}
