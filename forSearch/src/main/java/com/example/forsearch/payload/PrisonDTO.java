package com.example.forsearch.payload;

import com.example.forsearch.entity.Fraud;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrisonDTO {

    private String country;

    private String city;

    private String prisonName;

    private List<Fraud> fraudId;

}
