package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> showAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(long id, User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
    }
    }
}
