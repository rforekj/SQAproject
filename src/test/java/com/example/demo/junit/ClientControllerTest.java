package com.example.demo.junit;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.service.ConfigService;
import com.example.demo.service.UserService;
import org.hamcrest.core.IsNull;
import org.hamcrest.text.IsEmptyString;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void getAllClientByProvince() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")));
    }

    @Test
    public void getAllClientByDistrict() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")));
    }

    @Test
    public void getAllClientByCommune() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")));
    }

    @Test
    public void getAllClientByProvince2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Nam Định&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Nam Định")));
    }

    @Test
    public void getAllClientByProvinceAndLatePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=LATE_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void getAllClientByDistrictAndLatePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=LATE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void getAllClientByCommuneAndLatePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=LATE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void getAllClientByProvinceAndHavePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=HAVE_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void getAllClientByDistrictAndHavePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=HAVE_PAID&&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void getAllClientByCommuneAndHavePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=HAVE_PAID&&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void getAllClientByProvinceAndHaveNotPaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=HAVE_NOT_PAID&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void getAllClientByDistrictAndHaveNotPaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hà Đông&status=HAVE_NOT_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hà Đông")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void getAllClientByCommuneAndHaveNotPaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hà Đông&commune=Phúc La&status=HAVE_NOT_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hà Đông")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Phúc La")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void getAllClientByProvinceAndHaveReceived() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&status=RECEIVED&page=1&size=6")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void getAllClientByDistrictAndReceived() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&status=RECEIVED&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void getAllClientReceived() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?status=RECEIVED&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void getAllClientHavePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?status=HAVE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_PAID")));
    }

    @Test
    public void getAllClientHaveNotPaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?status=HAVE_NOT_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("HAVE_NOT_PAID")));
    }

    @Test
    public void getAllClientLatePaid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?status=LATE_PAID&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("LATE_PAID")));
    }

    @Test
    public void getAllClientByCommuneAndReceived() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client/list-client?province=Hà Nội&district=Hai Bà Trưng&commune=Thanh Lương&status=RECEIVED&page=1&size=60")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.listClient[*].address.province").value(hasItem("Hà Nội")))
                .andExpect(jsonPath("$.listClient[*].address.district").value(hasItem("Hai Bà Trưng")))
                .andExpect(jsonPath("$.listClient[*].address.commune").value(hasItem("Thanh Lương")))
                .andExpect(jsonPath("listClient[*].socialInsuranceList[*].status").value(hasItem("RECEIVED")));
    }

    @Test
    public void findClientByIdentityNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client?keyword=235034549900")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.identityNumber").value("235034549900"));
    }

    @Test
    public void findClientByWrongIdentityNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client?keyword=23503454990000")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().string(IsEmptyString.isEmptyOrNullString()));
    }

    @Test
    public void findClientByInsuranceNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client?keyword=25")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.socialInsuranceList[*].id").value(25));
    }

    @Test
    public void findClientByWrongInsuranceNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/client?keyword=76")
                .header("Authorization", "Bearer " + adminToken).accept(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().string(IsEmptyString.isEmptyOrNullString()));
    }


}
