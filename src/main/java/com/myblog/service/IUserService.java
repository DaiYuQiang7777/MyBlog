package com.myblog.service;

import com.myblog.entity.User;
import java.util.List;

public interface IUserService {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User registerUser(User user);
    User authenticateUser(String username, String password);
}