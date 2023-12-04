package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "pw", nullable = false)
    private String password;

    @Builder
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
