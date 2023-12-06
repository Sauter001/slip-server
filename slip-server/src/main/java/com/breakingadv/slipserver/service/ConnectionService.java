package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.exception.UserNotFoundException;
import com.breakingadv.slipserver.model.request.DeleteCCTVRequest;
import com.breakingadv.slipserver.model.response.CCTVInfoResponse;
import com.breakingadv.slipserver.model.response.DuplicationResponse;
import com.breakingadv.slipserver.repository.ConnectionRepository;
import com.breakingadv.slipserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    public CCTVInfoResponse deleteCCTV(DeleteCCTVRequest request, String userId) throws Exception {
        List<String> places = request.getPlaceName();

        // 사용자의 등록된 CCTV 가져오기
        Optional<Integer> uid = userRepository.findUidById(userId);
        if (uid.isPresent()) {
            for (String place : places) {
                connectionRepository.deleteByCctvName(place, uid.get());
            }
            List<String> cctvNames = connectionRepository.findCctvNamesByUid(uid.get());
            return new CCTVInfoResponse(cctvNames);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    public CCTVInfoResponse deleteAllCCTV(String userId) throws Exception {
        // 사용자의 등록된 CCTV 가져오기
        Optional<Integer> uid = userRepository.findUidById(userId);
        if (uid.isPresent()) {
            connectionRepository.deleteAllCCTV(uid.get());
            List<String> cctvNames = connectionRepository.findCctvNamesByUid(uid.get());
            return new CCTVInfoResponse(cctvNames);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    public DuplicationResponse getCCTVNameDuplication(String userId, String cctvName) throws UserNotFoundException {
        // 사용자의 uid 가져오기
        Optional<Integer> uid = userRepository.findUidById(userId);
        if (uid.isPresent()) {
            List<String> cctvs = connectionRepository.findCctvNamesByUid(uid.get()); // 사용자의 cctv 목록 가져오기
            if (cctvs.contains(cctvName)) {
                return new DuplicationResponse(true);
            } else {
                return new DuplicationResponse(false);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}
