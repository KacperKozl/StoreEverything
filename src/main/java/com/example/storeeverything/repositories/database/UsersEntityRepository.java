package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersEntityRepository extends JpaRepository<UsersEntity, String> {
    @Override
    List<UsersEntity> findAll();

    UsersEntity findByLogin(String login);
}
