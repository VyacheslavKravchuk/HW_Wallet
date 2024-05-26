package com.example.wallet.service.impl;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class WalletServiceImpl implements WalletService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public boolean createWallet(String email, String password) {
        if (isValidEmail(email) && password!=null && !password.isEmpty()) {
            WalletRegistered walletRegistered = new WalletRegistered(email, password);
            walletRepository.save(walletRegistered);
            return true;
        } else {
            throw new IllegalArgumentWalletException("Не корректный формат данных");
        }
    }

    @Override
    public boolean findByEmailWallet(String email, String password) {
        if (isValidEmail(email) && password!=null && !password.isEmpty()) {
            WalletRegistered walletRegistered = new WalletRegistered(email, password);
            walletRepository.findById(walletRegistered.getWalletId());
            return true;
        } else {
            throw new IllegalArgumentWalletException("Не корректный формат данных");
        }
    }
    @Override
    public Optional<WalletRegistered> getWalletById(String walletId) {
        if (walletRepository.findById(UUID.fromString(walletId)).isPresent()) {
            return walletRepository.findById(UUID.fromString(walletId));
        } else {
            throw new WalletRegisteredNotFoundException("Интернет-кошелек отсутствует");
        }
    }

    @Override
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
