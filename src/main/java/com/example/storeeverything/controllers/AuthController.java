package com.example.storeeverything.controllers;

import com.example.storeeverything.data.UserData;
import com.example.storeeverything.services.UserAlreadyExistException;
import com.example.storeeverything.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userData", new UserData());
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserData userData, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return "register";
        }

        try {
                userService.register(userData);
        } catch (UserAlreadyExistException e){
            bindingResult.rejectValue("login", "userData.login","An account already exists for this login.");
            model.addAttribute("registrationForm", userData);
            return "register";
        }
        return "login";
    }
}