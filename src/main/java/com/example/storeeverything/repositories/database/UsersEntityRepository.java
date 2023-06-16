package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersEntityRepository extends JpaRepository<UsersEntity, String> {
    @Override
    List<UsersEntity> findAll();

    UsersEntity findByUsername(String username);
}
