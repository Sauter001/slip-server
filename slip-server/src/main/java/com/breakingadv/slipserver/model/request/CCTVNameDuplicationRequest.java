package com.breakingadv.slipserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CCTVNameDuplicationRequest {
    private String id;
    private String cctvName;
}
