package com.example.pfmapi.controllers;

import com.example.pfmapi.models.FinancialRecord;
import com.example.pfmapi.service.IpfmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class pfmController {

    private final IpfmService service;

    public pfmController(IpfmService service) {this.service = service;}

    @GetMapping("/{id}/{month}")
    public ResponseEntity<FinancialRecord> GetFinancialRecord(@PathVariable(name = "id") String id, @PathVariable(name = "month") String month) throws Exception {
        return ResponseEntity.ok(service.GetRecord(id, month));
    }
}
