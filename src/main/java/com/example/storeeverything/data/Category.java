package com.example.storeeverything.data;

import lombok.Data;

@Data
public class Category {
    String name;
    public Category(String s){
        name=s;
    }
}
