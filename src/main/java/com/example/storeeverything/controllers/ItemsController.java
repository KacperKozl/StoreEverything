package com.example.storeeverything.controllers;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Indeks;
import com.example.storeeverything.data.Note;
import com.example.storeeverything.data.SortIndex;
import com.example.storeeverything.data.database.NotesEntity;
import com.example.storeeverything.repositories.ItemRepository;
import com.example.storeeverything.repositories.database.NotesEntityRepository;
import com.example.storeeverything.services.dbService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    dbService service;
    @Autowired
    private NotesEntityRepository notesEntityRepository;

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
    public String showItems(SortIndex sortIndex, Model model){
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByTitleAsc());
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByTitleDesc());
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByCategoryNameAsc());
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByCategoryNameDesc());
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByAddDateAsc());
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByAddDateDesc());
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByReminderDateAsc());
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findAllByOrderByReminderDateDesc());
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
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
    public String filterByCategory(Category e,Model model){
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        model.addAttribute("notes", notesEntityRepository.findByCategoryName_CategoryName(e.getName()));
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
}
