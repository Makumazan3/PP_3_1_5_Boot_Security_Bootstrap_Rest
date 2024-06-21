package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final MyDetailsService myDetailsService;
    private final UserService userService;

    @Autowired
    public AdminRestController(MyDetailsService myDetailsService, UserService userService) {
        this.myDetailsService = myDetailsService;
        this.userService = userService;
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

    //выводим список всех юзеров
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> read() {
        List<User> users = userService.showAllUser();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //cоздаём юзера
    @PostMapping(value = "/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //    получение юзера по id
    @GetMapping(value = "/show/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable(name = "id") Long id) {
        final User user = userService.getUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK)
                ;
    }

    //    обновление юзера
    @PutMapping(value = "/update")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long id,
                                        @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/userInfo")
    public ResponseEntity<User> showUserInfo(@AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        User userByName = (User) myDetailsService.loadUserByUsername(user.getUsername());
        return ResponseEntity.ok(userByName);
    }
    //    удаление юзера
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable(name = "id") long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
