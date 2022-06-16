package com.example.pfmapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataRequest {
    @JsonProperty("Identifier")
    private String Identifier;

    @JsonProperty("StartMonth")
    private String StartMonth;

    @JsonProperty("EndMonth")
    private String EndMonth;
}
