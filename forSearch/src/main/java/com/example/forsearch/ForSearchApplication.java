package com.example.forsearch;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.repository.PrisonElasticSearchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ForSearchApplication {

    private static final String COMMA_DELIMITER = ",";
    private final ElasticsearchOperations elasticsearchOperations;

    private final PrisonElasticSearchRepository prisonRepository;

    public static void main(String[] args) {
        SpringApplication.run(ForSearchApplication.class, args);
    }

    @PostConstruct
    public void buildIndex() {
        elasticsearchOperations.indexOps(Prison.class).refresh();
        prisonRepository.saveAll(prepareDataset());
    }

    private Collection<Prison> prepareDataset() {
        Resource resource = new ClassPathResource("fashion-products.csv");
        List<Prison> prisonList = new ArrayList<>();

        try (
                InputStream inputStream = resource.getInputStream();
                Scanner scanner = new Scanner(resource.getInputStream());) {
            int lineNo = 0;
            while (scanner.hasNextLine()) {
                ++lineNo;
                String line = scanner.nextLine();
                if (lineNo == 1) continue;
                Optional<Prison> prison = csvRowToPrisonMapper(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prisonList;
    }

    private Optional<Prison> csvRowToPrisonMapper(final String line) {
        try (
                Scanner rowScanner = new Scanner(line);) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                String name = rowScanner.next();
                String city = rowScanner.next();
                String country = rowScanner.next();
                return Optional.of(
                        Prison.builder()
                                .prisonName(name)
                                .city(city)
                                .country(country)
                                .build()
                );
            }
        }
        return Optional.empty();
    }
}
