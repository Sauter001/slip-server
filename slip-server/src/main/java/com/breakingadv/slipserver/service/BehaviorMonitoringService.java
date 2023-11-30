package com.breakingadv.slipserver.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BehaviorMonitoringService {
    // TODO: 모델의 영상 판독 service 구현
    @PostConstruct
    public void init() {
        new Thread(this::processMonitoring).start();
    }

    public void processMonitoring() {

    }

    /**
     *
     * @return {boolean} 가장 최근 영상을 AI 모델에 추가 후 결과 반환
     */
    public boolean getAIResult() {
        // TODO: 가장 최근 영상을 AI 모델에 추가 후 결과 반환
        return false;
    }
}
