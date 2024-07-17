package com.example.wallet.runnable;

import com.example.wallet.entity.WalletToWalletTransaction;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.excaption.WalletToWalletRequestNotFoundException;
import com.example.wallet.service.WalletToWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class WalletToWalletTransactionRunnable implements Runnable {
    private final String walletId;
    private final WalletToWalletTransaction walletToWalletTransaction;
    private final WalletToWalletService walletToWalletService;
    private final CompletableFuture<WalletToWalletTransaction> transactionFuture;
    private final Logger logger = LoggerFactory.getLogger(WalletToWalletTransactionRunnable.class);

    public WalletToWalletTransactionRunnable(String walletId,
                                             WalletToWalletTransaction walletToWalletTransaction,
                                             WalletToWalletService walletToWalletService, CompletableFuture<WalletToWalletTransaction> transactionFuture) {
        this.walletId = walletId;
        this.walletToWalletTransaction = walletToWalletTransaction;
        this.walletToWalletService = walletToWalletService;
        this.transactionFuture = transactionFuture;
    }

    @Override
    public void run() {
        try {
            // Вызываем метод processTransaction для обработки транзакции
            WalletToWalletTransaction result = walletToWalletService.processTransaction(walletId, walletToWalletTransaction);

            // Устанавливаем результат в CompletableFuture
            transactionFuture.complete(result);
        } catch (IllegalArgumentWalletException
                 | WalletRegisteredNotFoundException
                 | WalletToWalletRequestNotFoundException e) {
            logger.error("Ошибка при обработке транзакции");
            // В случае ошибки, устанавливаем исключение в CompletableFuture
            transactionFuture.completeExceptionally(e);
        }
    }

}