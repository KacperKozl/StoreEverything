package com.example.storeeverything.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@NoArgsConstructor
public class Item {
    String title;
    String content;
    String link;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String add_date;
    String reminder_date;
    Category category;
    public Item(String title1,String content1,String link1,String reminder_date1,Category category1){
        title=title1;
        content=content1;
        link=link1;
        add_date= dateFormat.format(Calendar.getInstance().getTime());
        reminder_date=reminder_date1;
        category=category1;
    }
    public Item(String title1,String content1,Category category1){
        title=title1;
        content=content1;
        link="";
        add_date= dateFormat.format(Calendar.getInstance().getTime());
        reminder_date="";
        category=category1;
    }
    public void setAdd_date(){
        add_date= dateFormat.format(Calendar.getInstance().getTime());
    }
}
