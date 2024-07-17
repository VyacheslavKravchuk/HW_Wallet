package com.example.wallet.service.impl;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletToWalletTransaction;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.excaption.WalletToWalletRequestNotFoundException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletToWalletTransactionRepository;
import com.example.wallet.runnable.WalletToWalletTransactionRunnable;
import com.example.wallet.service.WalletToWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WalletToWalletServiceImpl implements WalletToWalletService {

    private final Logger logger = LoggerFactory.getLogger(WalletToWalletTransactionRunnable.class);
    private final WalletRepository walletRepository;
    private final WalletToWalletTransactionRepository walletToWalletTransactionRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public WalletToWalletServiceImpl(WalletRepository walletRepository,
                                     WalletToWalletTransactionRepository walletToWalletTransactionRepository) {
        this.walletRepository = walletRepository;
        this.walletToWalletTransactionRepository = walletToWalletTransactionRepository;
    }

    @Transactional(rollbackFor = {IllegalArgumentWalletException.class,
            WalletRegisteredNotFoundException.class,
            WalletToWalletRequestNotFoundException.class})
    @Override
    public CompletableFuture<WalletToWalletTransaction> operationInputWalletToWallet(String walletId,
                                                                                     WalletToWalletTransaction walletToWalletTransaction) {
        // Создаем CompletableFuture, который будет хранить результат
        CompletableFuture<WalletToWalletTransaction> transactionFuture = new CompletableFuture<>();

        // Создаем и запускаем WalletToWalletTransactionRunnable
        WalletToWalletTransactionRunnable runnable = new WalletToWalletTransactionRunnable(
                walletId, walletToWalletTransaction, this, transactionFuture);
        executor.execute(runnable);

        // Возвращаем CompletableFuture
        return transactionFuture;
    }

    // Метод для обработки транзакции в WalletToWalletTransactionRunnable
    @Transactional(rollbackFor = {IllegalArgumentWalletException.class,
            WalletRegisteredNotFoundException.class,
            WalletToWalletRequestNotFoundException.class})
    @Override
    public WalletToWalletTransaction processTransaction(String walletId,
                                                        WalletToWalletTransaction walletToWalletTransaction) {

        WalletRegistered walletRegisteredWithdraw = walletRepository.findById(UUID.fromString(walletId)).orElseThrow();
        WalletRegistered walletRegisteredDeposit = walletRepository.findByEmail(walletToWalletTransaction.getEmail()).orElseThrow();

        if (walletToWalletTransaction != null &&
                walletRegisteredWithdraw != null && walletRegisteredDeposit != null) {

            // Проверка баланса
            int balanceCurrentByWithdraw = walletRegisteredWithdraw.getBalance();
            int amount = walletToWalletTransaction.getAmount();
            int newBalanceAfterWithdraw = balanceCurrentByWithdraw - amount;
            if (newBalanceAfterWithdraw < 0) {
                logger.error("Ошибка при обработке транзакции");
                throw new IllegalArgumentWalletException("Недостаточно средств для снятия");
            }

            // Обновление баланса
            int newBalanceAfterDeposit = walletRegisteredDeposit.getBalance() + amount;
            walletRegisteredWithdraw.setBalance(newBalanceAfterWithdraw);
            walletRegisteredDeposit.setBalance(newBalanceAfterDeposit);

            // Сохранение транзакции
            walletToWalletTransaction.setWalletRegistered(walletRegisteredWithdraw);
            walletToWalletTransaction.setWalletRegistered(walletRegisteredDeposit);
            walletToWalletTransactionRepository.save(walletToWalletTransaction);

            return walletToWalletTransaction;
        } else {
            throw new WalletToWalletRequestNotFoundException("Пользователь с email "
                    + walletToWalletTransaction.getEmail() + " не найден");
        }
    }


    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
    }



}
