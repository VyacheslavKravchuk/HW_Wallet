package com.example.wallet.controller;


import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(name = "Интернет-кошелек", description = "Создание кошелька")
public class WalletController {

    private final WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @PostMapping("/register_wallet")
    public ResponseEntity<String> registerWallet(@Valid @RequestParam("email") String email,
                                                  @Valid @RequestParam("password") String password) {
        try {
            walletService.createWallet(email, password);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }
    }

    @PostMapping("/login_wallet")
    public ResponseEntity<String> loginWallet(@Valid @RequestParam("email") String email,
                                                 @Valid @RequestParam("password") String password) {
        try {
            walletService.findByEmailWallet(email, password);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }
    }


    @GetMapping("{wallet_id}")
    public ResponseEntity<String> getWalletById(@RequestParam("wallet_id") String walletId) {
        try {
            walletService.getWalletById(walletId);
        } catch (WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }
        return ResponseEntity.ok().build();
    }
}
