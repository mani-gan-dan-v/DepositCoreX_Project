package com.example.deposit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.deposit.entity.TermDeposit;

@Repository
public interface TermDepositRepository extends JpaRepository<TermDeposit, Long> {
	 Optional<TermDeposit> findByAccount_AccountId(Long accountId);
}
