package com.example.storeeverything.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
