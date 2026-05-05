package com.depositcorex.Main.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.depositcorex.Main.Entities.DepositAccount;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<DepositAccount, Long> {
    
    // Find account by its unique account number (e.g., for UI searches)
    Optional<DepositAccount> findByAccountNumber(String accountNumber);
}