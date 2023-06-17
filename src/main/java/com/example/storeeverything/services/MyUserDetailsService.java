package com.example.storeeverything.services;

import com.example.storeeverything.data.MyUserPrincipal;
import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersEntityRepository usersEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsersEntity user = usersEntityRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new MyUserPrincipal(user);
    }
}