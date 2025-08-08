package com.akshat.leafTicketingBackend.service;

import com.akshat.leafTicketingBackend.model.Role;
import com.akshat.leafTicketingBackend.model.User;
import com.akshat.leafTicketingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> assignRole(Long userId, Role role) {
        Optional<User> userOpt = userRepository.findById(userId);
        userOpt.ifPresent(user -> {
            Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        });
        return userOpt;
    }

    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }
} 