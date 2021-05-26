package com.example.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
public class UserRegistrationDto {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "password must not be blank")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "date of birth must not be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotBlank(message = "address must not be blank")
    private String address;

    @Email
    @NotBlank(message = "email must not be blank")
    @Size(min = 3, max = 100)
    private String email;


}
