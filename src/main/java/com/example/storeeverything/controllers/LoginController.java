package com.example.storeeverything.controllers;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.SortIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/auth")
public class LoginController {

//    @Autowired
//    BCryptPasswordEncoder bCryptpasswordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping ("/register")
    public String register(@RequestParam String username, @RequestParam String password){
        System.out.println("Register data " + username + " " + password);
//        System.out.println("Encrypted password " + bCryptpasswordEncoder.encode(password) );
        return "login";
    }
}
