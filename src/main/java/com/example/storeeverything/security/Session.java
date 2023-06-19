package com.example.storeeverything.security;

import com.example.storeeverything.services.dbService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Session implements InvalidSessionStrategy {
    @Autowired
    dbService service;
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        service.getCategoriesEntityRepository().flush();
        service.getNotesEntityRepository().flush();
        service.getUsersEntityRepository().flush();
        service.getSharedEntityRepository().flush();
    }
}
