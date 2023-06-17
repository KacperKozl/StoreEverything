package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesEntityRepository extends JpaRepository<RolesEntity, Integer> {
    RolesEntity findByRoleName(String role);
}