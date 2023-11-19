package com.example.demo.service.hibernate;

import com.example.demo.exception.ConflictException;
import com.example.demo.model.db.User;
import com.example.demo.repository.hibernate.RolesJpaRepository;
import com.example.demo.repository.hibernate.UsersJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersJpaRepository userRepository;
    private final RolesJpaRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(user1 -> {
                    throw new ConflictException("User exists");
                });
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
