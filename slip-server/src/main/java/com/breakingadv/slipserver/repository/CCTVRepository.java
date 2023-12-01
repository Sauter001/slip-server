package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.CCTV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CCTVRepository  extends JpaRepository<CCTV, Integer> {
}
