package com.example.storeeverything.data;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Getter
@ToString
@NoArgsConstructor
public class Category {
//    @Pattern(regexp = "[a-z]{3,20}",message = "Nazwa kategorii musi mieć pomiędzy 3-20 małych liter.")
    @Pattern(regexp = "(?U)\\p{Ll}{3,20}",message = "Nazwa kategorii musi mieć pomiędzy 3-20 małych liter.")
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
