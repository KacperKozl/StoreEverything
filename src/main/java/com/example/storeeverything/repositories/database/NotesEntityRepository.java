package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.CategoriesEntity;
import com.example.storeeverything.data.database.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotesEntityRepository extends JpaRepository<NotesEntity, Integer> {

    List<NotesEntity> findByUser_LoginAndCategoryName_CategoryName(String login, String name);
    List<NotesEntity> findByUser_LoginOrderByTitleAsc(String login);
    List<NotesEntity> findByUser_LoginOrderByTitleDesc(String login);
    List<NotesEntity> findByUser_LoginOrderByCategoryName_CategoryNameAsc(String login);
    List<NotesEntity> findByUser_LoginOrderByCategoryName_CategoryNameDesc(String login);
    List<NotesEntity> findByUser_LoginOrderByAddDateAsc(String login);
    List<NotesEntity> findByUser_LoginOrderByAddDateDesc(String login);
    List<NotesEntity> findByUser_LoginOrderByReminderDateAsc(String login);
    List<NotesEntity> findByUser_LoginOrderByReminderDateDesc(String login);
    List<NotesEntity> findByUser_Login(String login);
    List<NotesEntity> findByUser_LoginAndReminderDate(String login,Date date);

    /*@Query("""
        select n from NotesEntity n
        where n.categoryName.categoryName = ?1
        order by n.title asc""")
    List<NotesEntity> findByCategoryName_CategoryName(String categoryName);*/
    @Query(value="Select notes.* from notes, (Select Category_id,count(1) liczba from notes group by category_id ) tab, users u where notes.category_id=tab.category_id and u.user_id=notes.user_id and u.login=?1 order by liczba asc;"
    ,nativeQuery = true)
    List<NotesEntity> sortByPopularCategoriesAsc(String login);

    @Query(value="Select notes.* from notes, (Select Category_id,count(1) liczba from notes group by category_id ) tab, users u where notes.category_id=tab.category_id and u.user_id=notes.user_id and u.login=?1 order by liczba desc;"
            ,nativeQuery = true)
    List<NotesEntity> sortByPopularCategoriesDesc(String login);

    @Override
    Optional<NotesEntity> findById(Integer integer);


}