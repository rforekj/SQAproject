package com.example.demo;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.service.ConfigService;
import com.example.demo.service.UserService;
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

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientControllerTest {
    @Autowired
    ConfigService configService;
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
    public void test1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")));
    }

    @Test
    public void test2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")));
    }

    @Test
    public void test3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")));
    }

    @Test
    public void test4() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Nam Định&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Nam Định")));
    }

    @Test
    public void test5() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=LATE_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void test6() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=LATE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void test7() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=LATE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void test8() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=HAVE_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void test9() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=HAVE_PAID&&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void test10() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=HAVE_PAID&&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void test11() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=HAVE_NOT_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void test12() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=HAVE_NOT_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void test13() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=HAVE_NOT_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void test14() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=RECEIVED&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void test15() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=RECEIVED&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void test16() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=RECEIVED&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }
}
