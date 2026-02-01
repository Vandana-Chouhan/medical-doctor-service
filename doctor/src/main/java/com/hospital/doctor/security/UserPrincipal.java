package com.hospital.doctor.security;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal {
    private Long userId;
    private String username;
    private List<String> roles;
    
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }
}
