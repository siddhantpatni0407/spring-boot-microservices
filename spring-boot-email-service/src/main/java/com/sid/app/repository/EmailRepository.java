package com.sid.app.repository;

import com.sid.app.entity.EmailTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siddhant Patni
 */
@Repository
public interface EmailRepository extends JpaRepository<EmailTransaction, Long> {

}