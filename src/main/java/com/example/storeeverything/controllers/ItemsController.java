package com.example.storeeverything.controllers;

import com.example.storeeverything.data.Item;
import com.example.storeeverything.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {
    ItemRepository repozytorium=new ItemRepository();
    @GetMapping("/")
    public String start(Model model){
        model.addAttribute("items",repozytorium.getItems());
        model.addAttribute("category_list",repozytorium.getCategory_list());
        System.out.println(repozytorium.getCategory_list());
        return "index";
    }
    @GetMapping("/sortbyname")
    public String showItems(Model model){
        repozytorium.sortByName();
        return "redirect:/items/";
    }

    @GetMapping("/manage")
    public String add(Model model){
        model.addAttribute("newItem",new Item());
        model.addAttribute("category_list",repozytorium.getCategory_list());
        return "add_item";
    }
    @PostMapping("/add")
    public String addItems(Item newItem){
        repozytorium.dodaj(newItem);
        return "redirect:/items/";
    }
}
