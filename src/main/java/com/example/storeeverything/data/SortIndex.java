package com.example.storeeverything.data;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class SortIndex {
    String value;
    int direction;
    public SortIndex(String s, int x){
        value=s;
        direction=x;
    }
}
