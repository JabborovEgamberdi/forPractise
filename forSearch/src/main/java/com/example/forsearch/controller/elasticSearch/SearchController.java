package com.example.forsearch.controller.elasticSearch;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.service.elasticSearch.PrisonSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final PrisonSearchService prisonSearchService;

    @GetMapping("/prison")
    @ResponseBody
    public List<Prison> fetchByNameOrDesc(@RequestParam(value = "q", required = false) String query) {
        log.info("searching by prisonName {}", query);
        List<Prison> suggests = prisonSearchService.processSearch(query);
        log.info("suggest {}", suggests);
        return suggests;
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        log.info("fetch suggests {}", query);
        List<String> suggests = prisonSearchService.fetchSuggestions(query);
        log.info("suggests {}", suggests);
        return suggests;
    }

}