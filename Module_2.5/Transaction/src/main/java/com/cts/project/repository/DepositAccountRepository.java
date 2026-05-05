package com.cts.project.repository;

import com.cts.project.entity.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {}
