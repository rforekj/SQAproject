package com.example.demo.service.dto;

import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ClientRespondDto {
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String identityNumber;

    private Client.Gender gender;

    private double salary;

    private Address address;

    private List<SocialInsuranceRespondDto> socialInsuranceList;
}
