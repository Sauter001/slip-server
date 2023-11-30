package com.breakingadv.slipserver.model.request;

import lombok.Getter;

@Getter
public class CCTVManageRequest {
    private String id;
    private String cctvName;
    private String phoneNumber;
    private String ip;
}
