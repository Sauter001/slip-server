package com.breakingadv.slipserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class CCTV {
    @Id
    private String ip;

    @Column
    private boolean emergency;
    @Column
    private String phoneNumber;

    @Builder
    public CCTV(String ip, boolean emergency, String phoneNumber) {
        this.ip = ip;
        this.emergency = emergency;
        this.phoneNumber = phoneNumber;
    }
}
