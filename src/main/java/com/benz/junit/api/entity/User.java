package com.benz.junit.api.entity;

import com.benz.junit.api.db.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER56",schema = Schema.TESTDB)
@Getter
@Setter
@ToString
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GEN",sequenceName = Schema.TESTDB+".USER_ID_SEQ",initialValue = 101,allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "SALARY")
    private double salary;

}
