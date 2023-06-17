package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS", schema = "PUBLIC", catalog = "DB")
@Data
@NoArgsConstructor
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "NAME", nullable = true, length = 20)
    private String name;
    @Basic
    @Column(name = "SURNAME", nullable = true, length = 50)
    private String surname;
    @Basic
    @Column(name = "LOGIN", nullable = true, length = 20)
    private String login;
    @Basic
    @Column(name = "PASSWORD", nullable = true, length = 50)
    private String password;
    @Basic
    @Column(name = "AGE", nullable = true)
    private Integer age;
    @Basic
    @Column(name = "ROLE_ID", nullable = true,insertable=false, updatable=false)
    private Integer roleId;
    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    private RolesEntity roleName;
    public Collection<GrantedAuthority> getRoles() {
        return List.of(new SimpleGrantedAuthority(roleName.getRoleName()));
    }
}
