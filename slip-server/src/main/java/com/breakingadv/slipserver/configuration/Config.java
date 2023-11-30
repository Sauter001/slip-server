package com.breakingadv.slipserver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Config {
    public Config() {
    }

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
