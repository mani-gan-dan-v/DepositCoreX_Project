package com.depositcore.repository;

import com.depositcore.entity.InterestAccrual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestAccrualRepository extends JpaRepository<InterestAccrual, Long> {

    InterestAccrual findTopByAccountIdOrderByCalculatedDateDesc(Long accountId);
}