package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.CCTV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CCTVRepository  extends JpaRepository<CCTV, Integer> {

    @Query("select c.ip from cctv c where c.ip = ?1")
    Optional<String> findByIp(String ip);
}
