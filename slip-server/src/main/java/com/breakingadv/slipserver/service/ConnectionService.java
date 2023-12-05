package com.breakingadv.slipserver.service;

import com.breakingadv.slipserver.exception.UserNotFoundException;
import com.breakingadv.slipserver.model.request.DeleteCCTVRequest;
import com.breakingadv.slipserver.model.response.CCTVInfoResponse;
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
}
