package com.example.demo.web;

import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserRespondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping("/list-user")
    public Map<String, Object> findAll(@RequestParam int page, @RequestParam int size) {
        return userService.findAll(page, size);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public UserRespondDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Not found id user");
        }
    }
}
