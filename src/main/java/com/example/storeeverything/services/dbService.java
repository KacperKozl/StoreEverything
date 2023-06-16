package com.example.storeeverything.services;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import com.example.storeeverything.data.User;
import com.example.storeeverything.data.database.CategoriesEntity;
import com.example.storeeverything.data.database.NotesEntity;
import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.CategoriesEntityRepository;
import com.example.storeeverything.repositories.database.NotesEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Getter
@Transactional
public class dbService {
    @Autowired
    CategoriesEntityRepository categoriesEntityRepository;
    @Autowired
    NotesEntityRepository notesEntityRepository;
    @Autowired
    UsersEntityRepository usersEntityRepository;

    public NotesEntity convertNote(Note newNote){
        NotesEntity ent=new NotesEntity();
        ent.setId(newNote.getId());
        ent.setTitle(newNote.getTitle());
        ent.setContent(newNote.getContent());
        ent.setLink(newNote.getLink());
        ent.setAddDate(new java.sql.Date(newNote.getAdd_date().getTime()));
        if(!Objects.isNull(newNote.getReminder_date())) ent.setReminderDate(new java.sql.Date(newNote.getReminder_date().getTime()));
        String catName=newNote.getCategory().getName();
        CategoriesEntity newCat=findCategory(catName);
        ent.setCategoryName(newCat);
        return ent;
    }
    public void addNewNote(Note newNote){
        NotesEntity ent=convertNote(newNote);
        notesEntityRepository.saveAndFlush(ent);
    }
    public void addNewCategory(Category category){
        CategoriesEntity ent=new CategoriesEntity();
        ent.setCategoryName(category.getName());
        categoriesEntityRepository.save(ent);
    }

    public void addNewUser(User user) {
        UsersEntity ent = new UsersEntity();
        usersEntityRepository.save(ent);
    }
    public CategoriesEntity findCategory(String name){
        return categoriesEntityRepository.findByCategoryName(name);
    }
}
