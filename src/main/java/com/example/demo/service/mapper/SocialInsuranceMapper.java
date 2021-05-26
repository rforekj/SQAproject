package com.example.demo.service.mapper;

import com.example.demo.model.SocialInsurance;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import com.example.demo.service.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialInsuranceMapper {
    public SocialInsuranceRespondDto socialInsuranceToSocialInsuranceRespondDto(SocialInsurance socialInsurance) {
        SocialInsuranceRespondDto socialInsuranceRespondDto = new SocialInsuranceRespondDto();
        socialInsuranceRespondDto.setId(socialInsurance.getId());
        socialInsuranceRespondDto.setCreatedDate(Date.from(socialInsurance.getCreatedDate()));
        socialInsuranceRespondDto.setExpiredDate(socialInsurance.getExpiredDate());
        socialInsuranceRespondDto.setType(socialInsurance.getType());
        //LocalDate expiredDate = socialInsurance.getExpiredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date theLastDayOfPreviousMonth = DateUtil.getTheLastDayOfPreviousMonth();
        Date theFirstDayOfNextMonth = DateUtil.getTheFirstDayOfNextMonth();
        if (socialInsurance.getExpiredDate().before(theLastDayOfPreviousMonth))
            socialInsuranceRespondDto.setStatus(SocialInsuranceRespondDto.Status.LATE_PAID);
        else if (socialInsurance.getExpiredDate().before(theFirstDayOfNextMonth))
            socialInsuranceRespondDto.setStatus(SocialInsuranceRespondDto.Status.HAVE_NOT_PAID);
        else
            socialInsuranceRespondDto.setStatus(SocialInsuranceRespondDto.Status.HAVE_PAID);
        if (socialInsurance.isPaid())
            socialInsuranceRespondDto.setStatus(SocialInsuranceRespondDto.Status.RECEIVED);
        return socialInsuranceRespondDto;
    }

    public List<SocialInsuranceRespondDto> socialInsurancesToSocialInsuranceRespondDtos(List<SocialInsurance> socialInsuranceList) {
        return socialInsuranceList.stream().map(this::socialInsuranceToSocialInsuranceRespondDto).collect(Collectors.toList());
    }

}
