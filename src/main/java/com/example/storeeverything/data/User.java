package com.example.storeeverything.data;

public class User {
    Integer userId;
    String name;
    String surname;
    Object login;
    Object password;
    Integer age;
    Integer roleId;

    public User(Integer userId, String name, String surname, Object login, Object password, Integer age, Integer roleId) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.age = age;
        this.roleId = roleId;
    }

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

    public String getName() {
        return this.name;
    }
}
