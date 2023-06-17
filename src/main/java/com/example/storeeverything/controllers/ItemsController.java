package com.example.storeeverything.controllers;

import com.example.storeeverything.data.*;
import com.example.storeeverything.data.database.NotesEntity;
import com.example.storeeverything.repositories.ItemRepository;
import com.example.storeeverything.repositories.database.NotesEntityRepository;
import com.example.storeeverything.services.crypto;
import com.example.storeeverything.services.dbService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    dbService service;
    @Autowired
    private NotesEntityRepository notesEntityRepository;
    crypto crpt =new crypto();

    @GetMapping("/")
    public String start(Model model){
        model.addAttribute("indeks",new Indeks());
        model.addAttribute("notes",service.getNotesEntityRepository().findAll());
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        return "index";
    }
    @RequestMapping("/sortby")
    public String showItems(SortIndex sortIndex, Model model, HttpServletResponse response){
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByTitleAsc());
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByTitleDesc());
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByCategoryName_CategoryNameAsc());
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByCategoryName_CategoryNameDesc());
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByAddDateAsc());
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByAddDateDesc());
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByReminderDateAsc());
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByReminderDateDesc());
        if(sortIndex.getValue().equals("pop_cat")&&sortIndex.getDirection()==1) model.addAttribute("notes", service.getNotesEntityRepository().sortByPopularCategoriesAsc());
        if(sortIndex.getValue().equals("pop_cat")&&sortIndex.getDirection()==-1) model.addAttribute("notes", service.getNotesEntityRepository().sortByPopularCategoriesDesc());
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());

        // Tworzenie ciasteczka
        Cookie sortCookie = new Cookie("sort.last", sortIndex.getValue() + "-" + sortIndex.getDirection());
        sortCookie.setMaxAge(3600); // Ustawienie czasu życia ciasteczka (np. 1 godzina)
        sortCookie.setPath("/"); // Ustawienie ścieżki, na której będzie dostępne ciasteczko

        // Ustawienie ciasteczka w odpowiedzi
        response.addCookie(sortCookie);

        return "index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("newNote",new Note());
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        return "add_item";
    }
    @PostMapping("/add")
    public String addItems(@Valid @ModelAttribute("newNote") Note newNote, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
            return "add_item";
        }
        newNote.setAdd_date();
        service.addNewNote(newNote);
        return "redirect:/items/";
    }
    @PostMapping("/filterbycategory")
    public String filterByCategory(Category e,Model model, HttpServletResponse response){
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        model.addAttribute("notes", notesEntityRepository.findByCategoryName_CategoryName(e.getName()));

        Cookie categoryCookie = new Cookie("category.last", e.getName());
        categoryCookie.setMaxAge(3600); // Ustawienie czasu życia ciasteczka (np. 1 godzina)
        categoryCookie.setPath("/"); // Ustawienie ścieżki, na której będzie dostępne ciasteczko

        // Ustawienie ciasteczka w odpowiedzi
        response.addCookie(categoryCookie);

        return "index";
    }
    @GetMapping("/category/add")
    public String addCategory(Model model){
        model.addAttribute("newCategory",new Category());
        return "add_category";
    }
    @PostMapping("/category/add")
    public String addCategory(@Valid @ModelAttribute("newCategory") Category newCategory, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add_category";
        }
        service.addNewCategory(newCategory);
        return "redirect:/items/";
    }

    @PostMapping("/edit/init")
    public String editNote(Model model, @ModelAttribute("indeks") Indeks indeks){
        NotesEntity note=service.getNotesEntityRepository().findById(indeks.getValue()).get();
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        model.addAttribute("editedNote",note);
        model.addAttribute("newNote",new Note());
        return "edit_note";
    }
    @PostMapping("/edit")
    public String editNote(@Valid @ModelAttribute("newNote") Note newNote, BindingResult result, Model model){
        NotesEntity note=service.convertNote(newNote);
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
            model.addAttribute("editedNote",note);
            return "edit_note";
        }
        service.getNotesEntityRepository().save(note);
        return "redirect:/items/";
    }
    @PostMapping("/delete")
    public String deleteNote(Model model, @ModelAttribute("indeks") Indeks indeks){
        service.getNotesEntityRepository().deleteById(indeks.getValue());
        return "redirect:/items/";
    }
    @RequestMapping("/shared/{id}")
    public String showSharedNote(Model model, @PathVariable("id") String code){
        String decoded= URLDecoder.decode(code);
        String decrypted="";
        try{
            System.out.println(decoded);
        decrypted=crpt.decrypt(code);
        System.out.println(decrypted);
        }
        catch (Exception e){ System.out.println(e.getMessage());}
        NotesEntity notatka=service.getNotesEntityRepository().findById((Integer) Integer.parseInt(decrypted)).get();
        model.addAttribute("note",notatka);
        return "shared_link";
    }
    @PostMapping("/shareto")
    public String shareNoteTo(HttpServletRequest request, Model model, @ModelAttribute("indeks") Indeks indeks){
        String URL = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
        try{
        URL=URL+"/items/shared/"+URLEncoder.encode(crpt.encrypt(indeks.getValue().toString()));
            System.out.println(crpt.encrypt(indeks.toString()));
        }
        catch (Exception e) {System.out.println(e.getMessage());}
        System.out.println(URL);
        model.addAttribute("URL",URL);
        return "redirect:/items/";
    }
}
