package com.example.demo.repository;

import com.example.demo.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Query("select distinct a.province from Address a")
    List<String> getAllProvince();

    @Query("select distinct a.district from Address a where a.province = :province")
    List<String> getDistrictByProvince(@Param("province") String province);

    @Query("select distinct a.commune from Address a where a.province = :province and a.district = :district")
    List<String> getCommuneByProvinceAndDistrict(@Param("province") String province, @Param("district") String district);

    @Query("select distinct a.hamlet from Address a where a.province = :province and a.district = :district and a.commune = :commune")
    List<String> getHamletByProvinceAndDistrictAndCommune(@Param("province") String province, @Param("district") String district, @Param("commune") String commune);
}
