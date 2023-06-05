package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "NOTES", schema = "PUBLIC", catalog = "DB")
@Data
@NoArgsConstructor
@Getter
@Setter
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "TITLE", nullable = true, length = 20)
    private String title;
    @Basic
    @Column(name = "CONTENT", nullable = true, length = 500)
    private String content;
    @Basic
    @Column(name = "LINK", nullable = true, length = 2048)
    private String link;
    @Basic
    @Column(name = "ADD_DATE", nullable = true)
    private Date addDate;
    @Basic
    @Column(name = "REMINDER_DATE", nullable = true)
    private Date reminderDate;
    @Basic
    @Column(name = "CATEGORY_ID", nullable = true,insertable=false, updatable=false)
    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    private CategoriesEntity categoryName;
    @Basic
    @Column(name = "IS_PUBLIC", nullable = true)
    private Boolean isPublic;
    public Integer getId(){
        return id;
    }
//    @Basic
//    @Column(name = "USER_ID", nullable = true,insertable=false, updatable=false)
//    private Integer userId;
//    @ManyToOne
//    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
//    private UsersEntity usersByUserId;
    public String getAdd(){
        return new SimpleDateFormat("dd-MM-yyyy").format(addDate);
    }
    public String getReminder(){
        if(Objects.isNull(reminderDate)) return "";
        return new SimpleDateFormat("dd-MM-yyyy").format(reminderDate);
    }
}
