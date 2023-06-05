package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CATEGORIES", schema = "PUBLIC", catalog = "DB")
@Data
@NoArgsConstructor
public class CategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "CATEGORY_NAME", nullable = true, length = 20)
    private String categoryName;
}
