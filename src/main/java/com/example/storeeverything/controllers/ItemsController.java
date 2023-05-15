package com.example.storeeverything.controllers;

import com.example.storeeverything.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {
    ItemRepository repozytorium=new ItemRepository();
    @GetMapping("/sortbyname")
    public String showItems(Model model){
        repozytorium.sortByName();
        model.addAttribute("items",repozytorium.getItems());
        return "index";
    }
}
