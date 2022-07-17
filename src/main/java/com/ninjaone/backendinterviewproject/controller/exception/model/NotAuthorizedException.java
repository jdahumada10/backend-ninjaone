package com.ninjaone.backendinterviewproject.controller.exception.model;

public class NotAuthorizedException extends Exception{
    public NotAuthorizedException(String message) {

        super(message);
    }
}
