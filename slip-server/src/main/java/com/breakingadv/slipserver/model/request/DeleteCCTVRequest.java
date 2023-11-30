package com.breakingadv.slipserver.model.request;

import lombok.Getter;

@Getter
public class DeleteCCTVRequest {
    private String id;
    private String[] placeName;
}
