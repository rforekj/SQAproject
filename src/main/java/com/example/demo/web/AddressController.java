package com.example.demo.web;

import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "*")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<String> getListAddress(@RequestParam(required = false) String province,
                                       @RequestParam(required = false) String district,
                                       @RequestParam(required = false) String commune) {
        return addressService.getListAddress(province, district, commune);
    }
}
