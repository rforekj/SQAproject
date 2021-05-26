package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Page<Client> findAll(Pageable pageable);

    Page<Client> findAllByAddress_Province(String province, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_District(String province, String district, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_DistrictAndAddress_Commune(String province, String district, String commune, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndAddress_Hamlet(String province, String district, String commune, String hamlet, Pageable pageable);

    Client findByIdentityNumber(String identityNumber);

    Client findBySocialInsuranceListIsContaining(SocialInsurance socialInsurance);

    Page<Client> findAllBySocialInsuranceListIsIn(List<SocialInsurance> list, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndSocialInsuranceListIsIn(String province, List<SocialInsurance> list, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_DistrictAndSocialInsuranceListIsIn(String province, String district, List<SocialInsurance> list, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndSocialInsuranceListIsIn(String province, String district, String commune, List<SocialInsurance> list, Pageable pageable);

    Page<Client> findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndAddress_HamletAndSocialInsuranceListIsIn(String province, String district, String commune, String hamlet, List<SocialInsurance> list, Pageable pageable);

}
