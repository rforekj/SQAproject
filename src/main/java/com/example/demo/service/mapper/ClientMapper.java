package com.example.demo.service.mapper;

import com.example.demo.model.Client;
import com.example.demo.service.dto.ClientRespondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientMapper {
    @Autowired SocialInsuranceMapper socialInsuranceMapper;

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
        return clientDto;
    }

    public List<ClientRespondDto> clientsToClientRespondDtos(List<Client> clients) {
        return clients.stream().map(this::clientToClientRespondDto).collect(Collectors.toList());
    }
}
