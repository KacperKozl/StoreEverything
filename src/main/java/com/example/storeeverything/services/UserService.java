package com.example.storeeverything.services;


import com.example.storeeverything.data.UserData;

public interface UserService {

    void register(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String login);
}
