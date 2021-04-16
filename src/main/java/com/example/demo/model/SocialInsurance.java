package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class SocialInsurance extends AbstractEntity {
    @Id
    private Long id;

    private Date expiredDate;

    private boolean paid;

    @ManyToOne
    private SocialInsuranceType type;

    @ManyToOne
    @JsonBackReference("client_insurance")
    private Client client;

}
