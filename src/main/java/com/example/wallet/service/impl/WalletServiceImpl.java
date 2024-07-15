package com.example.wallet.service.impl;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletRegisteredRequest;
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
    public WalletRegistered createWallet(WalletRegistered walletRegistered) {

        if (isValidEmail(walletRegistered.getEmail()) &&
                walletRegistered.getPassword()!=null &&
                !walletRegistered.getPassword().isEmpty()) {
            walletRepository.save(walletRegistered);
            return walletRegistered;
        } else {
            throw new IllegalArgumentWalletException("Не корректный формат данных");
        }
    }


    public WalletRegisteredRequest findByEmailWallet(String email, String password) {
        if (isValidEmail(email) && password != null && !password.isEmpty()) {
            Optional<WalletRegistered> existingWallet = walletRepository.findByEmail(email); // Ищем по email
            if (existingWallet.isPresent()) {

                WalletRegisteredRequest wallet = new WalletRegisteredRequest(email,
                        password, existingWallet.get().getBalance());
                if (wallet.getPassword().equals(password)) {
                    return wallet;
                } else {
                    throw new IllegalArgumentWalletException("Неверный пароль");
                }
            } else {
                throw new WalletRegisteredNotFoundException("Интернет-кошелек не зарегистрирован");
            }
        } else {
            throw new IllegalArgumentWalletException("Не корректный формат данных");
        }
    }


    @Override
    public Optional<WalletRegistered> getWalletById(String walletId) {
        if (walletRepository.findById(UUID.fromString(walletId)).isPresent()) {
            Optional<WalletRegistered> walletRegistered = walletRepository.findById(UUID.fromString(walletId));

            return walletRegistered;
        } else {
            throw new WalletRegisteredNotFoundException("Интернет-кошелек отсутствует");
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
            System.out.println(
                    "Пользователь с id: "+id+" имеет баланс: "
                            +walletRegistered.get().getBalance()
            );
            return walletRegistered.get().getBalance();
        } else {
            throw new WalletRegisteredNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}

