package com.sid.sb.jwt.app.repository;

import com.sid.sb.jwt.app.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByUserId(Long id);

}