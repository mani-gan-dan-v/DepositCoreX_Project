package com.depositcorex.Main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depositcorex.Main.Entities.DepositAccount;
import com.depositcorex.Main.Entities.StandingInstruction;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StandingInstructionRepository extends JpaRepository<StandingInstruction, Long> {

    // Find all active instructions that are due to run today or earlier
    List<StandingInstruction> findByStatusAndNextRunDateLessThanEqual(String status, java.time.LocalDate date);

    // Find all instructions belonging to a specific source account
    List<StandingInstruction> findByFromAccount(DepositAccount fromAccount);
}