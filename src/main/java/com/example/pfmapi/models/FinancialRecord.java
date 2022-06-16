package com.example.pfmapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FinancialRecord {
    @JsonProperty("Identifier")
    private String Identifier;

    @JsonProperty("Month")
    private String Month;

   // @JsonProperty("EndMonth")
   // private String EndMonth;

    @JsonProperty("CurrentData")
    private List<TransData> CurrentData;

    @JsonProperty("SpendByChannel")
    private List<PeriodTransData> SpendByChannel;

    @JsonProperty("TrendOfSpendReceipt")
    private List<PeriodTransData> TrendOfSpendReceipt;
}
