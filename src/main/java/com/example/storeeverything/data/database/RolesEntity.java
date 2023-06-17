package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ROLES", schema = "PUBLIC", catalog = "DB")
@Data
@NoArgsConstructor
public class RolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ROLE_ID", nullable = false)
    private int roleId;
    @Basic
    @Column(name = "ROLE_NAME", nullable = true, length = 7)
    private String roleName;

}
