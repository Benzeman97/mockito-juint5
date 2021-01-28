package com.benz.junit.api.service;

import com.benz.junit.api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findUser(int userId);
    List<User> getUsers();
    User saveUser(User user);
    User updateUser(int userId,User user);
    void deleteUser(int userId);
}
