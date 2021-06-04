package com.example.demo.junit;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticateControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCorrectEmailPassword() throws Exception {
        String email = "hieu8@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        ;
        assertNotNull(token);
    }

    @Test
    public void testWrongEmailPassword() throws Exception {
        String email = "hieu82@gmail.com";
        String password = "123456";
        assertThrows(BadCredentialsException.class, () -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)));
    }

    @Test
    public void testRoleEmployeeNotAllowToRegisterNewUser() throws Exception {
        String email = "hieu8@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        ;
        assertNotNull(token);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setName("hieu");
        userRegistrationDto.setEmail("hieu9@gmail.com");
        userRegistrationDto.setPassword("123456");
        userRegistrationDto.setDob(new Date());
        userRegistrationDto.setAddress("Ha Noi");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRoleAdminAllowToRegisterNewUser() throws Exception {
        String email = "hieu@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        assertNotNull(token);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setName("hieu");
        userRegistrationDto.setEmail("hieu100@gmail.com");
        userRegistrationDto.setPassword("123456");
        userRegistrationDto.setDob(new Date());
        userRegistrationDto.setAddress("Ha Noi");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        //Test database if there is user registered
//        Optional<User> user = userRepository.findByEmail("hieu100@gmail.com");
//        assertNotNull(user.get());
//        assertEquals(user.get().getName(),"hieu");
    }

    @Test
    public void testRegisterNewUserWithWrongInput() throws Exception {
        String email = "hieu@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        assertNotNull(token);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setName("hieu");
        userRegistrationDto.setEmail("hieu@gmail.com");
        userRegistrationDto.setPassword("123456");
        userRegistrationDto.setDob(new Date());
        userRegistrationDto.setAddress("Ha Noi");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userRegistrationDto);

        //Test register duplicate email
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(content().string("There is already an account registered with that email"));

        //Test register will name null
        userRegistrationDto.setName(null);
        json = mapper.writeValueAsString(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$[0]").value("name must not be blank"));

        //Test register will password null
        userRegistrationDto.setAddress("Ha Noi");
        userRegistrationDto.setName("hieu");
        userRegistrationDto.setPassword(null);
        json = mapper.writeValueAsString(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$[0]").value("password must not be blank"));

        //Test register will email null
        userRegistrationDto.setAddress("Ha Noi");
        userRegistrationDto.setPassword("123456");
        userRegistrationDto.setEmail(null);
        json = mapper.writeValueAsString(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$[0]").value("email must not be blank"));
    }

    @Test
    public void testDeleteUserNotInDatabase() throws Exception {
        String email = "hieu@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);

        //Test delete user not in database
        mvc.perform(MockMvcRequestBuilders.delete("/user/2000")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is(500))
                .andExpect(content().string("Not found id user"));
    }

    @Test
    public void testDeleteSucceed() throws Exception {
        //Test delete succeed
        String email = "hieu@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        mvc.perform(MockMvcRequestBuilders.delete("/user/157")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is(200));

        //Test database
        Optional<User> user = userRepository.findById(new Long(112));
        if (!user.isPresent()) user = null;
        assertNull(user);
    }

    @Test
    public void testDeleteWithRoleEmployee() throws Exception {
        //Test delete with role employee
        String email = "hieu8@gmail.com";
        String password = "123456";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        mvc.perform(MockMvcRequestBuilders.delete("/user/106")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is(403));

    }


}
