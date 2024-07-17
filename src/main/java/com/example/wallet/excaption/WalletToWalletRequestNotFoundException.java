package com.example.wallet.excaption;

public class WalletToWalletRequestNotFoundException
        extends RuntimeException{

    public WalletToWalletRequestNotFoundException(String message) {
        super(message);
    }
}

