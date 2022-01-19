package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository repository) {
        this.userService = userService;
        this.roleRepository = repository;
    }

// вывод всех юзеров
@GetMapping()
public String showAllUsers(ModelMap model){
        model.addAttribute("users", userService.showAllUser());
        return "users";
}

//создание нового юзера
    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "new";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/posts";
    }
    //    обновление юзера
    @PutMapping("/edit{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
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