package com.breakingadv.slipserver.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "connections")
@IdClass(ConnectionPK.class)
@NoArgsConstructor
public class Connection {
    @Id
    @Column(nullable = false)
    private int uid;

    @Column(nullable = false, length = 50)
    private String ip;
    @Column(name = "cctv_name", nullable = false)
    private String cctvName;

    @Column(name = "streaming_username", nullable = false, unique = true)
    private String streamingUsername;
    @Column(name = "streaming_password", nullable = false, unique = true)
    private String streamingPassword;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ip", referencedColumnName = "ip", insertable = false, updatable = false)
    private CCTV cctv;

    @Builder
    public Connection(int userId, String ip, String cctvName) {
        this.uid = userId;
        this.ip = ip;
        this.cctvName = cctvName;
    }
}
