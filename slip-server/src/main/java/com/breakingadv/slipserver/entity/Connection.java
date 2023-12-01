package com.breakingadv.slipserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class Connection {
    @Id
    private String userId;
    @Id
    private String ip;
    @Column
    private String cctvName;

    @Builder
    public Connection(String userId, String ip, String cctvName) {
        this.userId = userId;
        this.ip = ip;
        this.cctvName = cctvName;
    }
}
