package com.example.storeeverything;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//abstract
class StoreEverythingSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        int rounds = 10;
        return new BCryptPasswordEncoder(rounds);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user1")
                        .password("user1")
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.withUsername("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        System.out.println(user.getUsername()+"" + user.getPassword()+
                "" +user.getAuthorities());

        System.out.println(admin.getUsername()+"" + admin.getPassword()+
                "" +admin.getAuthorities());

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }

}
