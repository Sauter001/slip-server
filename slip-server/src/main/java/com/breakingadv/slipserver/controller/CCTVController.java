package com.breakingadv.slipserver.controller;

import com.breakingadv.slipserver.model.response.ApiResponse;
import com.breakingadv.slipserver.service.CCTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CCTVController {
    private final CCTVService cctvService;

    @GetMapping("/cctvIsDuplicated")
    public ResponseEntity<ApiResponse<Boolean>> getDuplication(@RequestParam String ip) {
        boolean response = cctvService.existsByIp(ip);
        return ResponseEntity.ok(new ApiResponse<>(true, "IP 중복 안 됨", response));
    }
}
