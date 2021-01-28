package com.benz.junit.api.dao;

import com.benz.junit.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    @Query("from User")
    Optional<List<User>> findAllUsers();
}
