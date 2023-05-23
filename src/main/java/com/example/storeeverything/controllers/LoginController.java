package com.example.storeeverything.controllers;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.SortIndex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @PostMapping("/login")
    public String start(Model model){
        return "login";
    }
}
