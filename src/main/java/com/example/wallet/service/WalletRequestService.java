package com.example.wallet.service;

import com.example.wallet.entity.WalletRequest;

public interface WalletRequestService {

    WalletRequest operationInputAndOutput(String walletId, WalletRequest walletRequest);

}
