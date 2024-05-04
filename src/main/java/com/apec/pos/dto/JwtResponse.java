package com.apec.pos.dto;

import com.apec.pos.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private  String jwttoken;
    private final String type = "Bearer";
    private  Long id;
    private  String username;
    private  String email;
    private String phoneNumber;
    private String accountName;
    private String imgReturn;
    private Set<RoleEntity> roles;
}
