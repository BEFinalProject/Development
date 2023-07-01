package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSearchByTokenResponse {
    private UUID uuid_user;
    private String email;
    private String full_name;
    private char gender;
    private String phone;
    private String roles;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
