package com.szabodev.example.security.web;

import com.szabodev.example.security.dto.AuthenticatedUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            log.info("User not authenticated!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated!");
        }
        log.info("User is authenticated, name: {}", auth.getPrincipal());
        return ResponseEntity.ok(auth.getPrincipal());
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/user-details")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAuthenticatedUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            log.info("User not authenticated!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated!");
        }

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) auth.getAuthorities();
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        authenticatedUserDTO.setUsername(auth.getName());
        for (GrantedAuthority authority : authorities) {
            authenticatedUserDTO.getRoles().add(authority.getAuthority());
        }
        log.info("User is authenticated: {}", authenticatedUserDTO);
        return ResponseEntity.ok(authenticatedUserDTO);
    }
}
