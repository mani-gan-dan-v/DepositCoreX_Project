package com.example.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.deposit.entity.DepositAccount;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {

}
