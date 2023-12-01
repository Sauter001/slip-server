package com.breakingadv.slipserver.model.response;

import com.breakingadv.slipserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private User user;
    private String expiresAt;
}
