package com.example.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.deposit.entity.CustomerRef;

@Repository
public interface CustomerRefRepository extends JpaRepository<CustomerRef, Long>{

}
