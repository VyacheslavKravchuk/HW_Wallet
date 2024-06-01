package com.example.wallet.controller;

import com.example.wallet.entity.WalletRequest;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.service.WalletRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet_requests")
@Tag(name = "Интернет-кошелек", description = "Операции ввода-вывода средств")
public class WalletRequestController {
    WalletRequestService walletRequestService;
    public WalletRequestController(WalletRequestService walletRequestService) {
        this.walletRequestService = walletRequestService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<WalletRequest> operationInputAndOutput(@RequestParam("wallet_id") String id,
                                                                 @RequestParam("operationType") String operationType,
                                                                 @RequestParam("amount") int amount) {
        try {
            WalletRequest walletRequest = walletRequestService.operationInputAndOutput(id, operationType, amount);
            return ResponseEntity.ok(walletRequest);
        } catch (IllegalArgumentWalletException | WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
