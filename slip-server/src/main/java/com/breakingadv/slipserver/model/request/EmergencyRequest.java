package com.breakingadv.slipserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmergencyRequest {
    private String id;
    private String cctvName;
    private boolean emergency;
}
