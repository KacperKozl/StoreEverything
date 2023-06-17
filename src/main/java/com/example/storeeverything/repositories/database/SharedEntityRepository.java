package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.SharedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SharedEntityRepository extends JpaRepository<SharedEntity, Long> {
    @Transactional
    @Modifying
    long deleteByNoteId(long noteId);
    List<SharedEntity> findByUser_Login(String login);
    List<SharedEntity> findByUser_UserIdAndNoteId(Integer userId, Integer noteID);
}