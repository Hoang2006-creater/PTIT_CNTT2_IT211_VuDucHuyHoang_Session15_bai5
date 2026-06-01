package com.re.session15_bai5.reponse;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final String message;

    public AuthResponse(String message) {
        this.message = message;
    }
}