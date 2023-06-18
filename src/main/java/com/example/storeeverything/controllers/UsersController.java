package com.example.storeeverything.controllers;

import com.example.storeeverything.repositories.database.UsersEntityRepository;
import com.example.storeeverything.services.dbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
