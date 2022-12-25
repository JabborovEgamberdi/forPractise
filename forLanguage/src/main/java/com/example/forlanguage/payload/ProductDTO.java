package com.example.forlanguage.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @JsonProperty("productNameUz")
    private String productNameUz;

    @JsonProperty("productNameRu")
    private String productNameRu;

    @JsonProperty("productNameEng")
    private String productNameEng;

    @JsonProperty("productDefinitionUz")
    private String productDefinitionUz;

    @JsonProperty("productDefinitionRu")
    private String productDefinitionRu;

    @JsonProperty("productDefinitionEng")
    private String productDefinitionEng;


}