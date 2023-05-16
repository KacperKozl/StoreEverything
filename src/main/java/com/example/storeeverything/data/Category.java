package com.example.storeeverything.data;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString
public class Category {
    String name;
    public Category(String s){
        name=s;
    }
    public String toString(){
        return name;
    }
    public int compareTo(Category e){
        return this.name.compareTo(e.name);
    }
}
