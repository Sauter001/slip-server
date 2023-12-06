package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.CCTV;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CCTVRepository extends JpaRepository<CCTV, Integer> {

    @Query("select c.ip from cctv c where c.ip = ?1")
    Optional<String> findByIp(String ip);

    @Query("select cc.ip from connections conn join cctv cc on (cc.ip = conn.ip) where conn.uid = ?1")
    List<String> findIpByUid(int uid);

    @Transactional
    @Modifying
    @Query("update cctv set emergency=:state where ip=:ip")
    void updateEmergencyConfirmation(@Param("ip") String ip, @Param("state") boolean state);
}
