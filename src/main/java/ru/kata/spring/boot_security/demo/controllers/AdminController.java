package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MyDetailsService myDetailsService;

    @Autowired
    public AdminController(MyDetailsService myDetailsService) {
        this.myDetailsService = myDetailsService;
    }


    @GetMapping(value = "/")
    public String login() {

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/login?logout";
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) myDetailsService.loadUserByUsername(userName);
        model.addAttribute("user", user);
        return "admin";
    }
}