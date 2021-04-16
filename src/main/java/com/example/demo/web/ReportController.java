package com.example.demo.web;

import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/report")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping
    public Map<String, Object> getReportThisMonth(){
        //if(month<0||month>12) return ResponseEntity.status(400).body("0 < month < 13");
        //if(year<2000|year>3000) return ResponseEntity.status(400).body("2000 < year < 3000");
        return reportService.getAllReportByMonth();
    }
}
