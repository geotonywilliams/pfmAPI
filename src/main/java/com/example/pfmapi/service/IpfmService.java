package com.example.pfmapi.service;

import com.example.pfmapi.models.FinancialRecord;

public interface IpfmService {
    FinancialRecord GetRecord(String id, String month) throws Exception;
}
