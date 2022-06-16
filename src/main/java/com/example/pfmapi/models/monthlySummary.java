package com.example.pfmapi.models;

import lombok.Data;

@Data
public class monthlySummary {
    private String ID;
    private String ORGKEY;
    private String YEAR_MONTH;
    private String SEGMENT;
    private String NO_OF_ACCOUNTS;
    private String CUST_FIRST_NAME;
    private String CUST_LAST_NAME;
    private String PREFERREDPHONE;
    private String EMAIL;
    private Double ACCOUNT2WALLET;
    private Double LOAN;
    private Double ATM;
    private Double POS;
    private Double BRANCH;
    private Double DIGITAL;
    private Double TOTALDEBITS;
    private Double TOTALCREDITS;
    private Double OTHERS;
    private Double ECOMMERCE;
    private Double HEALTH;
    private Double MISC;
    private Double RESTAURANTS;
    private Double RETAIL;
    private Double TRAVEL;
}
