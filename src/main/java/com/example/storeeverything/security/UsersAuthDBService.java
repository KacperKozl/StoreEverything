package com.example.storeeverything.security;

import com.example.storeeverything.data.database.UsersEntity;

import com.example.storeeverything.repositories.database.RolesEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class UsersAuthDBService implements UserDetailsService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UsersEntityRepository users;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RolesEntityRepository authorities;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Searching for user " + username);
        UsersEntity user = users.findByLogin(username)!= null?users.findByLogin(username):null;
        System.out.println("user " + user);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " not found.");
        else {
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                            user.getLogin(),
//                            user.getPassword(),
//                            user.getRole()
//            );

            LinkedList l=new LinkedList<SimpleGrantedAuthority>();
            l.add(user.getRole());
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    (Collection<? extends GrantedAuthority>) l
                            .stream()
                            .map(role -> {
                                System.out.println("Authorities " + user.getRole());
                                return new SimpleGrantedAuthority(user.getRole());
                            })
                            .collect(Collectors.toSet())
            );
            return userDetails;
        }
    }
}
