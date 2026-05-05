package com.depositcorex.productconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DepositCoreXApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepositCoreXApplication.class, args);
		System.out.println("----------------------------------------------");
        System.out.println("DepositCoreX Module 2.4 Started Successfully!");
        System.out.println("Database: MySQL | Module: Product Configuration");
        System.out.println("----------------------------------------------");
	}

}
