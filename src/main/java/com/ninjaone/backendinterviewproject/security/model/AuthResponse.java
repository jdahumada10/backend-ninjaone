package com.ninjaone.backendinterviewproject.security.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String email;

    private String accessToken;
}
