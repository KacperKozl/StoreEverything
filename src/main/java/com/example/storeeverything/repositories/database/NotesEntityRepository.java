package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesEntityRepository extends JpaRepository<NotesEntity, Integer> {
    NotesEntity findByCategoryName_CategoryName(String categoryName);
    List<NotesEntity> findAllByOrderByTitleAsc();
    List<NotesEntity> findAllByOrderByTitleDesc();
    List<NotesEntity> findAllByOrderByCategoryNameAsc();
    List<NotesEntity> findAllByOrderByCategoryNameDesc();
    List<NotesEntity> findAllByOrderByAddDateAsc();
    List<NotesEntity> findAllByOrderByAddDateDesc();
    List<NotesEntity> findAllByOrderByReminderDateAsc();
    List<NotesEntity> findAllByOrderByReminderDateDesc();

}