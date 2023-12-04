package com.breakingadv.slipserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CCTVManageRequest {
    private String id;
    private String cctvName;
    private String phoneNumber;
    private String streamingUsername;
    private String streamingPassword;
    private String ip;
}
