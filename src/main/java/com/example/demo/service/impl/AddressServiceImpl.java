package com.example.demo.service.impl;

import com.example.demo.repository.AddressRepository;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<String> getListAddress(String province, String district, String commune) {
        if (province == null) {
            return addressRepository.getAllProvince();
        } else if (district == null) {
            return addressRepository.getDistrictByProvince(province);
        } else if (commune == null) {
            return addressRepository.getCommuneByProvinceAndDistrict(province, district);
        } else {
            return addressRepository.getHamletByProvinceAndDistrictAndCommune(province, district, commune);
        }
    }
}
