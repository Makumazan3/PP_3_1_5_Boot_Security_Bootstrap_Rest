package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final MyDetailsService myDetailsService;

    @Autowired
    public UserController(MyDetailsService myDetailsService) {
        this.myDetailsService = myDetailsService;
    }

    @GetMapping()
    public String getUserById(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) myDetailsService.loadUserByUsername(userName);

        model.addAttribute("user", user);
        return "user";
    }



}

