package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select uid from users where id=?1")
    Optional<Integer> findUidById(String id);
}
