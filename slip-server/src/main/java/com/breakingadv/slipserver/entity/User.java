package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
