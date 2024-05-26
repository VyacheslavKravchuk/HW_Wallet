package com.example.wallet.service;

import com.example.wallet.entity.WalletRegistered;

import java.util.Optional;

public interface WalletService {
    boolean createWallet(String email, String password);

    Optional<WalletRegistered> getWalletById(String walletId);

    boolean isValidEmail(String email);

    boolean findByEmailWallet(String email, String password);
}
