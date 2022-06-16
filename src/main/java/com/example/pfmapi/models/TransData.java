package com.example.pfmapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransData {
    @JsonProperty("Criteria")
    private String Criteria;

    @JsonProperty("Amount")
    private Double Amount;

    @JsonProperty("Percentage")
    private Double Percentage;
}
