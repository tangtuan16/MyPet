package org.example.mypet.DTO.Auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}