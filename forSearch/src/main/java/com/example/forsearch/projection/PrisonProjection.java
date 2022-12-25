package com.example.forsearch.projection;

import com.example.forsearch.entity.Prison;
import com.example.forsearch.payload.ApiResponse;
import com.example.forsearch.payload.PrisonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrisonProjection {

    List<Prison> getAll();

    Prison getById(Integer id);

    ApiResponse addPrison(PrisonDTO prisonDTO);

    ApiResponse updatePrison(Integer id, PrisonDTO prisonDTO);

    ApiResponse deletePrison(Integer id);

    Page<Prison> findAllByCountryContaining(String country, Pageable pageable);

}
