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

    @Override
    public String toString() {
        if (hamlet == null) hamlet = "";
        return province + "-" + district + "-" + commune + "-" + hamlet;
    }
}
