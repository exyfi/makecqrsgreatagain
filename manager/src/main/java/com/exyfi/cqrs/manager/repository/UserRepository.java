package com.exyfi.cqrs.manager.repository;

import com.exyfi.cqrs.manager.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRepository extends JpaRepository<UserStatus, String> {
}
