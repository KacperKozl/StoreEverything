package com.example.storeeverything.security;

import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.RolesEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersAuthDBService implements UserDetailsService {
    @Autowired
    UsersEntityRepository users;

    @Autowired
    RolesEntityRepository rolesEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Searching for user " + username);
        UsersEntity user = users.findByLogin(username)!= null?users.findByLogin(username):null;
        System.out.println("user " + user);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " not found.");
        else {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                            user.getLogin(),
                            user.getPassword(),
                            user.getRoles()
            );
            System.out.println(userDetails.getAuthorities());
            return userDetails;
        }
    }
}
