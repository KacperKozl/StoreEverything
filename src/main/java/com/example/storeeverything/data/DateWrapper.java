package com.example.storeeverything.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class DateWrapper {
    Date data;
    public String formatDate(Date data){
        return new SimpleDateFormat("dd-MM-yyyy").format(data);
    }
}
