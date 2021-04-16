package com.example.demo.repository;

import com.example.demo.model.SocialInsuranceType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SocialInsuranceTypeRepository extends CrudRepository<SocialInsuranceType, Integer> {
    List<SocialInsuranceType> findAll();
}
