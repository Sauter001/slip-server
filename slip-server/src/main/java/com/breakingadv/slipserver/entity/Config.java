package com.breakingadv.slipserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Config {
    @JsonProperty("private_ip")
    private String privateIp;

    @JsonProperty("external_ip")
    private String externalIp;

    @JsonProperty("rtsp_url")
    private String rtspUrl;

    public String getPrivateIp() {
        return privateIp;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public String getRtspUrl() {
        return rtspUrl;
    }
}
