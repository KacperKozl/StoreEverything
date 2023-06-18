package com.example.storeeverything.controllers;

import com.example.storeeverything.data.Indeks;
import com.example.storeeverything.data.Note;
import com.example.storeeverything.data.User;
import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import com.example.storeeverything.services.dbService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    @Autowired
    dbService service;

    @Autowired
    private UsersEntityRepository usersEntityRepository;

    @GetMapping("/users")
    public String fetchUsers(Model model) {
        model.addAttribute("users",service.getUsersEntityRepository().findAll());
        return "users";
    }

    @PostMapping("/users/edit")
    public String showEditUser(Model model, @ModelAttribute("indeks") Indeks indeks) {
        UsersEntity user = service.getUsersEntityRepository().findByUserId(indeks.getValue());
        model.addAttribute("roles", service.getRolesEntityRepository().findAll());
        model.addAttribute("editedUser", user);
        model.addAttribute("newUser", new User());
        return "edit_user";
    }

    @PostMapping("/user/edit/init")
    public String editUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model) {
       UsersEntity user = service.convertUser(newUser);
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            model.addAttribute("roles", service.getRolesEntityRepository().findAll());
            model.addAttribute("editedUser", user);
            return "edit_user";
        }
        service.getUsersEntityRepository().save(user);
        return "users";
    }
}
