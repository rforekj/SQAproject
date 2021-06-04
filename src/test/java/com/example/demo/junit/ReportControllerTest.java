package com.example.demo.junit;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.SocialInsurance;
import com.example.demo.repository.SocialInsuranceRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReportControllerTest {
    String adminToken;
    String employeeToken;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private SocialInsuranceRepository socialInsuranceRepository;

    @PostConstruct
    public void init() {
        String emailEmployee = "hieu8@gmail.com";
        String passwordEmployee = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailEmployee, passwordEmployee));
        final UserDetails employee = userDetailsService.loadUserByUsername(emailEmployee);
        employeeToken = jwtTokenUtil.generateToken(employee);

        String emailAdmin = "hieu@gmail.com";
        String passwordAdmin = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailAdmin, passwordAdmin));
        final UserDetails admin = userDetailsService.loadUserByUsername(emailAdmin);
        adminToken = jwtTokenUtil.generateToken(admin);
    }

    @Test
    public void testReportController() throws Exception {
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
        mvc.perform(MockMvcRequestBuilders.get("/report")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(jsonPath("$.moneyHaveNotPaid").value(moneyHaveNotPaid))
                .andExpect(jsonPath("$.numberLatePaid").value(numberLatePaid))
                .andExpect(jsonPath("$.numberHaveNotPaid").value(numberHaveNotPaid))
                .andExpect(jsonPath("$.moneyReceived").value(moneyReceived))
                .andExpect(jsonPath("$.moneyHavePaid").value(moneyHavePaid))
                .andExpect(jsonPath("$.numberHavePaid").value(numberHavePaid))
                .andExpect(jsonPath("$.numberReceived").value(numberReceived));
    }
}
