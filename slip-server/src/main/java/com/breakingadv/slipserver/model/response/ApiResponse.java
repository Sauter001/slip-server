package com.breakingadv.slipserver.model.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
