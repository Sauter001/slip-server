package com.breakingadv.slipserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteCCTVRequest {
    private String id;
    private String[] placeName;
}
