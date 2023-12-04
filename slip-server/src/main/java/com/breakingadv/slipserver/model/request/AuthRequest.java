package com.breakingadv.slipserver.model.request;


import com.breakingadv.slipserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthRequest {
    private String id;
    private String password;

    public User toEntity() {
        return User.builder().id(this.id).password(this.password).build();
    }
}
