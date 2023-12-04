package com.breakingadv.slipserver.controller;

import com.breakingadv.slipserver.model.request.CCTVDuplicationRequest;
import com.breakingadv.slipserver.model.request.CCTVManageRequest;
import com.breakingadv.slipserver.model.response.ApiResponse;
import com.breakingadv.slipserver.model.response.CCTVInfoResponse;
import com.breakingadv.slipserver.model.response.DuplicationResponse;
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
    public ResponseEntity<ApiResponse<DuplicationResponse>> getDuplication(@RequestParam String ip) {
        boolean isDuplicated = cctvService.getDuplication(ip).isDuplicated();
        DuplicationResponse response = new DuplicationResponse(isDuplicated);
        if (response.isDuplicated()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "IP 중복됨", response));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(true, "IP 중복 안 됨", response));
        }
    }

    @PostMapping("/{userId}/cctvAdd")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> addCCTV(@RequestBody CCTVManageRequest request, @PathVariable String userId) {
        // TODO: CCTV 추가 기능 구현
        return ResponseEntity.ok(new ApiResponse<>(true, "예시: " + userId, new CCTVInfoResponse(new String[]{"아빠집", "엄마집"})));
    }
}
