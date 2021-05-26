package com.example.demo.service.mapper;

import com.example.demo.config.Constants;
import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import com.example.demo.service.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ClientMapper {
    @Autowired
    SocialInsuranceMapper socialInsuranceMapper;
    @Autowired
    ClientRepository clientRepository;

    public ClientRespondDto clientToClientRespondDto(Client client) {
        ClientRespondDto clientDto = new ClientRespondDto();
        clientDto.setAddress(client.getAddress());
        clientDto.setDob(client.getDob());
        clientDto.setGender(client.getGender());
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setSalary(client.getSalary());
        clientDto.setIdentityNumber(client.getIdentityNumber());
        clientDto.setSocialInsuranceList(socialInsuranceMapper.socialInsurancesToSocialInsuranceRespondDtos(client.getSocialInsuranceList()));
        clientDto.setPaidMoney(client.getPaidMoney());
        clientDto.setReceivedMoney(client.getReceivedMoney());
        double moneyNeedToPaidThisMonth = 0;
        double moneyPaidThisMonth = 0;
        for (SocialInsuranceRespondDto socialInsurance : clientDto.getSocialInsuranceList()) {
            if(!socialInsurance.getStatus().equals(SocialInsuranceRespondDto.Status.RECEIVED)) {
                moneyNeedToPaidThisMonth += Constants.PERCENT_PAID_AMOUNT * client.getSalary();
                if (socialInsurance.getStatus().equals(SocialInsuranceRespondDto.Status.HAVE_PAID))
                    moneyPaidThisMonth += Constants.PERCENT_PAID_AMOUNT * client.getSalary();
            }
        }
        clientDto.setMoneyNeedToPaidThisMonth(moneyNeedToPaidThisMonth);
        clientDto.setMoneyPaidThisMonth(moneyPaidThisMonth);
        return clientDto;
    }

    public List<ClientRespondDto> clientsToClientRespondDtos(List<Client> clients) {
        return clients.stream().map(this::clientToClientRespondDto).collect(Collectors.toList());
    }
}
