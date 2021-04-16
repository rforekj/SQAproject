package com.example.demo.service;

import com.example.demo.model.SocialInsuranceType;

import java.util.List;

public interface ConfigService {
    SocialInsuranceType updateInsuranceType(int id, double price);
    List<SocialInsuranceType> getAllInsuranceType();
}
