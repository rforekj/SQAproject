package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Address {
    @Id
    private int id;

    private String province;

    private String district;

    private String commune;

    private String hamlet;
}
