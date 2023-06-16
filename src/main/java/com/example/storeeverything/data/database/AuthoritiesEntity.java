package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AUTHORITIES", schema = "PUBLIC", catalog = "DB")
@Data
public class AuthoritiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 20, insertable = false)
    private String username;

    @Basic
    @Column(name = "AUTHORITY", nullable = false, length = 50)
    private String authority;
}
