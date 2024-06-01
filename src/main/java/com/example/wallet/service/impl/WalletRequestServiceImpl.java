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

            WalletRequest walletRequest = new WalletRequest(operation, amount);
            walletRepository.save(walletRegistered.get());
            walletRequestRepository.save(walletRequest);
            return walletRequest;
        } else {
            throw new WalletRegisteredNotFoundException("Пользователь с идентификатором " + walletId + " не найден");
        }
    }

}
