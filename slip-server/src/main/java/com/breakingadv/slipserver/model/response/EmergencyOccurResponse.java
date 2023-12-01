package com.breakingadv.slipserver.model.response;

import lombok.Getter;

@Getter
public class EmergencyOccurResponse {
    private String cctvName;
    private String video; // base64로 인코딩된 영상
}
