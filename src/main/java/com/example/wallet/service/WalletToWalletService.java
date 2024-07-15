package com.example.wallet.service;

import com.example.wallet.entity.WalletToWalletTransaction;

import java.util.concurrent.CompletableFuture;

public interface WalletToWalletService {
    CompletableFuture<WalletToWalletTransaction> operationInputWalletToWallet(String id, WalletToWalletTransaction walletToWalletTransaction);

    WalletToWalletTransaction processTransaction(String walletId, WalletToWalletTransaction walletToWalletTransaction);
}
