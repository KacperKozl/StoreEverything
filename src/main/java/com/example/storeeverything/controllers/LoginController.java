package com.example.storeeverything.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

////    @Autowired
//    BCryptPasswordEncoder bCryptpasswordEncoder;

    @GetMapping(value="/")
    public String hello() {
        return "startpage";
    }
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
