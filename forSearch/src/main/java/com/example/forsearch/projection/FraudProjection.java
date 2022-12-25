package com.example.forsearch.projection;

import com.example.forsearch.entity.Fraud;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.FraudDTO;

import java.util.List;

public interface FraudProjection {

    List<Fraud> getAll();

    Fraud getById(Integer id);

    ApiResponse addFraud(FraudDTO fraudDTO);

    ApiResponse updateFraud(Integer id, FraudDTO fraudDTO);

    ApiResponse deleteFraud(Integer id);
}
