package com.breakingadv.slipserver.model.request;

import com.breakingadv.slipserver.entity.User;
import lombok.Getter;

@Getter
public class AuthRequest {
    private String id;
    private String password;

    public User toEntity() {
        return User.builder().id(id).password(password).build();
    }
}
