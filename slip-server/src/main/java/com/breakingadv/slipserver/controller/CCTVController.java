package com.breakingadv.slipserver.controller;

import com.breakingadv.slipserver.exception.UserNotFoundException;
import com.breakingadv.slipserver.model.request.CCTVManageRequest;
import com.breakingadv.slipserver.model.request.DeleteCCTVRequest;
import com.breakingadv.slipserver.model.request.EmergencyRequest;
import com.breakingadv.slipserver.model.response.*;
import com.breakingadv.slipserver.service.CCTVService;
import com.breakingadv.slipserver.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CCTVController {
    private final CCTVService cctvService;
    private final ConnectionService connectionService;

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
        CCTVInfoResponse response = cctvService.addCCTV(request, userId);

        if (!response.getCctvName().isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "장소 추가 완료", response));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "장소 추가 실패", response));
        }
    }

    /*
     * CCTV 수정 기능
     * spring은 동일 primary key가 있으면 값을 update하는 것으로 보임
     * 이에 따라 addCCTV를 그대로 실행하도록 함
     */
    @PutMapping("/{userId}/cctvEdit")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> updateCCTV(@RequestBody CCTVManageRequest request, @PathVariable String userId) {
        return addCCTV(request, userId);
    }

    // 특정 CCTV 삭제 기능
    @DeleteMapping("/{userId}/delete")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> deleteCCTV(
            @RequestBody DeleteCCTVRequest request,
            @PathVariable String userId) {

        try {
            CCTVInfoResponse response = connectionService.deleteCCTV(request, userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "장소 삭제 완료", response));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = "알 수 없는 에러";
            }

            ApiResponse<CCTVInfoResponse> apiResponse = new ApiResponse<>(false, errorMessage, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/{userId}/deleteAll")
    @ResponseBody
    public ResponseEntity<ApiResponse<CCTVInfoResponse>> deleteAllCCTV(
            @PathVariable String userId) {
        try {
            // userId 경로 포함 시 DeleteAllCCTVRequest 요청 필요 없음
            CCTVInfoResponse response = connectionService.deleteAllCCTV(userId);

            if (response.getCctvName().isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "전체 장소 삭제 완료", response));
            } else {
                ApiResponse<CCTVInfoResponse> apiResponse = new ApiResponse<>(false, "전체 장소 삭제 실패", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = "알 수 없는 에러";
            }

            ApiResponse<CCTVInfoResponse> apiResponse = new ApiResponse<>(false, errorMessage, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/{userId}/nameIsDuplicated")
    @ResponseBody
    public ResponseEntity<ApiResponse<DuplicationResponse>> getNameDuplication(
            @PathVariable String userId,
            @RequestParam String cctvName) {
        try {
            DuplicationResponse response = connectionService.getCCTVNameDuplication(userId, cctvName);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "CCTV 이름 중복 여부 (true: 중복됨, false: 중복 안됨)", response)
            );
        } catch (UserNotFoundException e) {
            // 존재하지 않는 사용자 정보일 시
            ApiResponse<DuplicationResponse> apiResponse = new ApiResponse<>(false, "사용자가 존재하지 않습니다.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @PutMapping("/{userId}/confirm")
    @ResponseBody
    public ResponseEntity<ApiResponse<EmergencyConfirmResponse>> confirmEmergency(
            @RequestBody EmergencyRequest request,
            @PathVariable String userId) {
        try {
            EmergencyConfirmResponse response = cctvService.confirmEmergency(request, userId);
            return ResponseEntity.ok(
                    new ApiResponse<EmergencyConfirmResponse>(
                            true,
                            "위험 상황 확인 완료",
                            response)
            );
        } catch (UserNotFoundException e) {
            ApiResponse<EmergencyConfirmResponse> apiResponse = new ApiResponse<>(
                    false,
                    "위험 상황 확인 실패",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
