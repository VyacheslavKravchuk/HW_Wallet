package com.example.wallet.entity;

//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.UUID;
/**
 * Класс для сущности транзакций
 */
@Entity
@Table(name = "wallet_request")
public class WalletRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private UUID transactionId;
    @Column(name = "operation_type")
    private Operation operationType;
    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletRegistered walletRegistered;

    public WalletRequest(Operation operationType, int amount) {
        this.transactionId = getTransactionId();
        this.operationType = operationType;
        this.amount = amount;
    }

    public WalletRequest() {
    }

    public WalletRegistered getWalletRegistered() {
        return walletRegistered;
    }

    public void setWalletRegistered(WalletRegistered walletRegistered) {
        this.walletRegistered = walletRegistered;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "WalletRequest{" +
                "transactionId=" + transactionId +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }
}
