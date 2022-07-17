package com.ninjaone.backendinterviewproject.security.model;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String email;
    private String password;
}
