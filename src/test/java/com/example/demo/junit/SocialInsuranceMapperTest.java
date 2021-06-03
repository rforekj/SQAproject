package com.example.demo.junit;

import com.example.demo.model.SocialInsurance;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import com.example.demo.service.mapper.SocialInsuranceMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SocialInsuranceMapperTest {

    @Autowired
    SocialInsuranceMapper socialInsuranceMapper;

    @Test
    public void testStatusLatePaid1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-04-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.LATE_PAID);
    }

    @Test
    public void testStatusLatePaid2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("12-02-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.LATE_PAID);
    }

    @Test
    public void testStatusHaveNotPaid1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-06-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.HAVE_NOT_PAID);
    }

    @Test
    public void testStatusHaveNotPaid2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("12-06-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.HAVE_NOT_PAID);
    }

    @Test
    public void testStatusHavePaid1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("30-07-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.HAVE_PAID);
    }

    @Test
    public void testStatusHavePaid2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("01-07-2021"));
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.HAVE_PAID);
    }

    @Test
    public void testStatusReceived1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("29-05-2021"));
        socialInsurance.setPaid(true);
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.RECEIVED);
    }

    @Test
    public void testStatusReceived2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SocialInsurance socialInsurance = new SocialInsurance();
        socialInsurance.setExpiredDate(simpleDateFormat.parse("29-05-2021"));
        socialInsurance.setPaid(false);
        SocialInsuranceRespondDto socialInsuranceRespondDto = socialInsuranceMapper.socialInsuranceToSocialInsuranceRespondDto(socialInsurance);
        Assert.assertNotEquals(socialInsuranceRespondDto.getStatus(), SocialInsuranceRespondDto.Status.RECEIVED);
    }

}
