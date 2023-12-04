package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.model.response.DuplicationResponse;
import com.breakingadv.slipserver.repository.CCTVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CCTVService {
    // todo: CCTV에 대한 데이터베이스 처리 수행
    private final CCTVRepository cctvRepository;


    public DuplicationResponse getDuplication(String ip) {
        boolean isDuplicated = cctvRepository.existsByIp(ip);
        return new DuplicationResponse(isDuplicated);
    }

    public boolean existsByIp(String ip) {
        return false;
    }
}
