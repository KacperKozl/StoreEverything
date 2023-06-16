package com.example.storeeverything.security;

import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.AuthoritiesEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class UsersAuthDBService implements UserDetailsService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UsersEntityRepository users;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    AuthoritiesEntityRepository authorities;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Searching for user " + username);
        UsersEntity user = users.findByUsername(username)!= null?users.findByUsername(username):null;
        System.out.println("user " + user);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " not found.");
        else {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            authorities.findByUsername(username)
                                    .stream()
                                    .map(role -> {
                                        System.out.println("Authorities " + role.getAuthority());
                                        return new SimpleGrantedAuthority(role.getAuthority());
                                    })
                                    .collect(Collectors.toSet())
            );
            return userDetails;
        }
    }
}
