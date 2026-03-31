package com.depositcore.repository;

import com.depositcore.entity.InterestPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestPostingRepository extends JpaRepository<InterestPosting, Long> {
    boolean existsByAccountId(Long accountId);
}