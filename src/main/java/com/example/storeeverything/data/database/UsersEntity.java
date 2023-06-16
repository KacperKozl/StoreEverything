package com.example.storeeverything.data.database;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "USERS", schema = "PUBLIC", catalog = "DB")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "NAME", nullable = true, length = 20)
    private Object name;
    @Basic
    @Column(name = "SURNAME", nullable = true, length = 50)
    private Object surname;
    @Basic
    @Column(name = "LOGIN", nullable = true, length = 20)
    private Object login;
    @Basic
    @Column(name = "PASSWORD", nullable = true, length = 50)
    private Object password;
    @Basic
    @Column(name = "AGE", nullable = true)
    private Integer age;
    @Basic
    @Column(name = "ROLE_ID", nullable = true)
    private Integer roleId;

    public Integer getId(){
        return userId;
    }
}
