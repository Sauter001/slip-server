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

    }

    public void processMonitoring() {
        /* TODO: 최근 영상 하나를 가져온 후 getAIResult로 배회 판단
         * if 배회 -> 프론트에 전송
         * else -> 영상 삭제
         */
        try {
            boolean result = getAIResult();
            System.out.println(result);
            if (result) {
                System.out.println("배회 발생");
            } else {
                System.out.println("문제 없음");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Input problem");
        }
    }

    /**
     * @return {boolean} 가장 최근 영상을 AI 모델에 추가 후 결과 반환
     */
    public boolean getAIResult() throws IOException {
        // TODO: 가장 최근 영상을 AI 모델에 학습 후 결과 반환
        final String PATH = System.getProperty("user.dir") + "/slip-server/src/main/resources/";
        final String fname = "model.py";

        Process process = Runtime.getRuntime().exec("cmd /c cd " + PATH + "&& python " + fname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        StringBuilder wanderState = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            wanderState.append(line);
            wanderState.append("\n");
        }

        return !wanderState.isEmpty();
    }
}
