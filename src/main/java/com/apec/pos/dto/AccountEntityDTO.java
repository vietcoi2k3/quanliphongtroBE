package com.apec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntityDTO {

    private String username;
    private String password;
    private String accountName;
    private String phoneNumber;
    private String email;
}
