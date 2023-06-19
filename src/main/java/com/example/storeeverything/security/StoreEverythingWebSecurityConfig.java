package com.example.storeeverything.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class StoreEverythingWebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
//        int rounds = 12;
//        return new BCryptPasswordEncoder(rounds);
        return NoOpPasswordEncoder.getInstance();
    }
    @Autowired
    Logout customLogoutHandler;
    @Autowired
    Session customSessionStrategy;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withUsername("user1")
//                        .password("user1")
//                        .roles("USER")
//                        .build();
//
//        UserDetails weak_user =
//                User.withUsername("user2")
//                        .password("user2")
//                        .roles("USER_WEAK")
//                        .build();
//
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password("admin")
//                        .roles("ADMIN")
//                        .build();
//
//        System.out.println(user.getUsername()+"" + user.getPassword()+
//                "" +user.getAuthorities());
//
//        System.out.println(weak_user.getUsername()+"" + weak_user.getPassword()+
//                "" +weak_user.getAuthorities());
//
//        System.out.println(admin.getUsername()+"" + admin.getPassword()+
//                "" +admin.getAuthorities());
//
//        return new InMemoryUserDetailsManager(user, weak_user, admin);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/styles/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()// access to all users
                        .requestMatchers("/items/category/*").hasAuthority("full")
                        .requestMatchers("/users").hasAuthority("admin")
                        .requestMatchers("/users/edit").hasAuthority("admin")
                        .requestMatchers("/users/edit/init").hasAuthority("admin")
                        .requestMatchers("/items/today").permitAll()
                        .requestMatchers("/items/shared/{id}").permitAll()
                        .requestMatchers("/items/shareto/mine").hasAnyAuthority("full","limited")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().hasAuthority("full") // access to the rest of the resources regardless of the role
                )
                .formLogin((form) -> form //redirect to the login page regardless of the string
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/items/today", true)
                )
//                .sessionManagement((session)->session.invalidSessionStrategy(customSessionStrategy))
                .logout((logout) -> logout.logoutSuccessUrl("/").permitAll().addLogoutHandler(customLogoutHandler))
                .csrf(csrf -> csrf .ignoringRequestMatchers(toH2Console())).headers().frameOptions().disable();

        return http.build();
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService UsersAuthDBService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(UsersAuthDBService);
        authProvider.setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers = List.of(authProvider);

        return new ProviderManager(providers);
    }

}
