package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
}
