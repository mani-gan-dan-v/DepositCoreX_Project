package com.depositcorex.Main.SIExecutionJob;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.depositcorex.Main.Entities.*;
import com.depositcorex.Main.Repository.*;
import com.depositcorex.Main.Service.*;
import com.depositcorex.Main.TransactionalServicce.MockTransactionService;

import java.time.LocalDate;
import java.util.List;

@Component
public class StandingInstructionJob {

    @Autowired 
    private StandingInstructionRepository siRepo;
    @Autowired 
    private ServicingService servicingService;
    @Autowired 
    private MockTransactionService transactionService;

    /**
     * Runs every day at midnight (00:00).
     * Cron format: "Second Minute Hour Day Month DayOfWeek"
     */
    @Scheduled(cron = "0 36 8 * * *") 
    public void processDailySIs() {
        System.out.println("Starting Batch Job: Standing Instruction Execution - " + LocalDate.now());

        // 1. Find all active SIs that are due today or in the past
        List<StandingInstruction> dueSIs = siRepo.findByStatusAndNextRunDateLessThanEqual("ACTIVE", LocalDate.now());

        for (StandingInstruction si : dueSIs) {
            try {
                executeSingleSI(si);
            } catch (Exception e) {
                // Log the failure and update SI status
                System.err.println("Failed to execute SI ID " + si.getSiId() + ": " + e.getMessage());
                si.setStatus("FAILED");
                siRepo.save(si);
            }
        }
    }

    private void executeSingleSI(StandingInstruction si) {
        // 2. Validate Source and Destination Account Status (Module 2.3 check)
        try{
        servicingService.validateAccountStatusForSI(si.getFromAccount());
        
        servicingService.validateAccountStatusForSI(si.getToAccount());

        // 3. Validate Funds Availability (Module 2.7 check including Holds/Liens)
        servicingService.validateFundAvailability(si.getFromAccount().getAccountId(), si.getAmount());

        // 4. Trigger Transaction (Module 2.5 Logic)
        // This is where you would call your TransactionService.transferFunds() method.
        transactionService.internalTransfer(si.getFromAccount(), si.getToAccount(), si.getAmount());

        // 5. Update Next Run Date based on frequency
        updateNextRunDate(si);
        siRepo.save(si);
        }
        catch(RuntimeException e) {
        	// Trigger Failure Alert
//            notificationService.sendAlert(
//                si.getFromAccount().getCustomerId(),
//                "SI_FAILURE",
//                "Your standing instruction to " + si.getToAccount().getAccountNumber() + 
//                " failed due to: " + e.getMessage()
//            );
//            
            throw e; // Rethrow to let the job handle status update to 'FAILED'
        }
    }

    private void updateNextRunDate(StandingInstruction si) {
        String freq = si.getFrequency().toUpperCase();

        switch (freq) {
            case "MONTHLY":
                si.setNextRunDate(si.getNextRunDate().plusMonths(1));
                break;
                
            case "WEEKLY":
                si.setNextRunDate(si.getNextRunDate().plusWeeks(1));
                break;
                
            case "DAILY":
                si.setNextRunDate(si.getNextRunDate().plusDays(1));
                break;
                
            case "ONCE":
            case "ONE_TIME":
                // Mark as completed so it's excluded from future batch runs
                si.setStatus("COMPLETED");
                break;
                
            default:
                // If frequency is unknown, fail it for safety
                si.setStatus("FAILED");
                System.err.println("Unknown frequency for SI ID: " + si.getSiId());
                break;
        }
    }
}