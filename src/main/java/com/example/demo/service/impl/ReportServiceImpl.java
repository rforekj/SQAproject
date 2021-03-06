package com.example.demo.service.impl;

import com.example.demo.model.SocialInsurance;
import com.example.demo.repository.SocialInsuranceRepository;
import com.example.demo.service.ReportService;
import com.example.demo.service.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    SocialInsuranceRepository socialInsuranceRepository;

    @Override
    public Map<String, Object> getAllReportByMonth() {
        Date theFirstDayOfThisMonth = DateUtil.getTheFirstDayOfThisMonth();
        Date theFirstDayOfNextMonth = DateUtil.getTheFirstDayOfNextMonth();
        Date theLastDayOfThisMonth = DateUtil.getTheLastDayOfThisMonth();
        Date theLastDayOfPreviousMonth = DateUtil.getTheLastDayOfPreviousMonth();
        Integer numberLatePaid = socialInsuranceRepository.countAllByExpiredDateBeforeAndPaidIsFalse(theFirstDayOfThisMonth);
        Integer numberHaveNotPaid = socialInsuranceRepository.countAllByExpiredDateIsBetweenAndPaidIsFalse(theFirstDayOfThisMonth, theFirstDayOfNextMonth);
        Integer numberHavePaid = socialInsuranceRepository.countAllByExpiredDateAfterAndPaidIsFalse(theFirstDayOfNextMonth);
        Double moneyHavePaid = socialInsuranceRepository.calculateMoneyHavePaid(theFirstDayOfNextMonth);
        Double moneyHaveNotPaid = socialInsuranceRepository.calculateMoneyHaveNotPaid(theFirstDayOfThisMonth, theFirstDayOfNextMonth);

        List<SocialInsurance> socialInsuranceList = socialInsuranceRepository.findAllByPaidIsTrueAndExpiredDateIsBetween(theFirstDayOfThisMonth, theLastDayOfThisMonth);
        Integer numberReceived = socialInsuranceList.size();
        double moneyReceived = 0;
        for (SocialInsurance socialInsurance : socialInsuranceList) {
            Date createdDate = Date.from(socialInsurance.getCreatedDate());
            Date receivedDate = socialInsurance.getExpiredDate();
            if (receivedDate.before(theFirstDayOfNextMonth) && receivedDate.after(theLastDayOfPreviousMonth)) {
                long diffMonth = TimeUnit.DAYS.convert(receivedDate.getTime() - createdDate.getTime(), TimeUnit.MILLISECONDS) / 30;
                moneyReceived += diffMonth * 2 * socialInsurance.getType().getPrice() * socialInsurance.getClient().getSalary() * 0.01;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("numberLatePaid", numberLatePaid);
        result.put("numberHaveNotPaid", numberHaveNotPaid);
        result.put("numberHavePaid", numberHavePaid);
        result.put("numberReceived", numberReceived);
        result.put("moneyHavePaid", moneyHavePaid);
        result.put("moneyHaveNotPaid", moneyHaveNotPaid);
        result.put("moneyReceived", moneyReceived);
        return result;
    }
}
