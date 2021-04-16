package com.example.demo.service;

import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;

import java.util.Map;

public interface ClientService {
    Map<String, Object> findClientByAddress(String province, String district, String commune, String hamlet, SocialInsuranceRespondDto.Status status, int page, int size);

    ClientRespondDto findClient(String keyword);

}
