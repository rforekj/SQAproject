package com.example.demo.service;

import java.util.List;

public interface AddressService {
    List<String> getListAddress(String province, String district, String commune);
}
