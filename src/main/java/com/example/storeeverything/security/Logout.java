package com.example.storeeverything.security;

import com.example.storeeverything.services.dbService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
@Service
public class Logout implements LogoutHandler {
    @Autowired
    dbService service;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        service.getCategoriesEntityRepository().flush();
        service.getNotesEntityRepository().flush();
        service.getUsersEntityRepository().flush();
        service.getSharedEntityRepository().flush();
    }
}
