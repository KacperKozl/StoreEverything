package com.example.storeeverything.repositories;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Item;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Getter
public class ItemRepository {
    List<Item> items=new ArrayList<>();
    List<Category> category_list=new ArrayList<>();
    public ItemRepository(){
        items.add(new Item("1","treść",new Category("c")));
        items.add(new Item("2","treść",new Category("b")));
        items.add(new Item("3","treść",new Category("a")));
        category_list.add(new Category("a"));
        category_list.add(new Category("b"));
        category_list.add(new Category("c"));
    }
    public void dodaj(Item item){
        items.add(item);
    }
    public void dodajkategorie(Category category){
        category_list.add(category);
    }
     public void sortByName(int x){
         Collections.sort(items, new Comparator<Item>() {
             @Override
             public int compare(Item o1, Item o2) {
                 return x*o1.getTitle().compareTo(o2.getTitle());
             }
         });
     }
    public void sortByCategory(int x){
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return x*o1.getCategory().compareTo(o2.getCategory());
            }
        });
    }
    public void sortByAddDate(int x){
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return x*o1.getAdd_date().compareTo(o2.getAdd_date());
            }
        });
    }
    public void sortByReminderDate(int x){
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(Objects.isNull(o1.getReminder_date())) return x;
                if(Objects.isNull(o2.getReminder_date())) return -x;
                return x*o1.getReminder_date().compareTo(o2.getReminder_date());
            }
        });
    }
     public List<Item> filterByCategory(Category e){
        List<Item> filtered=new ArrayList<>();
        for(Item item:items){
            if(item.getCategory().compareTo(e)==0){
                filtered.add(item);
            }
        }
        return filtered;
     }
}
