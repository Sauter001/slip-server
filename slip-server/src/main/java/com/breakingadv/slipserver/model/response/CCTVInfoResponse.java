package com.breakingadv.slipserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CCTVInfoResponse {
    private List<String> cctvName;
}
