package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {
    @Override
    List<UsersEntity> findAll();

    @Override
    Optional<UsersEntity> findById(Integer id);

//    UsersEntity findByUsername(String username);
}
