package com.benz.junit.api.service;

import com.benz.junit.api.dao.UserDAO;
import com.benz.junit.api.entity.User;
import com.benz.junit.api.service.impl.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserDAO userDAO;

    @Test
    @DisplayName("findUserTest")
    public void findUserTest()
    {
        int userId=user1().getUserId();

        Mockito.when(userDAO.findById(userId))
                .thenReturn(Optional.of(user1()));

        assertEquals(user1().getUserId(),userService.findUser(userId).getUserId());
    }

    @Test
    public void getUsersTest()
    {
         Mockito.when(userDAO.findAllUsers())
                 .thenReturn(Optional.of(users()));

         assertEquals(users().size(),userService.getUsers().size());
    }

    @Test
    public void saveUserTest()
    {
        User user =user1();

        Mockito.when(userDAO.save(user))
                .thenReturn(user);

        assertEquals(user,userService.saveUser(user));
    }

    @Test
    public void updateUserTest()
    {
        user1().setUserName("Doto Kama");
        user1().setSalary(89000.00);

        User user=user1();

        Mockito.when(userDAO.findById(user.getUserId()))
                .thenReturn(Optional.of(user));

        Mockito.when(userDAO.save(user))
                .thenReturn(user);

        assertEquals(user,userService.updateUser(user.getUserId(),user));
    }

    @Test
    public void deleteUserTest()
    {
        User user=user1();

        Mockito.when(userDAO.findById(user.getUserId()))
                .thenReturn(Optional.of(user));

        userService.deleteUser(user.getUserId());

        Mockito.verify(userDAO,Mockito.times(1)).delete(user);
    }

    private User user1()
    {
        User user =new User();
        user.setUserId(101);
        user.setUserName("Nafaz Benzema");
        user.setSalary(75000.00);
        return user;
    }

    private User user2()
    {
        User user=new User();
        user.setUserId(102);
        user.setUserName("Kelly Brook");
        user.setSalary(90000.00);
        return user;
    }

    private List<User> users()
    {
        return Arrays.asList(user1(),user2());
    }
}
