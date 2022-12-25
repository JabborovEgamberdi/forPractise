package com.example.forsearch.service;

import com.example.forsearch.entity.Fraud;
import com.example.forsearch.entity.Prison;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.PrisonDTO;
import com.example.forsearch.projection.PrisonProjection;
import com.example.forsearch.repository.FraudRepository;
import com.example.forsearch.repository.PrisonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class PrisonService implements PrisonProjection {

    private final FraudRepository fraudRepository;
    private final PrisonRepository prisonRepository;

    public PrisonService(FraudRepository fraudRepository, PrisonRepository prisonRepository) {
        this.fraudRepository = fraudRepository;
        this.prisonRepository = prisonRepository;
    }

    @Override
    public List<Prison> getAll() {
        return prisonRepository.findAll();
    }

    @Override
    public Prison getById(Integer id) {
        return prisonRepository.findById(id).get();

    }

    @Override
    public ApiResponse addPrison(@Valid PrisonDTO prisonDTO) {
        Prison prison = new Prison();
        List<Fraud> byId = (List<Fraud>) fraudRepository.getById(prisonDTO.getFraudId().get(0).getId());
        if (byId != null) {
            prison.setCountry(prisonDTO.getCountry());
            prison.setCity(prisonDTO.getCity());
            prison.setPrisonName(prisonDTO.getPrisonName());
            prison.setFraud(byId);
            prisonRepository.saveAndFlush(prison);
            return new ApiResponse("New prison added successfully", true);
        }
        return new ApiResponse("There is no fraud with this id. " +
                "That is why, you can not added this prison", false);
    }

//    @Override
//    public ApiResponse addPrison(@Valid PrisonDTO prisonDTO) {
//        Prison prison = new Prison();
//        List<Fraud> byId = (List<Fraud>) fraudRepository.getById(prisonDTO.getFraudId().get(0).getId());
//        if (byId != null) {
//            prison.setCountry(prisonDTO.getCountry());
//            prison.setCity(prisonDTO.getCity());
//            prison.setPrisonName(prisonDTO.getPrisonName());
//            prison.setFraud(byId);
//            prisonRepository.saveAndFlush(prison);
//            return new ApiResponse("New prison added successfully", true);
//        }
//        return new ApiResponse("There is no fraud with this id. " +
//                "That is why, you can not added this prison", false);
//    }
//    public ApiResponse addPrisonWithId(Integer id, @Valid PrisonDTO prisonDTO) {
//        Prison byId = prisonRepository.getById(id);
//
//    }

    @Override
    public ApiResponse updatePrison(Integer id, @Valid PrisonDTO prisonDTO) {
//        List<Fraud> byId = (List<Fraud>) fraudRepository.getById(prisonDTO.getFraudId().get(0).getId());
        List<Fraud> byId = (List<Fraud>) fraudRepository.getById(prisonDTO.getFraudId().get(0).getId());
        if (byId != null) {
            Optional<Prison> optionalPrison = prisonRepository.findById(id);
            if (optionalPrison.isPresent()) {
                Prison prison = optionalPrison.get();
                prison.setCountry(prisonDTO.getCountry());
                prison.setCity(prisonDTO.getCity());
                prison.setPrisonName(prisonDTO.getPrisonName());
                prison.setFraud(byId);
                prisonRepository.saveAndFlush(prison);
            }
            return new ApiResponse("Prison is successfully updated", true);
        }
        return new ApiResponse("There is no fraud with this id." +
                "So, you can not added this prison", false);
    }

    @Override
    public ApiResponse deletePrison(Integer id) {
        Optional<Prison> optionalPrison = prisonRepository.findById(id);
        if (optionalPrison.isPresent()) {
            prisonRepository.deleteById(id);
            return new ApiResponse("Prison deleted successfully", true);
        }
        return new ApiResponse("There is no prison with this id", false);
    }

    @Override
    public Page<Prison> findAllByCountryContaining(String country, Pageable pageable) {
        return prisonRepository.findAllByCountryContaining(country, pageable);
    }

}