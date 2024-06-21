package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;

import java.security.Principal;


@RestController
@RequestMapping({"api/user"})
public class UserController {

    private final MyDetailsService myDetailsService;

    @Autowired
    public UserController(MyDetailsService myDetailsService) {
        this.myDetailsService = myDetailsService;
    }

    @GetMapping()
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok((User)myDetailsService.loadUserByUsername(principal.getName()));
    }
    @GetMapping("/logout")
    public String logoutPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }
}

