package com.example.wallet.entity;


import javax.persistence.*;
import java.util.UUID;

/**
 * Класс для сущности транзакций внесения средств
 * любого пользователя любому кошельку
 */
@Entity
@Table(name = "wallet_to_wallet_transaction")
public class WalletToWalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_to_wallet_transaction_id", updatable = false, nullable = false)
    private UUID walletToWalletTransactionId;
    @Column(name = "email")
    private String email;
    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
    private WalletRegistered walletRegistered;

    public WalletToWalletTransaction(String email, int amount) {
        this.email = email;
        this.amount = amount;
    }

    public WalletToWalletTransaction() {
    }

    public UUID getWalletToWalletTransactionId() {
        return walletToWalletTransactionId;
    }

    public String getEmail() {
        return email;
    }

    public int getAmount() {
        return amount;
    }

    public WalletRegistered getWalletRegistered() {
        return walletRegistered;
    }

    public void setWalletToWalletTransactionId(UUID walletToWalletTransactionId) {
        this.walletToWalletTransactionId = walletToWalletTransactionId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setWalletRegistered(WalletRegistered walletRegistered) {
        this.walletRegistered = walletRegistered;
    }

    @Override
    public String toString() {
        return "WalletToWalletTransaction{" +
                "email='" + email + '\'' +
                ", amount=" + amount +
                '}';
    }
}
