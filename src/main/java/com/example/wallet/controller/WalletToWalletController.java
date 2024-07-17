package com.example.wallet.controller;

import com.example.wallet.entity.WalletToWalletTransaction;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.service.WalletToWalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet_to_wallet_requests")
@Tag(name = "Интернет-кошелек", description = "Операции " +
        "перевода средств из кошелька одного пользователя - " +
        "кошельку другого пользователя")
public class WalletToWalletController {

    WalletToWalletService walletToWalletService;

    public WalletToWalletController(WalletToWalletService walletToWalletService) {
        this.walletToWalletService = walletToWalletService;
    }

    @PostMapping("/wallet_to_wallet_transaction")
    public ResponseEntity<WalletToWalletTransaction> operationInputWalletToWallet(@RequestParam("walletId") String id,
                                                                                  @RequestBody WalletToWalletTransaction walletToWalletTransaction) {
        try {
            WalletToWalletTransaction result = walletToWalletService.processTransaction(id, walletToWalletTransaction);
            return ResponseEntity.ok(result);
        } catch (WalletRegisteredNotFoundException
                 | IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
