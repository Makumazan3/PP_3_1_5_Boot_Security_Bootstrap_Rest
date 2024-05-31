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

    @Autowired
    public AdminController(UserService userService, RoleRepository repository) {
        this.userService = userService;
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
        return "new";
    }

    @PostMapping("/saveUser")
    public String save(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
    //    обновление юзера

    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("userList", userService.showAllUser());
        return "edit";
    }
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("id") Long id,
                             @ModelAttribute("user") User user) {
        User user1 = userService.getUserById(id);
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        userService.updateUser(id, user1);
        return "redirect:/admin";
    }


//    удаление юзера
    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}