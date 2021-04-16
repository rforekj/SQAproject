package com.example.demo.service.dto;

import com.example.demo.model.SocialInsuranceType;
import lombok.Data;

import java.util.Date;

@Data
public class SocialInsuranceRespondDto {
    private Long id;

    private Date expiredDate;

    private SocialInsuranceType type;

    private Date createdDate;

    private Status status;
    
    public enum Status{
        HAVE_PAID,
        HAVE_NOT_PAID,
        LATE_PAID,
        RECEIVED
    }
}
