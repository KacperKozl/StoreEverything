package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesEntityRepository extends JpaRepository<CategoriesEntity, Integer> {
    CategoriesEntity findByCategoryName(String categoryName);

    @Query(value="Select Categories.* from categories left join (Select Category_id,count(1) liczba from notes group by category_id ) tab  on categories.category_id=tab.category_id order by liczba desc;",
          nativeQuery = true)
    List<CategoriesEntity> findPopular();
}