package com.example.wallet.controller;

import com.example.wallet.entity.WalletRequest;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.excaption.WalletRequestNotFoundException;
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
    public ResponseEntity<WalletRequest> operationInputAndOutput(@RequestParam("walletId") String id,
                                                                 @RequestBody WalletRequest walletRequest) {
        try {
            WalletRequest result = walletRequestService.operationInputAndOutput(id, walletRequest);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentWalletException
                 | WalletRegisteredNotFoundException
                 | WalletRequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
