package com.example.storeeverything.repositories;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Item;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
@Getter
public class ItemRepository {
    List<Item> items=new ArrayList<>();
    List<Category> category_list=new ArrayList<>();
    public ItemRepository(){
        items.add(new Item("1","treść",new Category("a")));
        items.add(new Item("2","treść",new Category("b")));
        items.add(new Item("3","treść",new Category("c")));
        category_list.add(new Category("a"));
        category_list.add(new Category("b"));
        category_list.add(new Category("c"));
    }
    public void dodaj(Item item){
        items.add(item);
    }
     public void sortByName(){
         Collections.sort(items, new Comparator<Item>() {
             @Override
             public int compare(Item o1, Item o2) {
                 return o2.getTitle().compareTo(o1.getTitle());
             }
         });
     }
}
