package com.breakingadv.slipserver.model.request;

import lombok.Getter;

@Getter
public class EmergencyRequest {
    private String id;
    private String cctvName;
    private boolean emergency;
}
