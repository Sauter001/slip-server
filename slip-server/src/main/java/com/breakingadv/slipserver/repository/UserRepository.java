package com.breakingadv.slipserver.repository;

import com.breakingadv.slipserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
