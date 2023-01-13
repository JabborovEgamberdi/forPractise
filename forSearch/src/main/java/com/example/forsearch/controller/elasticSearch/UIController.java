package com.example.forsearch.controller.elasticSearch;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.service.elasticSearch.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UIController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String home(Model model) {
        List<Prison> prisons = searchService.fetchPrisonNamesContaining("Hornby");
        List<String> name = prisons.stream().flatMap(prod -> Stream.of(prod.getPrisonName())).collect(Collectors.toList());
        log.info("prison name {}", name);
        model.addAttribute("name", name);
        return "search";
    }

}
