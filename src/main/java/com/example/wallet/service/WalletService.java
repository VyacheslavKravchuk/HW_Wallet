package com.example.wallet.service;

import com.example.wallet.entity.WalletRegistered;

import java.util.Optional;

public interface WalletService {
    WalletRegistered createWallet(String email, String password);

    Optional<WalletRegistered> getWalletById(String walletId);

    int operationGetBalance(String id);

    boolean isValidEmail(String email);

    WalletRegistered findByEmailWallet(String email, String password);
}