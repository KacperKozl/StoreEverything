package com.example.storeeverything.controllers;

import com.example.storeeverything.data.*;
import com.example.storeeverything.data.database.NotesEntity;
import com.example.storeeverything.data.database.SharedEntity;
import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.ItemRepository;
import com.example.storeeverything.repositories.database.NotesEntityRepository;
import com.example.storeeverything.repositories.database.SharedEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import com.example.storeeverything.services.crypto;
import com.example.storeeverything.services.dbService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    dbService service;
    @Autowired
    private NotesEntityRepository notesEntityRepository;
    crypto crpt =new crypto();
    @Autowired
    private UsersEntityRepository usersEntityRepository;
    @Autowired
    private SharedEntityRepository sharedEntityRepository;

    @GetMapping("/")
    public String start(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Cookie[] ciasteczka=request.getCookies();
        for (Cookie cookie:ciasteczka) {
            if(cookie.getName().compareTo("sort.last")==0){
                String[] param=cookie.getValue().split("_");
                SortIndex ind=new SortIndex(param[0],Integer.parseInt(param[1]));
                redirectAttributes.addFlashAttribute("sortIndex",ind);
                return "redirect:/items/sortby";
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        model.addAttribute("indeks",new Indeks());
        model.addAttribute("notes",service.getNotesEntityRepository().findByUser_Login(login));
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        return "index";
    }
    @RequestMapping("/today")
    public String today(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        List<NotesEntity> list=service.getNotesEntityRepository().findByUser_LoginAndReminderDate(login,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        if(list.isEmpty()) return "redirect:/";
        model.addAttribute("notes",list);
        return "today";
    }
    @RequestMapping("/cookie")
    public String deleteSortCookie(Model model,HttpServletResponse response){

        Cookie sortCookie = new Cookie("sort.last", null);
        sortCookie.setMaxAge(0); // Ustawienie czasu życia ciasteczka (np. 1 godzina)
        sortCookie.setPath("/"); // Ustawienie ścieżki, na której będzie dostępne ciasteczko

        // Ustawienie ciasteczka w odpowiedzi
        response.addCookie(sortCookie);
        return "redirect:/items/";
    }
    @RequestMapping("/sortby")
    public String showItems(@ModelAttribute SortIndex sortIndex, Model model, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByTitleAsc(login));
        if(sortIndex.getValue().equals("alf")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByTitleDesc(login));
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByCategoryName_CategoryNameAsc(login));
        if(sortIndex.getValue().equals("cat")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByCategoryName_CategoryNameDesc(login));
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByAddDateAsc(login));
        if(sortIndex.getValue().equals("A_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByAddDateDesc(login));
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByReminderDateAsc(login));
        if(sortIndex.getValue().equals("R_date")&&sortIndex.getDirection()==-1) model.addAttribute("notes",service.getNotesEntityRepository().findByUser_LoginOrderByReminderDateDesc(login));
        if(sortIndex.getValue().equals("pop_cat")&&sortIndex.getDirection()==1) model.addAttribute("notes", service.getNotesEntityRepository().sortByPopularCategoriesAsc(login));
        if(sortIndex.getValue().equals("pop_cat")&&sortIndex.getDirection()==-1) model.addAttribute("notes", service.getNotesEntityRepository().sortByPopularCategoriesDesc(login));
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());

        // Tworzenie ciasteczka
        Cookie sortCookie = new Cookie("sort.last", sortIndex.getValue() + "_" + sortIndex.getDirection());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        if(result.hasErrors()){
            model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
            return "add_item";
        }
        newNote.setAdd_date();
        service.addNewNote(newNote,login);
        return "redirect:/items/";
    }
    @PostMapping("/filterbycategory")
    public String filterByCategory(Category e,Model model, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        model.addAttribute("sortIndex",new SortIndex());
        model.addAttribute("category",new Category("a"));
        model.addAttribute("category_list",service.getCategoriesEntityRepository().findAll());
        model.addAttribute("notes", notesEntityRepository.findByUser_LoginAndCategoryName_CategoryName(login,e.getName()));
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        note.setUser(service.getUsersEntityRepository().findByLogin(login));
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
        sharedEntityRepository.deleteByNoteId(indeks.getValue());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String URL = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
        try{
        URL=URL+"/items/shared/"+URLEncoder.encode(crpt.encrypt(indeks.getValue().toString()));
            System.out.println(crpt.encrypt(indeks.toString()));
        }
        catch (Exception e) {System.out.println(e.getMessage());}
        System.out.println(indeks.getValue());
        ShareInd share=new ShareInd();
        share.setNoteID(indeks.getValue());
        model.addAttribute("ind",share);
        model.addAttribute("URL",URL);
        model.addAttribute("users_list", usersEntityRepository.findByRoleName_RoleNameNotAndUserIdNot("admin",service.getUsersEntityRepository().findByLogin(login).getUserId()));
        return "share_to";
    }
    @PostMapping("/shareto/chosen")
    public String dbShare(HttpServletRequest request, Model model, @ModelAttribute("ind") ShareInd ind){
        if(service.getSharedEntityRepository().findByUser_UserIdAndNoteId(ind.getUserID(),ind.getNoteID()).isEmpty()){
        UsersEntity user=service.getUsersEntityRepository().findByUserId(ind.getUserID());
        NotesEntity note=service.getNotesEntityRepository().findById(ind.getNoteID()).get();
        SharedEntity share=new SharedEntity();
        share.setNote(note);
        share.setUser(user);
        service.getSharedEntityRepository().save(share);}
        return "redirect:/items/";
    }
    @RequestMapping("/shareto/mine")
    public String showSharedToMe(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        List<NotesEntity> list=service.getSharedEntityRepository().findByUser_Login(login).stream().map(SharedEntity::getNote).collect(Collectors.toList());
        model.addAttribute("notes",list);
        return "shared_mine";
    }
}
