package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cctv")
@NoArgsConstructor
public class CCTV {
    @Id
    @Column(length = 50)
    private String ip;

    @Column(name = "private_ip",length=50)
    private String privateIp;

    @Column(nullable = false)
    private boolean emergency;
    @Column(nullable = false, length = 128)
    private String phoneNumber;

    @Builder
    public CCTV(String ip, boolean emergency, String phoneNumber) {
        this.ip = ip;
        this.emergency = emergency;
        this.phoneNumber = phoneNumber;
    }
}
