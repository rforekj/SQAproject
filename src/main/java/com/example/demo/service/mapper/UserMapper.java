package com.example.demo.service.mapper;

import com.example.demo.config.Constants;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.dto.UserRegistrationDto;
import com.example.demo.service.dto.UserRespondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    public UserRespondDto userToUserRespondDto(User user) {
        UserRespondDto userRespondDto = new UserRespondDto();
        userRespondDto.setAddress(user.getAddress());
        userRespondDto.setEmail(user.getEmail());
        userRespondDto.setDob(user.getDob());
        userRespondDto.setName(user.getName());
        userRespondDto.setRoles(user.getRoles());
        userRespondDto.setId(user.getId());
        return userRespondDto;
    }

    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = new User();
        user.setRoles(Arrays.asList(roleRepository.findByName(Constants.ROLE_EMPLOYEE)));
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
        user.setName(userRegistrationDto.getName());
        user.setDob(userRegistrationDto.getDob());
        user.setAddress(userRegistrationDto.getAddress());
        user.setCreatedBy(username);
        return user;
    }

    public List<UserRespondDto> usersToUserRespondDtos(List<User> users) {
        return users.stream().map(this::userToUserRespondDto).collect(Collectors.toList());
    }
}
