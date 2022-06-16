package com.example.pfmapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PeriodTransData {
    @JsonProperty("Month")
    private String Month;

    @JsonProperty("TransData")
    private List<TransData> TransData;
}
