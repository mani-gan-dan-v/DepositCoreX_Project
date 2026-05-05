package com.depositcorex.Main.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.depositcorex.Main.Dto.*;
import com.depositcorex.Main.Entities.*;

import com.depositcorex.Main.Service.*;

import java.math.BigDecimal;




@RestController
@RequestMapping("/api/v1/servicing")
public class ServicingController {

    private final ServicingService servicingService;
    
    public ServicingController(ServicingService servicingService){
    	this.servicingService = servicingService;
    }

    @PostMapping("/holds/place")
    public ResponseEntity<HoldOrLien> placeHold(@RequestBody HoldRequest request) {
        HoldOrLien hold = servicingService.placeHold(
            request.getAccountId(), 
            request.getAmount(), 
            request.getReason(), 
            request.getType()
        );
        return ResponseEntity.ok(hold);
    }

    @PostMapping("/holds/release/{holdId}")
    public ResponseEntity<String> releaseHold(@PathVariable Long holdId) {
        servicingService.releaseHold(holdId);
        return ResponseEntity.ok("Hold released successfully");
    }

    @GetMapping("/accounts/{accountId}/available-balance")
    public ResponseEntity<BalanceResponse> getAvailableBalance(@PathVariable Long accountId) {
        BigDecimal available = servicingService.getAvailableBalance(accountId);
        return ResponseEntity.ok(new BalanceResponse(accountId, available));
    }

    @PostMapping("/si/create")
    public ResponseEntity<StandingInstruction> createSI(@RequestBody SIRequest request) {
        StandingInstruction si = servicingService.createStandingInstruction(
            request.getFromAccId(), 
            request.getToAccId(), 
            request.getAmount(), 
            request.getFrequency()
        );
        return ResponseEntity.ok(si);
    }
}