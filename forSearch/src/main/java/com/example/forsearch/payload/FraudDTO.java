package com.example.forsearch.payload;

import com.example.forsearch.entity.Prison;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FraudDTO {

    private String firstName;

    private String lastName;

    private String crimeCommit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp crimeTime;

    private Prison prison;
}