package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SocialInsuranceType {
    @Id
    private int id;

    private String name;

    private Double price;
}
