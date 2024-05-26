package com.example.wallet.entity;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Класс для сущности зарегестрированных кошельков
 */
@Entity
@Table(name = "wallet_registered")
public class WalletRegistered {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id", updatable = false, nullable = false)
    private UUID walletId = UUID.randomUUID();

    @Column(name = "balance")
    private int balance;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public WalletRegistered(String email, String password) {
        this.balance = 0;
        this.email = email;
        this.password = password;
    }

    public WalletRegistered() {
    }



    public UUID getWalletId() {
        return walletId;
    }


    public int getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "WalletRegistered{" +
                "id=" + walletId +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
