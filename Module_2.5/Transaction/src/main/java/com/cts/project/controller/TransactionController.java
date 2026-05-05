package com.cts.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.TransactionRequestDTO;
import com.cts.project.dto.TransactionResponseDTO;
import com.cts.project.service.TransactionService;

@RestController   //Automatically converts Java object → JSON response
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping //Handles POST Request
    public ResponseEntity<TransactionResponseDTO> postTransaction(@RequestBody TransactionRequestDTO request) {
        return ResponseEntity.ok(transactionService.postTransaction(request));
    }

    @PostMapping("/{id}/reverse")
    public ResponseEntity<TransactionResponseDTO> reverseTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.reverseTransaction(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getByAccountId(accountId));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }
}
