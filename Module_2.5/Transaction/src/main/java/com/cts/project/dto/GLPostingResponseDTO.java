package com.cts.project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GLPostingResponseDTO {
    private Long glPostingId;
    private Long txnId;
    private String glAccount;
    private String debitOrCredit;
    private BigDecimal amount;
    private LocalDateTime postedDate;

    public Long getGlPostingId() { return glPostingId; }
    public void setGlPostingId(Long glPostingId) { this.glPostingId = glPostingId; }

    public Long getTxnId() { return txnId; }
    public void setTxnId(Long txnId) { this.txnId = txnId; }

    public String getGlAccount() { return glAccount; }
    public void setGlAccount(String glAccount) { this.glAccount = glAccount; }

    public String getDebitOrCredit() { return debitOrCredit; }
    public void setDebitOrCredit(String debitOrCredit) { this.debitOrCredit = debitOrCredit; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }
}
