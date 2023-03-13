package com.example.forgetdatafromapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DataDTO {
    private String expand;
    private String key;
    private String releaseDate;

    public DataDTO(String key) {
        this.key = key;
    }
}
