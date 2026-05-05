package com.cts.project.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "txnid")
    private Long txnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid")
    private DepositAccount account;

    @Column(name = "txntype", length = 40)
    private String txnType;

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "narrative", length = 200)
    private String narrative;

    @Column(name = "channel", length = 40)
    private String channel;

    @Column(name = "txndate")
    private LocalDateTime txnDate;

    @Column(name = "balanceafter", precision = 19, scale = 4)
    private BigDecimal balanceAfter;

    @Column(name = "status", length = 30)
    private String status;

    public Transaction() {}

    public Transaction(Long txnId, DepositAccount account, String txnType, BigDecimal amount,
                       String narrative, String channel, LocalDateTime txnDate,
                       BigDecimal balanceAfter, String status) {
        this.txnId = txnId;
        this.account = account;
        this.txnType = txnType;
        this.amount = amount;
        this.narrative = narrative;
        this.channel = channel;
        this.txnDate = txnDate;
        this.balanceAfter = balanceAfter;
        this.status = status;
    }

    public Long getTxnId() { return txnId; }
    public void setTxnId(Long txnId) { this.txnId = txnId; }

    public DepositAccount getAccount() { return account; }
    public void setAccount(DepositAccount account) { this.account = account; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNarrative() { return narrative; }
    public void setNarrative(String narrative) { this.narrative = narrative; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public LocalDateTime getTxnDate() { return txnDate; }
    public void setTxnDate(LocalDateTime txnDate) { this.txnDate = txnDate; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static TransactionBuilder builder() { return new TransactionBuilder(); }

    public static class TransactionBuilder {
        private Long txnId;
        private DepositAccount account;
        private String txnType;
        private BigDecimal amount;
        private String narrative;
        private String channel;
        private LocalDateTime txnDate;
        private BigDecimal balanceAfter;
        private String status;

        public TransactionBuilder txnId(Long txnId) { this.txnId = txnId; return this; }
        public TransactionBuilder account(DepositAccount account) { this.account = account; return this; }
        public TransactionBuilder txnType(String txnType) { this.txnType = txnType; return this; }
        public TransactionBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public TransactionBuilder narrative(String narrative) { this.narrative = narrative; return this; }
        public TransactionBuilder channel(String channel) { this.channel = channel; return this; }
        public TransactionBuilder txnDate(LocalDateTime txnDate) { this.txnDate = txnDate; return this; }
        public TransactionBuilder balanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; return this; }
        public TransactionBuilder status(String status) { this.status = status; return this; }

        public Transaction build() {
            return new Transaction(txnId, account, txnType, amount, narrative, channel, txnDate, balanceAfter, status);
        }
    }
}
