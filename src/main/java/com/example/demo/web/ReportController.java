package com.example.demo.web;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.ReportService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import com.example.demo.service.util.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    ClientService clientService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public Map<String, Object> getReportThisMonth() {
        return reportService.getAllReportByMonth();
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping("/export/report.xlsx")
    public ResponseEntity exportExcel(@RequestParam(required = false) String province,
                                      @RequestParam(required = false) String district,
                                      @RequestParam(required = false) String commune,
                                      @RequestParam(required = false) String hamlet,
                                      @RequestParam(required = false) SocialInsuranceRespondDto.Status status) throws IOException {
        Map<String, Object> result = clientService.findClientByAddress(province, district, commune, hamlet, status,1,10000);
        List<ClientRespondDto> clients = (List<ClientRespondDto>) result.get("listClient");

        ByteArrayInputStream in = ExcelExporter.clientsToExcel(clients, province, district, commune, hamlet, status);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

}
