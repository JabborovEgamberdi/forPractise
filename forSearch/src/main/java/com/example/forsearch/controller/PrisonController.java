package com.example.forsearch.controller;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.exception.ResourceNotFoundException;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.PrisonDTO;
import com.example.forsearch.repository.PrisonRepository;
import com.example.forsearch.service.PrisonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/prison")
public class PrisonController {

    private final PrisonService prisonService;

    private final PrisonRepository prisonRepository;

    public PrisonController(PrisonService prisonService, PrisonRepository prisonRepository) {
        this.prisonService = prisonService;
        this.prisonRepository = prisonRepository;
    }

    @GetMapping
    public HttpEntity<?> getAll() {

        List<Prison> prisonList = prisonService.getAll();
        return new ResponseEntity<>(prisonList, prisonList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Prison getPrisonById = prisonService.getById(id);
        return new ResponseEntity<>(getPrisonById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public HttpEntity<?> addPrison(@RequestBody PrisonDTO prisonDTO) {
        try {
            ApiResponse addPrison = prisonService.addPrison(prisonDTO);
            return new ResponseEntity<>(addPrison, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            e.getMessage();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false)
            );
        }
    }

    @PostMapping("/addPrison3")
    public HttpEntity<?> addPrison3(@RequestBody Prison prison) {
        try {
            Prison save = prisonRepository.save(prison);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false)
            );
        }
    }


    @PutMapping("/updatePrison2/{id}")
    public HttpEntity<?> updatePrison2(@PathVariable Integer id,
                                       @RequestBody PrisonDTO prisonDTO) {
        return prisonRepository.findById(id)
                .map(prison -> {
                    prison.setCountry(prisonDTO.getCountry());
                    prison.setCity(prisonDTO.getCity());
                    prison.setPrisonName(prisonDTO.getPrisonName());
                    prison.setFraud(prisonDTO.getFraudId());
                    return new ResponseEntity<>(prisonRepository.save(prison), HttpStatus.OK);
                }).orElseThrow(() -> new ResourceNotFoundException("There is no prison with this id" + id));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updatePrison(@PathVariable Integer id,
                                      @RequestBody PrisonDTO prisonDTO) {
        try {
            ApiResponse updatePrison = prisonService.updatePrison(id, prisonDTO);
            return new ResponseEntity<>(updatePrison, HttpStatus.ACCEPTED);
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }

    }



    public Prison updatePrison2(@PathVariable Integer id,
                                @Valid @RequestBody Prison prison) {
        return prisonRepository.findById(id)
                .map(updatedPrison -> {
                    updatedPrison.setCountry(prison.getCountry());
                    updatedPrison.setCity(prison.getCity());
                    updatedPrison.setPrisonName(prison.getPrisonName());
                    return prisonRepository.save(updatedPrison);
        }).orElseThrow(() -> new ResourceNotFoundException("There is no prison with this id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deletePrison(@PathVariable Integer id) {
        ApiResponse deletePrison = prisonService.deletePrison(id);
        return new ResponseEntity<>(deletePrison, HttpStatus.OK);
    }

    @GetMapping("/search/{country}")
    public HttpEntity<?> searchByNamePrison(@PathVariable String country,
                                            @PageableDefault(sort = "country", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Prison> prisonPage = prisonService.findAllByCountryContaining(country.toLowerCase(), pageable);
        if (prisonPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(prisonPage, HttpStatus.OK);
    }

}