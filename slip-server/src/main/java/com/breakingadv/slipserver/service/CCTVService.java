package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.entity.CCTV;
import com.breakingadv.slipserver.entity.Connection;
import com.breakingadv.slipserver.exception.UserNotFoundException;
import com.breakingadv.slipserver.model.request.CCTVManageRequest;
import com.breakingadv.slipserver.model.request.EmergencyRequest;
import com.breakingadv.slipserver.model.response.CCTVInfoResponse;
import com.breakingadv.slipserver.model.response.DuplicationResponse;
import com.breakingadv.slipserver.model.response.EmergencyConfirmResponse;
import com.breakingadv.slipserver.repository.CCTVRepository;
import com.breakingadv.slipserver.repository.ConnectionRepository;
import com.breakingadv.slipserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CCTVService {
    // todo: CCTV에 대한 데이터베이스 처리 수행
    private final UserRepository userRepository;
    private final CCTVRepository cctvRepository;
    private final ConnectionRepository connectionRepository;


    public DuplicationResponse getDuplication(String ip) {
        Optional<String> ips = cctvRepository.findByIp(ip);
        if (ips.isPresent()) {
            return new DuplicationResponse(true);
        } else {
            return new DuplicationResponse(false);
        }
    }

    // 받은 cctv 정보를 cctv 테이블에 추가한다.
    public CCTVInfoResponse addCCTV(CCTVManageRequest request, String userId) {
        Optional<Integer> uid = userRepository.findUidById(userId); // 입력 받은 아이디의 uid를 가져온다.
        if (uid.isPresent()) {
            // CCTV 정보 등록
            CCTV cctv = CCTV.builder()
                    .ip(request.getIp())
                    .phoneNumber(request.getPhoneNumber())
                    .streamingUsername(request.getStreamingUsername())
                    .streamingPassword(request.getStreamingPassword())
                    .build();
            CCTV savedCCTV = cctvRepository.save(cctv);

            // CCTV 연결 추가
            Connection connection = new Connection();
            connection.setUid(uid.get());
            connection.setCctvName(request.getCctvName());
            connection.setIp(request.getIp());
            Connection savedConnection = connectionRepository.save(connection);

            // 사용자의 등록된 CCTV 가져오기
            List<String> cctvNames = connectionRepository.findCctvNamesByUid(uid.get());
            return new CCTVInfoResponse(cctvNames);

        } else {
            return new CCTVInfoResponse(List.of(new String[]{}));
        }
    }

    // 위험상황 확인 후 cctv emergency를 false 처리하는 함수
    public EmergencyConfirmResponse confirmEmergency(EmergencyRequest request, String userId) throws UserNotFoundException {
        Optional<Integer> uid = userRepository.findUidById(userId); // 입력 받은 아이디의 uid를 가져온다.
        if (uid.isPresent()) {
            // 사용자가 존재하는 경우
            List<String> ips = cctvRepository.findIpByUid(uid.get());
            if (!ips.isEmpty()) {
                // 사용자와 연결된 cctv에 있으면 false 처리
                for (String ip : ips) {
                    cctvRepository.updateEmergencyConfirmation(ip, request.isEmergency());
                }
            }
            return new EmergencyConfirmResponse(userId);
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}
