package com.example.forsearch.service;

import com.example.forsearch.entity.Fraud;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.FraudDTO;
import com.example.forsearch.projection.FraudProjection;
import com.example.forsearch.repository.FraudRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class FraudService implements FraudProjection {

    private final FraudRepository fraudRepository;

    public FraudService(FraudRepository fraudRepository) {
        this.fraudRepository = fraudRepository;
    }
//findAll() --> select * from fraud

    @Override
    public List<Fraud> getAll() {
        return fraudRepository.findAll();
    }

    @Override
    public Fraud getById(Integer id) {
        return fraudRepository.findById(id).orElse(null);
    }

    @Override
    public ApiResponse addFraud(@Valid FraudDTO fraudDTO) {
        Fraud fraud = new Fraud();
        fraud.setFirstName(fraudDTO.getFirstName());
        fraud.setLastName(fraudDTO.getLastName());
        fraud.setCrimeCommit(fraudDTO.getCrimeCommit());
        fraud.setCrimeTime(fraudDTO.getCrimeTime());
        fraudRepository.saveAndFlush(fraud);
        return new ApiResponse("Fraud added successfully. Id: " + fraud.getId(), true);
    }

    @Override
    public ApiResponse updateFraud(Integer id, FraudDTO fraudDTO) {
        Optional<Fraud> optionalFraud = fraudRepository.findById(id);
        if (optionalFraud.isPresent()) {
            Fraud fraud = optionalFraud.get();
            fraud.setFirstName(fraudDTO.getFirstName());
            fraud.setLastName(fraudDTO.getLastName());
            fraud.setCrimeCommit(fraudDTO.getCrimeCommit());
            fraud.setCrimeTime(fraudDTO.getCrimeTime());
            fraudRepository.saveAndFlush(fraud);
            return new ApiResponse("Fraud's information updated successfully", true);
        }
        return new ApiResponse("There is no fraud with this id", false);
    }

    @Override
    public ApiResponse deleteFraud(Integer id) {
        Optional<Fraud> optionalFraud = fraudRepository.findById(id);
        if (optionalFraud.isPresent()) {
            fraudRepository.deleteById(id);
            return new ApiResponse("Fraud is deleted successfully", true);
        }
        return new ApiResponse("There is no fraud with this id" + optionalFraud.get().getId(), false);
    }


}