package com.example.storeeverything.repositories.database;

import com.example.storeeverything.data.database.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuthoritiesEntityRepository extends JpaRepository<AuthoritiesEntity, Integer> {
    List<AuthoritiesEntity> findByUsername(String username);
}
