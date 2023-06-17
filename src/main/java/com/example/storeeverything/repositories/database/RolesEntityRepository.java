package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesEntityRepository extends JpaRepository<RolesEntity, Integer> {
}