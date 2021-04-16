package com.example.demo.service.dto;

import com.example.demo.model.Role;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class UserRespondDto {
    private Long id;

    private String name;

    private Date dob;

    private String email;

    private String address;

    private Collection<Role> roles;

}
