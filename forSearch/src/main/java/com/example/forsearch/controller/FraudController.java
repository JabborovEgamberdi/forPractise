package com.example.forsearch.controller;

import com.example.forsearch.entity.Fraud;
import com.example.forsearch.exception.ResourceNotFoundException;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.FraudDTO;
import com.example.forsearch.repository.FraudRepository;
import com.example.forsearch.repository.PrisonRepository;
import com.example.forsearch.service.FraudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final FraudService fraudService;

    private final FraudRepository fraudRepository;
    private final PrisonRepository prisonRepository;

    public FraudController(FraudService fraudService, FraudRepository fraudRepository,
                           PrisonRepository prisonRepository) {
        this.fraudService = fraudService;
        this.fraudRepository = fraudRepository;
        this.prisonRepository = prisonRepository;
    }


    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Fraud> fraudList = fraudService.getAll();
        return new ResponseEntity<>(fraudList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Fraud getFraudById = fraudService.getById(id);
        return new ResponseEntity<>(getFraudById, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpEntity<?> addFraud(@RequestBody FraudDTO fraudDTO) {
        try {
            ApiResponse addFraud = fraudService.addFraud(fraudDTO);
            return new ResponseEntity<>(addFraud, HttpStatus.CREATED);
        } catch (HttpClientErrorException.UnsupportedMediaType e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false));
        } catch (NullPointerException e) {
            e.getMessage();
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false));
        }
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/updateFraud/{prisonId}/{fraudId}")
    public Fraud updateFraud2(@PathVariable Integer prisonId,
                              @PathVariable Integer fraudId,
                              @Valid @RequestBody Fraud fraud) {

        if (!prisonRepository.existsById(prisonId)) {
            throw new ResourceNotFoundException("Prison not found");
        }

        return fraudRepository.findById(fraudId)
                .map(updatedFraud -> {
                    updatedFraud.setFirstName(fraud.getFirstName());
                    updatedFraud.setLastName(fraud.getLastName());
                    updatedFraud.setCrimeTime(fraud.getCrimeTime());
                    updatedFraud.setCrimeCommit(fraud.getCrimeCommit());
                    return fraudRepository.save(updatedFraud);
                }).orElseThrow(() -> new ResourceNotFoundException("This fraud not found"));

    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/addFraud/{id}")
    public Fraud addFraud2(@PathVariable Integer id,
                           @Valid @RequestBody Fraud fraud) {
        return prisonRepository.findById(id)
                .map(savedFraud -> {
                    fraud.setPrison(savedFraud);
                    return fraudRepository.save(fraud);
                }).orElseThrow(() -> new ResourceNotFoundException("There is no prison with this id: " + id));
    }

    @CrossOrigin(origins = "addCorsMapping")
    @PutMapping("/update/{id}")
    public HttpEntity<?> updateFraud(@PathVariable Integer id,
                                     @RequestBody FraudDTO fraudDTO) {
        ApiResponse updateFraud = fraudService.updateFraud(id, fraudDTO);
        return new ResponseEntity<>(updateFraud, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteFraud(@PathVariable Integer id) {
        ApiResponse deleteFraud = fraudService.deleteFraud(id);
        return new ResponseEntity<>(deleteFraud, !deleteFraud.toString().isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

}