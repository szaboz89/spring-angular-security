package com.szabodev.example.security.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthenticatedUserDTO {

    private String username;

    private List<String> roles = new ArrayList<>();
}
