package com.example.storeeverything.data;

import jakarta.persistence.criteria.CriteriaBuilder;

public class User  {
    private Integer userId;
    private String name;
    private String surname;
    private Object login;
    private Object password;
    private Integer age;
    private Integer roleId;

    public User(Integer userId, String name, String surname, Object login, Object password, Integer age, Integer roleId) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.age = age;
        this.roleId = roleId;
    }

//    public User(Integer userId, String name, String surname, Object login, Object password, Integer age) {
//        this.userId = userId;
//        this.name = name;
//        this.surname = surname;
//        this.login = login;
//        this.password = password;
//        this.age = age;
//        this.roleId = null;
//    }
//
//    public User(Integer userId, String name, String surname, Object login, Integer roleId, Object password) {
//        this.userId = userId;
//        this.name = name;
//        this.surname = surname;
//        this.login = login;
//        this.password = password;
//        this.age = null;
//        this.roleId = roleId;
//    }

    public User(Integer userId, String name, String surname, Object login, Object password) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.age = null;
        this.roleId = null;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

//    public String getUsername() {
//        return this.name;
//    }
}
