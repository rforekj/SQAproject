package com.example.demo.service.impl;

import com.example.demo.model.SocialInsuranceType;
import com.example.demo.repository.SocialInsuranceTypeRepository;
import com.example.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    SocialInsuranceTypeRepository socialInsuranceTypeRepository;

    @Override
    public SocialInsuranceType updateInsuranceType(int id, double price) {
        Optional<SocialInsuranceType> socialInsuranceType = socialInsuranceTypeRepository.findById(id);
        if(!socialInsuranceType.isPresent()) return null;
        socialInsuranceType.get().setPrice(price);
        return socialInsuranceTypeRepository.save(socialInsuranceType.get());
    }

    @Override
    public List<SocialInsuranceType> getAllInsuranceType() {
        return socialInsuranceTypeRepository.findAll();
    }

}
