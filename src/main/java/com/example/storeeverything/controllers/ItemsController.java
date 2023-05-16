package com.example.storeeverything.controllers;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Item;
import com.example.storeeverything.data.SortIndex;
import com.example.storeeverything.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/items")
public class ItemsController {
    ItemRepository repozytorium=new ItemRepository();
    @GetMapping("/")
    public String start(Model model){
        model.addAttribute("items",repozytorium.getItems());
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",repozytorium.getCategory_list());
        return "index";
    }
    @RequestMapping("/sortby")
    public String showItems(SortIndex sortIndex, Model model){
        if(sortIndex.getValue().equals("alf")) repozytorium.sortByName(sortIndex.getDirection());
        if(sortIndex.getValue().equals("cat")) repozytorium.sortByCategory(sortIndex.getDirection());
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
        newItem.setAdd_date();
        repozytorium.dodaj(newItem);
        return "redirect:/items/";
    }
    @PostMapping("/filterbycategory")
    public String filterByCategory(Category e,Model model){
        System.out.println(e);
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",repozytorium.getCategory_list());
        model.addAttribute("items",repozytorium.filterByCategory(e));
        return "index";
    }
}
