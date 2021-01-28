package com.benz.junit.api.controller;

import com.benz.junit.api.entity.User;
import com.benz.junit.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @GetMapping(value = "/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUser(@PathVariable int userId)
    {
         if(userId!=0)
             return new ResponseEntity<>(userService.findUser(userId), HttpStatus.OK);
         else
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getUsers()
    {
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @PostMapping(value = "/save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> saveUser(@RequestBody User user)
    {
        if(user.getUserId()!=0 && !user.getUserName().trim().isEmpty() && user.getSalary()!=0.0)
            return new ResponseEntity<>(userService.saveUser(user),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/{userId}",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable int userId,@RequestBody User user)
    {
        if(userId!=0 && !user.getUserName().trim().isEmpty() && user.getSalary()!=0.0)
            return new ResponseEntity<>(userService.updateUser(userId,user),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable int userId)
    {
           if(userId!=0)
               userService.deleteUser(userId);
           else
               throw new IllegalArgumentException("userId should not be null");
    }
}
