package com.example.wallet.service;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletRegisteredRequest;

import java.util.Optional;

public interface WalletService {
    WalletRegistered createWallet(WalletRegistered walletRegistered);

    Optional<WalletRegistered> getWalletById(String walletId);

    int operationGetBalance(String id);

    boolean isValidEmail(String email);

    //WalletRegistered findByEmailWallet(WalletRegistered walletRegistered);

    WalletRegisteredRequest findByEmailWallet(String email, String password);
}