package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cctv")
@Setter
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

    @Column(name = "streaming_username", nullable = false)
    private String streamingUsername;
    @Column(name = "streaming_password", nullable = false)
    private String streamingPassword;


    @Builder
    public CCTV(String ip, boolean emergency, String phoneNumber, String streamingUsername, String streamingPassword) {
        this.ip = ip;
        this.emergency = emergency;
        this.phoneNumber = phoneNumber;
        this.streamingUsername = streamingUsername;
        this.streamingPassword = streamingPassword;
    }
}
