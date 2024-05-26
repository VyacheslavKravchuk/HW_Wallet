package com.example.wallet.excaption;

/**
 * Класс для обработки ошибок не корректных параметров
 */
public class IllegalArgumentWalletException extends RuntimeException{

    public IllegalArgumentWalletException(String message) {
        super(message);
    }
}
