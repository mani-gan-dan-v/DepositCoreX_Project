package com.cts.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.GLPostingResponseDTO;
import com.cts.project.service.GLPostingService;

@RestController    //return JSON
@RequestMapping("/api/gl-postings")
public class GLPostingController {

    @Autowired
    private GLPostingService glPostingService;

    @GetMapping("/transaction/{txnId}")
    public ResponseEntity<List<GLPostingResponseDTO>> getByTxnId(@PathVariable Long txnId) {
        return ResponseEntity.ok(glPostingService.getByTxnId(txnId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GLPostingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(glPostingService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GLPostingResponseDTO>> getAll() {
        return ResponseEntity.ok(glPostingService.getAll());
    }
}
