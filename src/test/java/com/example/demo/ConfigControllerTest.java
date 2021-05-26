package com.example.demo;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.SocialInsuranceType;
import com.example.demo.repository.SocialInsuranceTypeRepository;
import com.example.demo.service.ConfigService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ConfigControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    ConfigService configService;

    @Autowired
    SocialInsuranceTypeRepository socialInsuranceTypeRepository;

    String adminToken;
    String employeeToken;

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
    public void testInputPriceNotCorrect1() throws Exception {
        //Test input price not correct format
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(-0.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("price must greater than 0 and lower than 10"));
    }

    @Test
    public void testInputPriceNotCorrect2() throws Exception {
        //Test input price not correct format
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(10.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("price must greater than 0 and lower than 10"));
    }

    @Test
    public void testInputPriceNotCorrect3() throws Exception {
        //Test input price not correct format
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(100.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("price must greater than 0 and lower than 10"));
    }

    @Test
    public void testInputPriceNotCorrect4() throws Exception {
        //Test input price not correct format
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(-100.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("price must greater than 0 and lower than 10"));
    }

    @Test
    public void testConfigSucceed1() throws Exception {
        //Test config succeed
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(0.9);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200))
                .andExpect(content().string("succeed"));
        //Test database
        Optional<SocialInsuranceType> socialInsuranceType1 = socialInsuranceTypeRepository.findById(2);
        assertNotNull(socialInsuranceType1);
        assertEquals(new Double(socialInsuranceType1.get().getPrice().doubleValue()), new Double(0.9));
    }

    @Test
    public void testConfigSucceed2() throws Exception {
        //Test config succeed
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(0.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200))
                .andExpect(content().string("succeed"));
        //Test database
        Optional<SocialInsuranceType> socialInsuranceType1 = socialInsuranceTypeRepository.findById(2);
        assertNotNull(socialInsuranceType1);
        assertEquals(new Double(socialInsuranceType1.get().getPrice().doubleValue()), new Double(0.1));

    }

    @Test
    public void testConfigSucceed3() throws Exception {
        //Test config succeed
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(9.9);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200))
                .andExpect(content().string("succeed"));
        //Test database
        Optional<SocialInsuranceType> socialInsuranceType1 = socialInsuranceTypeRepository.findById(2);
        assertNotNull(socialInsuranceType1);
        assertEquals(new Double(socialInsuranceType1.get().getPrice().doubleValue()), new Double(9.9));

    }

    @Test
    public void testConfigTypeNotInDatabase() throws Exception {
        //Test config type not in database
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(0.9);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/20")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("not found id type"));


    }

    @Test
    public void testRoleEmployeeConfig() throws Exception {
        SocialInsuranceType socialInsuranceType = new SocialInsuranceType();
        socialInsuranceType.setName("Bảo hiểm tai nạn lao động");
        socialInsuranceType.setPrice(-0.1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(socialInsuranceType);
        mvc.perform(MockMvcRequestBuilders.put("/config/insurance-type/2")
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(403));
    }


}
