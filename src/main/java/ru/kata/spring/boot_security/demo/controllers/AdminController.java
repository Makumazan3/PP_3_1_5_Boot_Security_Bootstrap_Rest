package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MyDetailsService myDetailsService;
    private final UserService userService;
    private final RoleService roleService;
@Autowired
    public AdminController(MyDetailsService myDetailsService, UserService userService, RoleService roleService) {
    this.myDetailsService = myDetailsService;
    this.userService = userService;
    this.roleService = roleService;
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
        model.addAttribute("role", new Role());
        return "new";
    }

    @PostMapping("/saveUser")
    public String save(@ModelAttribute("user") User user,
                       @RequestParam(name = "roles", required = true) Set<Long> roleIds) {
   Set<Role> roles = new HashSet<>();
    for (Long roleId : roleIds){
        roles.add(roleService.getRoleById(roleId));
    }
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
                             @ModelAttribute("user") User user,
                             @RequestParam(name = "roles", required = true) List<Long> roleIds) {
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