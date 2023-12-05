package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.Connection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    @Query("select conn.cctvName from connections conn where conn.uid = ?1")
    List<String> findCctvNamesByUid(int uid);

    @Transactional
    @Modifying
    @Query("DELETE FROM connections c WHERE c.cctvName = :cctvName and c.uid = :uid")
    void deleteByCctvName(@Param("cctvName") String cctvName, @Param("uid") int uid);

    @Transactional
    @Modifying
    @Query("DELETE FROM connections c where c.uid = :uid")
    void deleteAllCCTV(@Param("uid") int uid);
}
