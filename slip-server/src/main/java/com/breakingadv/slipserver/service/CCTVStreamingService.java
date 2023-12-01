package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.configuration.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bytedeco.ffmpeg.global.avcodec;
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
    private static final int RECORD_DURATION = 5000; // 영상 녹화 단위

    @PostConstruct // 자동 실행하려면 주석 해제
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("addr_info.json");
            Config config = mapper.readValue(resource.getInputStream(), Config.class);

            PRIVATE_IP = config.getPrivateIp();
            EXTERNAL_IP = config.getExternalIp();
            // 외부에서 접속 시 PRIVATE_IP를 EXTERNAL_IP로 변경 필요
            rtspURL = config.getRtspUrl().replace("{ip}", EXTERNAL_IP);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(this::getStreamingFrame).start();
        new Thread(this::maintainNumberOfVideos).start();
    }

    /**
     * 연결된 CCTV로부터 프레임을 받아옴
     */
    public void getStreamingFrame() {
        try (FFmpegFrameGrabber grabber = createFrameGrabber();) {

            grabber.start();
            processStreaming(grabber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 스트리밍으로 받아오는 frame을 영상으로 변환한다.
     * @param grabber 프레임 수집할 grabber
     */
    private void processStreaming(FFmpegFrameGrabber grabber) throws Exception {
        long startTime = System.currentTimeMillis();
        FFmpegFrameRecorder recorder = null;


        System.out.println("스트리밍 중...");
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

    /**
     *
     * @param grabber 프레임의 정보를 받아올 grabber
     * @return {FFmpegFrameRecorder} 영상을 녹화할 recorder
     * @throws Exception
     */
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
        recorder.setAudioChannels(1);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        FFmpegLogCallback.set();
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

    /**
     * user_video 디렉토리의 영상 수가 MAX_NUM_OF_VIDEOS를 초과하면 가장 오래된 영상부터 삭제한다.
     * NOTE: 가장 최근의 영상은 기록 중인 상태라 완전히 녹화가 끝난 영상은 11번째 것을 이용해야 함
     */

    private void maintainNumberOfVideos() {
        final int MAX_NUM_OF_VIDEOS = 12; // 기본값 12

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
