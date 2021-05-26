package com.example.demo.web;

import com.example.demo.config.Constants;
import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import com.example.demo.service.ClientService;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping("list-client")
    public Map<String, Object> findAllByAddress(@RequestParam(required = false) String province,
                                                @RequestParam(required = false) String district,
                                                @RequestParam(required = false) String commune,
                                                @RequestParam(required = false) String hamlet,
                                                @RequestParam(required = false) SocialInsuranceRespondDto.Status status,
                                                @RequestParam int page, @RequestParam int size) {
        return clientService.findClientByAddress(province, district, commune, hamlet, status, page, size);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ClientRespondDto findClient(@RequestParam String keyword) {
        return clientService.findClient(keyword);
    }


}
