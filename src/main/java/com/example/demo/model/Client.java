package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Client extends AbstractEntity {
    @Id
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String identityNumber;

    private Gender gender;

    private double salary;

    private double paidMoney;

    private double receivedMoney;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    @JsonManagedReference("client_insurance")
    private List<SocialInsurance> socialInsuranceList;

    public enum Gender {
        MALE,
        FEMALE
    }
}
