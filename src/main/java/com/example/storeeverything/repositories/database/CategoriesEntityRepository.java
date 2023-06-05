package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesEntityRepository extends JpaRepository<CategoriesEntity, Integer> {
    CategoriesEntity findByCategoryName(String categoryName);
}