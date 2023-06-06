package com.example.storeeverything.repositories;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Getter
public class ItemRepository {
    List<Note> notes =new ArrayList<>();
    List<Category> category_list=new ArrayList<>();
    public ItemRepository(){
        notes.add(new Note("1","treść", new Category("c")));
        notes.add(new Note("2","treść", new Category("b")));
        notes.add(new Note("3","treść", new Category("a")));
        category_list.add(new Category("a"));
        category_list.add(new Category("b"));
        category_list.add(new Category("c"));
    }
    public void dodaj(Note note){
        notes.add(note);
    }
    public void dodajkategorie(Category category){
        category_list.add(category);
    }
     public void sortByName(int x){
         Collections.sort(notes, new Comparator<Note>() {
             @Override
             public int compare(Note o1, Note o2) {
                 return x*o1.getTitle().compareTo(o2.getTitle());
             }
         });
     }
    public void sortByCategory(int x){
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return x*o1.getCategory().compareTo(o2.getCategory());
            }
        });
    }
    public void sortByAddDate(int x){
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return x*o1.getAdd_date().compareTo(o2.getAdd_date());
            }
        });
    }
    public void sortByReminderDate(int x){
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if(Objects.isNull(o1.getReminder_date())) return x;
                if(Objects.isNull(o2.getReminder_date())) return -x;
                return x*o1.getReminder_date().compareTo(o2.getReminder_date());
            }
        });
    }
     public List<Note> filterByCategory(Category e){
        List<Note> filtered=new ArrayList<>();
        for(Note note : notes){
            if(note.getCategory().compareTo(e)==0){
                filtered.add(note);
            }
        }
        return filtered;
     }
}
