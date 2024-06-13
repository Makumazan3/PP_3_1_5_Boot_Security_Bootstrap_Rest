package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MyDetailsService myDetailsService;
    private final UserService userService;
    private final RoleService roleService;

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

    @Autowired
    public AdminController(MyDetailsService myDetailsService, UserService userService, RoleService roleService) {
        this.myDetailsService = myDetailsService;
        this.userService = userService;
        this.roleService = roleService;
    }


    // вывод всех юзеров
    @GetMapping()
    public String showAllUsers(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) myDetailsService.loadUserByUsername(userName);
        model.addAttribute("addRoles", userService.showAllUser());
        model.addAttribute("userInfo", user);
        model.addAttribute("users", userService.showAllUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("setRoles", roleService.getAllRoles());

        return "users";
    }

//создание нового юзера

    @PostMapping("/saveUser")
    public String save(@ModelAttribute("user") User user,
                       @RequestParam(name = "roles", required = true) List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            roles.add(roleService.getRoleById(roleId));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }
    //    обновление юзера

    @PutMapping("/edit")
    public String update(@PathVariable("id") Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }


    //    удаление юзера
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}