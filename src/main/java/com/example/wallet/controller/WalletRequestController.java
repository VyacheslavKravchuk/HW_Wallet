package com.example.wallet.controller;

import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.service.WalletRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
@Tag(name = "Интернет-кошелек", description = "Операции ввода-вывода средств")
public class WalletRequestController {
    WalletRequestService walletRequestService;
    public WalletRequestController(WalletRequestService walletRequestService) {
        this.walletRequestService = walletRequestService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> operationInputAndOutput(@RequestParam("wallet_id") String id,
                                                                 @RequestParam("operationType") String operationType,
                                                                 @RequestParam("amount") int amount) {
        try {
            walletRequestService.operationInputAndOutput(id, operationType, amount);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentWalletException | WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }
    }

    @GetMapping("/balance")
    ResponseEntity<String> operationGetBalance(@RequestParam("wallet_id") String id){
        try {
            walletRequestService.operationGetBalance(id);
            return ResponseEntity.ok().build();
        } catch (WalletRegisteredNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с данным идентификатором не найден");
        } catch (IllegalArgumentWalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса отсутствуют или имеют некорректный формат");
        }

    }
}
