package com.example.storeeverything.data;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
public class Item {
    @Size(min=3, max=20,message = "{error.size}")
    String title;
    @Size(min=5, max=500,message = "{error.size}")
    String content;
    String link;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String add_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Data nie może być przeszła")
    Date reminder_date;
    @NotNull
    Category category;
    public Item(String title1, String content1, String link1, Date reminder_date1, Category category1){
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
        reminder_date=null;
        category=category1;
    }
    public void setAdd_date(){
        add_date= dateFormat.format(Calendar.getInstance().getTime());
    }
}
