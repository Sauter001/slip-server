package com.breakingadv.slipserver.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class BehaviorMonitoringService {
    // TODO: 모델의 영상 판독 service 구현
    @PostConstruct
    public void init() {
        new Thread(this::processMonitoring).start();
        try {
            getAIResult();
        } catch (IOException e) {
            System.out.println("Input problem");
        }
    }

    public void processMonitoring() {
        /* TODO: 최근 영상 하나를 가져온 후 getAIResult로 배회 판단
         * if 배회 -> 프론트에 전송
         * else -> 영상 삭제
         */
    }

    /**
     * @return {boolean} 가장 최근 영상을 AI 모델에 추가 후 결과 반환
     */
    public boolean getAIResult() throws IOException {
        // TODO: 가장 최근 영상을 AI 모델에 학습 후 결과 반환
        final String PATH = "slip-server/src/main/resources/";
        final String fname = "model.py";

        Process process = Runtime.getRuntime().exec("cd " + PATH + "; python " + fname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);

        return false;
    }
}
