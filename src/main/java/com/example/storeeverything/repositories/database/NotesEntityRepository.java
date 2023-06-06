package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.CategoriesEntity;
import com.example.storeeverything.data.database.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotesEntityRepository extends JpaRepository<NotesEntity, Integer> {
    @Transactional
    @Modifying
    @Query("""
            update NotesEntity n set n.title = ?1
            where n.title = ?2 and n.content = ?3 and n.reminderDate = ?4 and n.categoryName = ?5 and n.isPublic = ?6""")
    int updateTitleByTitleAndContentAndReminderDateAndCategoryNameAndIsPublic(String title, String title1, String content, Date reminderDate, CategoriesEntity categoryName, Boolean isPublic);
    NotesEntity findByCategoryName_CategoryName(String categoryName);
    List<NotesEntity> findAllByOrderByTitleAsc();
    List<NotesEntity> findAllByOrderByTitleDesc();
    List<NotesEntity> findAllByOrderByCategoryNameAsc();
    List<NotesEntity> findAllByOrderByCategoryNameDesc();
    List<NotesEntity> findAllByOrderByAddDateAsc();
    List<NotesEntity> findAllByOrderByAddDateDesc();
    List<NotesEntity> findAllByOrderByReminderDateAsc();
    List<NotesEntity> findAllByOrderByReminderDateDesc();


    @Override
    Optional<NotesEntity> findById(Integer integer);
}