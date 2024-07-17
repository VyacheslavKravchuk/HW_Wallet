package com.example.wallet.service.impl;

import com.example.wallet.entity.Operation;
import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletRequest;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.excaption.WalletRequestNotFoundException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletRequestRepository;
import com.example.wallet.service.WalletRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional  //(isolation = Isolation.READ_COMMITTED)
    @Override
    public WalletRequest operationInputAndOutput(String walletId, WalletRequest walletRequest) {
        Optional<WalletRegistered> walletRegistered = walletRepository
                .findById(UUID.fromString(walletId));

        if (walletRequest!=null && walletRegistered.isPresent()) {
            int balanceCurrent = walletRegistered.get().getBalance();
            Operation operation = walletRequest.getOperationType();
            int amount = walletRequest.getAmount();

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

            walletRequest.setWalletRegistered(walletRegistered.get());
            walletRequestRepository.save(walletRequest);
            return walletRequest;
        } else if (walletRequest == null) {
            throw new WalletRequestNotFoundException("Запрос отсутствует");

        }
        else {
            throw new WalletRegisteredNotFoundException("Пользователь с идентификатором "
                    + walletId + " не найден");
        }
    }

}

