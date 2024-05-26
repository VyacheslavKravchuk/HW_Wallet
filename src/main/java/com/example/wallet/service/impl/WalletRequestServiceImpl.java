package com.example.wallet.service.impl;

import com.example.wallet.entity.Operation;
import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletRequest;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletRequestRepository;
import com.example.wallet.service.WalletRequestService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WalletRequestServiceImpl implements WalletRequestService {

    private WalletRepository walletRepository;
    private WalletRequestRepository walletRequestRepository;

    public WalletRequestServiceImpl(WalletRepository walletRepository,
                                    WalletRequestRepository walletRequestRepository) {
        this.walletRepository = walletRepository;
        this.walletRequestRepository = walletRequestRepository;
    }

    @Override
    public WalletRequest operationInputAndOutput(String walletId,
                                                 String operationType, int amount) {
        Optional<WalletRegistered> walletRegistered = walletRepository
                .findById(UUID.fromString(walletId));

        if (walletRegistered.isPresent()) {
            int balanceCurrent = walletRegistered.get().getBalance();
            Operation operation = Operation.parse(operationType);
            WalletRequest walletRequest = new WalletRequest(operation, amount);

            if (operation == Operation.DEPOSIT) {
                int newBalance = balanceCurrent + amount;
                walletRegistered.get().setBalance(newBalance);
            } else if (operation == Operation.WITHDRAW) {
                int newBalance = balanceCurrent - amount;
                if (newBalance < 0) {
                    throw new IllegalArgumentWalletException("Недостаточно средств для снятия");
                }
                walletRegistered.get().setBalance(newBalance);
            } else {
                throw new IllegalArgumentWalletException("Неверно указан тип операции");
            }

            walletRepository.save(walletRegistered.get());
            return walletRequestRepository.save(walletRequest);
        } else {
            throw new WalletRegisteredNotFoundException("Пользователь с идентификатором " + walletId + " не найден");
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public int operationGetBalance(String id) {
        // Проверка валидности UUID
        try {
            UUID uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentWalletException("Не валидный идентификатор");
        }

        // Поиск пользователя по UUID
        Optional<WalletRegistered> walletRegistered = walletRepository.findById(UUID.fromString(id));
        if (walletRegistered.isPresent()) {
            return walletRegistered.get().getBalance();
        } else {
            throw new WalletRegisteredNotFoundException("Пользователь не найден");
        }
    }
}
