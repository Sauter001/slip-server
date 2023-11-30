package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.configuration.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bytedeco.javacv.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CCTVStreamingService {
    private static String PRIVATE_IP = "";
    private static String EXTERNAL_IP = "";
    private static String rtspURL = "";
    private static final String OUTPUT_DIR = "slip-server/src/user_video";
    ;
    private static final int RECORD_DURATION = 5000;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("addr_info.json");
            Config config = mapper.readValue(resource.getInputStream(), Config.class);

            PRIVATE_IP = config.getPrivateIp();
            EXTERNAL_IP = config.getExternalIp();
            rtspURL = config.getRtspUrl().replace("{ip}", PRIVATE_IP);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(this::getStreamingFrame).start();
        new Thread(this::maintainNumberOfVideos).start();
    }

    public void getStreamingFrame() {
        try (FFmpegFrameGrabber grabber = createFrameGrabber();
             Java2DFrameConverter converter = new Java2DFrameConverter();) {

            grabber.start();
            processStreaming(grabber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processStreaming(FFmpegFrameGrabber grabber) throws Exception {
        long startTime = System.currentTimeMillis();
        FFmpegFrameRecorder recorder = null;

        while (true) {
            // Get the video frame and show it on the CanvasFrame
            Frame frame = grabber.grab();
            if (frame == null) {
                System.out.println("화면이 송출되고 있지 않습니다.");
                break;
            }

            FFmpegLogCallback.set();
            if (recorder == null || (System.currentTimeMillis() - startTime) >= RECORD_DURATION) {
                if (recorder != null) {
                    closeRecorder(recorder);
                }
                recorder = createRecorder(grabber);
                startTime = System.currentTimeMillis();
            }

            recorder.record(frame);
        }
    }

    private FFmpegFrameRecorder checkAndResetRecorder(FFmpegFrameRecorder recorder, FFmpegFrameGrabber grabber, long startTime) throws Exception {
        if (recorder == null || (System.currentTimeMillis() - startTime) >= RECORD_DURATION) {
            closeRecorder(recorder);
            return createRecorder(grabber);
        }
        return recorder;
    }

    private FFmpegFrameRecorder createRecorder(FFmpegFrameGrabber grabber) throws Exception {
        String fileName = "video_" + System.currentTimeMillis() + ".mp4";

        // 디렉토리 없으면 생성
        File folder = new File(OUTPUT_DIR);
        if (!folder.exists()) {
            try {
                if (folder.mkdirs())
                    System.out.println("비디오 폴더 생성 완료");
                else
                    System.out.println("폴더 생성 안됨");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("폴더가 이미 존재합니다.");
        }

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(OUTPUT_DIR + File.separator + fileName, grabber.getImageWidth(), grabber.getImageHeight());
        FFmpegLogCallback.set();
        recorder.setAudioChannels(1);
        recorder.start();
        return recorder;
    }

    private void closeRecorder(FFmpegFrameRecorder recorder) throws Exception {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }
    }

    private FFmpegFrameGrabber createFrameGrabber() {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspURL);
        grabber.setOption("rtsp_transport", "tcp");
        FFmpegLogCallback.set();
        return grabber;
    }


    private void maintainNumberOfVideos() {
        final int MAX_NUM_OF_VIDEOS = 12;

        while (true) {
            File dir = new File(OUTPUT_DIR);
            File[] videos = dir.listFiles(((dir1, name) -> name.endsWith(".mp4")));

            if (videos != null && videos.length > MAX_NUM_OF_VIDEOS) {
                Arrays.sort(videos, (v1, v2) -> Long.compare(v1.lastModified(), v2.lastModified()));
                for (int i = 0; i < videos.length - MAX_NUM_OF_VIDEOS; ++i) {
                    try {
                        Files.delete(videos[i].toPath());
                        System.out.println("Deleted: " + videos[i].getName());
                    } catch (Exception e) {
                        System.err.println("Failed to delete " + videos[i].getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}
