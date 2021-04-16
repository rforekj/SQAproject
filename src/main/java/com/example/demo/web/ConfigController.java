package com.example.demo.web;

import com.example.demo.model.Config;
import com.example.demo.model.SocialInsuranceType;
import com.example.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
@CrossOrigin(origins = "*")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/insurance-type")
    List<SocialInsuranceType> findAll(){
        return configService.getAllInsuranceType();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/insurance-type/{id}")
    public ResponseEntity<?> updateInsuranceType(@PathVariable int id, @RequestBody SocialInsuranceType type) {
        if (type.getPrice()==null) return ResponseEntity.status(400).body("price must not null");
        if(type.getPrice()<0||type.getPrice()>10) return ResponseEntity.status(400).body("price must greater than 0 and lower than 10");
        if(configService.updateInsuranceType(id,type.getPrice())==null){
            return ResponseEntity.status(400).body("not found id type");
        }
        return ResponseEntity.ok("succeed");
    }

}
