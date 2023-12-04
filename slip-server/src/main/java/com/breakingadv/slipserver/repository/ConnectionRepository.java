package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    @Query("select conn.cctvName from connections conn where conn.uid = ?1")
    List<String> findCctvNamesByUid(int uid);

}
