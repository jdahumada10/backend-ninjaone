package com.ninjaone.backendinterviewproject.security;

import com.ninjaone.backendinterviewproject.model.User;
import com.ninjaone.backendinterviewproject.security.model.AuthRequest;
import com.ninjaone.backendinterviewproject.security.model.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        try {
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );
            final Authentication authentication = authManager.authenticate(authenticationToken);
            final User user = (User) authentication.getPrincipal();
            final String accessToken = jwtUtil.generateAccessToken(user);
            final AuthResponse response = AuthResponse.builder().email(user.getEmail()).accessToken(accessToken).build();
            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
