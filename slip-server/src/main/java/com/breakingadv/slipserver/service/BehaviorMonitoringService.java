package com.breakingadv.slipserver.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class BehaviorMonitoringService {
    // TODO: 모델의 영상 판독 service 구현
    private static final String OUTPUT_DIR = "slip-server/src/user_video";

    @PostConstruct
    public void init() {
        new Thread(this::processMonitoring).start();
    }

    public void processMonitoring() {
        /* TODO: 최근 영상 하나를 가져온 후 getAIResult로 배회 판단
         * if 배회 -> 프론트에 전송
         * else -> 영상 삭제
         */
        while (true) {
            try {
                String result = getAIResult();
                if (!result.equals("")) {
                    System.out.println(result);
                } else {
                    System.out.println("문제 없음");
                }
                Thread.sleep(5000);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Input problem");
            } catch (InterruptedException intException) {
                intException.printStackTrace();
            }
        }
    }

    /**
     * @return {boolean} 가장 최근 영상을 AI 모델에 추가 후 결과 반환
     */
    public String getAIResult() throws IOException {
        // TODO: 가장 최근 영상을 AI 모델에 학습 후 결과 반환
        final String PATH = System.getProperty("user.dir") + "/slip-server/src/main/resources/";
        final String modelName = "model.py";

        File dir = new File(OUTPUT_DIR);
        File[] videos = dir.listFiles(((dir1, name) -> name.endsWith(".mp4")));

        if (videos == null) return null;

        Arrays.sort(videos, (v1, v2) -> (-1) * Long.compare(v1.lastModified(), v2.lastModified()));
        String recentVideo = videos[1].getName(); // 가장 최근 영상은 녹화 상태임
        System.out.println("recent video is: " + recentVideo);

        String command = "cmd /c cd " + PATH + "&& echo " + recentVideo + " | python " + modelName;
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        StringBuilder wanderState = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            wanderState.append(line);
            wanderState.append("\n");
        }

//        // 배회가 아니면 삭제
//        if (wanderState.toString().equals("")) {
//            Path videoPath = videos[1].toPath();
//            Files.delete(videoPath);
//            System.out.println("Delete recent file: " + videoPath);
//        }

        return wanderState.toString();
    }
}
