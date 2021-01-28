package com.benz.junit.api.service.impl;

import com.benz.junit.api.dao.UserDAO;
import com.benz.junit.api.entity.User;
import com.benz.junit.api.exception.DataNotFoundException;
import com.benz.junit.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public User findUser(int userId) {
        return userDAO.findById(userId)
                .orElseThrow(()->new DataNotFoundException(String.format("User is not found with %d",userId)));
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAllUsers()
                .orElseThrow(()->new DataNotFoundException("No Users available in DB"));
    }

    @Override
    public User saveUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public User updateUser(int userId,User user) {
        User user1=findUser(userId);
          if(!Objects.isNull(user1))
            return userDAO.save(user);
          else
              throw new DataNotFoundException(String.format("User is not found with %d",userId));
    }

    @Override
    public void deleteUser(int userId) {
         User user = findUser(userId);
         if(!Objects.isNull(user))
             userDAO.delete(user);
         else
             throw new DataNotFoundException(String.format("User is not found with %d",userId));
    }
}
