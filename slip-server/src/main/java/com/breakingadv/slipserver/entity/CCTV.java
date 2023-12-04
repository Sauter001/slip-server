package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cctv")
@NoArgsConstructor
public class CCTV {
    @Id
    private String ip;

    @Column(nullable = false)
    private boolean emergency;
    @Column(nullable = false)
    private String phoneNumber;

    @Builder
    public CCTV(String ip, boolean emergency, String phoneNumber) {
        this.ip = ip;
        this.emergency = emergency;
        this.phoneNumber = phoneNumber;
    }
}
