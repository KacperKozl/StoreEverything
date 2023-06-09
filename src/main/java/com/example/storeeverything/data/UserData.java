package com.example.storeeverything.data;

import com.example.storeeverything.data.database.RolesEntity;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserData implements Serializable {

//    @NotEmpty(message = "Name can not be empty!")
    @Size(min=3, max=25,message = "{error.size}")
    private String name;

    @Size(min=3, max=25,message = "{error.size}")
    private String surname;

    @Size(min=5, max=25,message = "{error.size}")
    private String login;

    private Integer age;

    @Size(min=5, max=25,message = "{error.size}")
    private String password;

    @Size(min=5, max=25,message = "{error.size}")
    private String repeatPassword;

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public RolesEntity getRoleName() {
        return roleName;
    }

    public void setRoleName(RolesEntity roleName) {
        this.roleName = roleName;
    }

    private String roleId;
    private RolesEntity roleName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}