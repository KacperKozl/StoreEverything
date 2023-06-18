package com.example.storeeverything.services;
/**
 * Exception thrown by system in case someone try to register with already existing login
 */
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}