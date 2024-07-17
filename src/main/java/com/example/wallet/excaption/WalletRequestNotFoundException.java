package com.example.wallet.excaption;

public class WalletRequestNotFoundException extends RuntimeException{

    public WalletRequestNotFoundException(String message) {
        super(message);
    }
}
