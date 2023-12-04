package com.breakingadv.slipserver.controller;

import com.breakingadv.slipserver.model.request.CCTVDuplicationRequest;
import com.breakingadv.slipserver.model.response.ApiResponse;
import com.breakingadv.slipserver.service.CCTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CCTVController {
    private final CCTVService cctvService;

    @GetMapping("/cctvIsDuplicated")
    @ResponseBody
    public ResponseEntity<ApiResponse<Boolean>> getDuplication(@RequestParam String ip) {
        boolean response = cctvService.getDuplication(ip).isDuplicated();

        if (response) {
            return ResponseEntity.ok(new ApiResponse<>(true, "IP 중복됨", true));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(true, "IP 중복 안 됨", false));
        }
    }
}
