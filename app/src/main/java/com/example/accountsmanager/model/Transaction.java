package com.example.accountsmanager.model;

public class Transaction {
    private String partyId;
    private String transactionId;
    private String transactionDate;
    private String transactionTime;
    private String transactionCreditAmount;
    private String transactionDebitAmount;
    private String transactionNote;

    public Transaction(String partyId, String transactionId, String transactionDate,String transactionTime, String transactionCreditAmount, String transactionDebitAmount, String transactionNote) {
        this.partyId = partyId;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.transactionCreditAmount = transactionCreditAmount;
        this.transactionDebitAmount = transactionDebitAmount;
        this.transactionNote = transactionNote;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionCreditAmount() {
        return transactionCreditAmount;
    }

    public void setTransactionCreditAmount(String transactionCreditAmount) {
        this.transactionCreditAmount = transactionCreditAmount;
    }

    public String getTransactionDebitAmount() {
        return transactionDebitAmount;
    }

    public void setTransactionDebitAmount(String transactionDebitAmount) {
        this.transactionDebitAmount = transactionDebitAmount;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }
}
