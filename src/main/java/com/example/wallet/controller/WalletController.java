package com.example.wallet.controller;


import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(name = "Интернет-кошелек", description = "Создание кошелька")
public class WalletController {


    private final WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @PostMapping("/register_wallet")
    public ResponseEntity<WalletRegistered> registerWallet(@RequestParam("email") String email,
                                                           @RequestParam("password") String password) {
        try {
            WalletRegistered walletRegistered = walletService.createWallet(email, password);
            return ResponseEntity.ok(walletRegistered);
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/login_wallet")
    public ResponseEntity<WalletRegistered> loginWallet(@RequestParam("email") String email,
                                                        @RequestParam("password") String password) {
        try {
            WalletRegistered walletRegistered = walletService.findByEmailWallet(email, password);
            return ResponseEntity.ok(walletRegistered);
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{wallet_id}")
    public ResponseEntity<Optional<WalletRegistered>> getWalletById(@RequestParam("wallet_id") String walletId) {
        try {
            Optional<WalletRegistered> walletRegistered = walletService.getWalletById(walletId);
            return ResponseEntity.ok(walletRegistered);
        } catch (WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/balance")
    ResponseEntity<String> operationGetBalance(@RequestParam("wallet_id") String id){
        try {
            Optional<WalletRegistered> walletRegistered = walletService.getWalletById(id);
            return ResponseEntity.ok(String.valueOf(walletRegistered));

        } catch (WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с данным идентификатором не найден");
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }

    }
}
