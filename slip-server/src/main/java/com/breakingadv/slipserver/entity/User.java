package com.breakingadv.slipserver.entity;

import lombok.Builder;

public class User {
    private String id;
    private String password;

    @Builder
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
