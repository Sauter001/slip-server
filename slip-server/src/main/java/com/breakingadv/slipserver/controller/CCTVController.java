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

    // CCTV 추가 기능
    @PostMapping("/{userId}/cctvAdd")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> addCCTV(@RequestBody CCTVManageRequest request, @PathVariable String userId) {
        // TODO: CCTV 추가 기능 구현
        CCTVInfoResponse response = cctvService.addCCTV(request, userId);

        if (!response.getCctvName().isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "장소 추가 완료", response));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "장소 추가 실패", response));
        }
    }

    /*
    * spring은 동일 primary key가 있으면 값을 update하는 것으로 보임
    * 이에 따라 addCCTV를 그대로 실행하도록 함
     */
    @PutMapping("/{userId}/cctvEdit")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> updateCCTV(@RequestBody CCTVManageRequest request, @PathVariable String userId) {
        return addCCTV(request, userId);
    }
}
