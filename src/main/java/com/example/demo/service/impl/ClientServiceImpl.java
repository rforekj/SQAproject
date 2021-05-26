package com.example.demo.service.impl;

import com.example.demo.config.Constants;
import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SocialInsuranceRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import com.example.demo.service.mapper.ClientMapper;
import com.example.demo.service.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientMapper clientMapper;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SocialInsuranceRepository socialInsuranceRepository;

    @Override
    public Map<String, Object> findClientByAddress(String province, String district, String commune, String hamlet, SocialInsuranceRespondDto.Status status, int page, int size) {
        if (page < 1 || size < 1) return null;
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Client> pageTuts = null;
        if (status == null) {
            if (province == null)
                pageTuts = clientRepository.findAll(paging);
            else if (district == null)
                pageTuts = clientRepository.findAllByAddress_Province(province, paging);
            else if (commune == null)
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_District(province, district, paging);
            else if (hamlet == null)
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndAddress_Commune(province, district, commune, paging);
            else
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndAddress_Hamlet(province, district, commune, hamlet, paging);
        } else {
            List<SocialInsurance> socialInsuranceList;
            if (status == SocialInsuranceRespondDto.Status.LATE_PAID)
                socialInsuranceList = socialInsuranceRepository.findAllByExpiredDateIsBeforeAndPaidIsFalse(DateUtil.getTheFirstDayOfThisMonth());
            else if (status == SocialInsuranceRespondDto.Status.HAVE_NOT_PAID)
                socialInsuranceList = socialInsuranceRepository.findAllByExpiredDateIsBetweenAndPaidIsFalse(DateUtil.getTheFirstDayOfThisMonth(), DateUtil.getTheFirstDayOfNextMonth());
            else if (status == SocialInsuranceRespondDto.Status.HAVE_PAID)
                socialInsuranceList = socialInsuranceRepository.findAllByExpiredDateIsAfterAndPaidIsFalse(DateUtil.getTheFirstDayOfNextMonth());
            else
                socialInsuranceList = socialInsuranceRepository.findAllByPaidIsTrue();
            if (province == null)
                pageTuts = clientRepository.findAllBySocialInsuranceListIsIn(socialInsuranceList, paging);
            else if (district == null)
                pageTuts = clientRepository.findAllByAddress_ProvinceAndSocialInsuranceListIsIn(province, socialInsuranceList, paging);
            else if (commune == null)
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndSocialInsuranceListIsIn(province, district, socialInsuranceList, paging);
            else if (hamlet == null)
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndSocialInsuranceListIsIn(province, district, commune, socialInsuranceList, paging);
            else
                pageTuts = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndAddress_CommuneAndAddress_HamletAndSocialInsuranceListIsIn(province, district, commune, hamlet, socialInsuranceList, paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", pageTuts.getNumber() + 1);
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        response.put("listClient", clientMapper.clientsToClientRespondDtos(pageTuts.getContent()));
        return response;
    }

    @Override
    public ClientRespondDto findClient(String keyword) {
        Client client = clientRepository.findByIdentityNumber(keyword);
        if (client != null)
            return clientMapper.clientToClientRespondDto(client);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setId(Long.valueOf(keyword));
        client = clientRepository.findBySocialInsuranceListIsContaining(socialInsurance);
        if (client != null)
            return clientMapper.clientToClientRespondDto(client);
        return null;
    }

}
