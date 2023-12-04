package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.model.response.DuplicationResponse;
import com.breakingadv.slipserver.repository.CCTVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CCTVService {
    // todo: CCTV에 대한 데이터베이스 처리 수행
    private final CCTVRepository cctvRepository;


    public DuplicationResponse getDuplication(String ip) {
        Optional<String> ips = cctvRepository.findByIp(ip);
        if (ips.isPresent()) {
            return new DuplicationResponse(true);
        } else {
            return new DuplicationResponse(false);
        }
    }
}
