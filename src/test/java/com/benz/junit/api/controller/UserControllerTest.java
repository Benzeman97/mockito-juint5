package com.benz.junit.api.controller;


import com.benz.junit.api.BootWithMockitoApplication;
import com.benz.junit.api.entity.User;
import com.benz.junit.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BootWithMockitoApplication.class
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application.yml"})
public class UserControllerTest {

    final private static Logger LOGGER= LogManager.getLogger(UserControllerTest.class);

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getUsetTest() throws Exception
    {
        String expectedUser = new ObjectMapper().writeValueAsString(user1());

        Mockito.when(userService.findUser(Mockito.any(Integer.class))).thenReturn(user1());

         MvcResult result = mockMvc.perform(get("/user/{userId}",101).contentType(MediaType.APPLICATION_JSON_VALUE))
                  .andExpect(status().isOk())
                  .andReturn();

          String actualUser = result.getResponse().getContentAsString();

         LOGGER.info(actualUser);

        Assertions.assertEquals(expectedUser,actualUser,"User should be returned with status 200");
    }

    @Test
    public void getUsersTest() throws Exception
    {
        String expectedUser = new ObjectMapper().writeValueAsString(Arrays.asList(user1(),user2()));

        Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(user1(),user2()));

        MvcResult result = mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String actualEmployees = result.getResponse().getContentAsString();

        LOGGER.info(actualEmployees);

        Assertions.assertEquals(expectedUser,actualEmployees,"all users should be returned with status 2000");
    }

    @Test
    public void saveUserTest() throws Exception
    {

        String expectedUser = new ObjectMapper().writeValueAsString(user1());

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(user1());

        MvcResult result = mockMvc.perform(post("/user/save").accept(new String[]{MediaType.APPLICATION_JSON_VALUE}).content(expectedUser)
        .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn();

        String actualUser = result.getResponse().getContentAsString();

        LOGGER.info(actualUser);

        Assertions.assertEquals(expectedUser,actualUser,"user should be saved and return status with 200");
    }

    @Test
    public void updateUserTest() throws Exception
    {
        User user=user1();
        user.setSalary(120000.0);

        String expectedUser=new ObjectMapper().writeValueAsString(user);

        Mockito.when(userService.updateUser(Mockito.any(Integer.class),Mockito.any(User.class))).thenReturn(user);

       MvcResult result = mockMvc.perform(put("/user/update/{userId}",user.getUserId()).content(expectedUser).accept(new String[]{MediaType.APPLICATION_JSON_VALUE})
        .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn();

       String actualUser = result.getResponse().getContentAsString();

       LOGGER.info(actualUser);

       Assertions.assertEquals(expectedUser,actualUser,"user should be updated and return status 200");
    }

    @Test
    public void deleteUserTest() throws Exception
    {
      MvcResult result =  mockMvc.perform(delete("/user/delete/{userId}",user1().getUserId()))
                .andExpect(status().isOk())
                .andReturn();

      Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus(),"user should be deleted and return status 200");
    }

    private User user2()
    {
        User user=new User();
        user.setUserId(102);
        user.setUserName("Kelly Brook");
        user.setSalary(55000.0);
        return user;
    }

    private User user1()
    {
        User user=new User();
        user.setUserId(101);
        user.setUserName("Nafaz Benzema");
        user.setSalary(78000.0);
        return user;
    }
}
