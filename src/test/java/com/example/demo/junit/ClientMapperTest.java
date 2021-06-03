package com.example.demo.junit;

import com.example.demo.model.Client;
import com.example.demo.model.SocialInsurance;
import com.example.demo.model.SocialInsuranceType;
import com.example.demo.repository.SocialInsuranceTypeRepository;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.mapper.ClientMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientMapperTest {
    @Autowired
    ClientMapper clientMapper;

    @Test //1 received. 1 have paid
    public void testMoneyThisMonth1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(100);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(true);
        socialInsurance.setExpiredDate(new Date());
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(8));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(8));
    }

    @Test //1 received. 1 have not paid
    public void testMoneyThisMonth2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(200);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(true);
        socialInsurance.setExpiredDate(new Date());
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(16));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(0));
    }

    @Test //1 have paid. 1 have not paid
    public void testMoneyThisMonth3() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(100);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(false);
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(16));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(8));
    }

    @Test //2 have paid. 1 have not paid
    public void testMoneyThisMonth4() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(100);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(false);
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        SocialInsurance socialInsurance2 = new SocialInsurance();
        socialInsurance2.setPaid(false);
        socialInsurance2.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1, socialInsurance2));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(24));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(16));
    }

    @Test  //1 have paid. 2 have not paid
    public void testMoneyThisMonth5() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(100);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(false);
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        SocialInsurance socialInsurance2 = new SocialInsurance();
        socialInsurance2.setPaid(false);
        socialInsurance2.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1, socialInsurance2));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(24));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(8));
    }

    @Test
    public void testMoneyThisMonth6() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Client client = new Client();
        client.setSalary(100);
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setPaid(true);
        socialInsurance.setExpiredDate(new Date());
        SocialInsurance socialInsurance1 = new SocialInsurance();
        socialInsurance1.setPaid(false);
        socialInsurance1.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        client.setSocialInsuranceList(Arrays.asList(socialInsurance, socialInsurance1));
        ClientRespondDto clientRespondDto = clientMapper.clientToClientRespondDto(client);
        Assert.assertEquals(clientRespondDto.getMoneyNeedToPaidThisMonth(), new Double(8));
        Assert.assertEquals(clientRespondDto.getMoneyPaidThisMonth(), new Double(8));
    }
}
