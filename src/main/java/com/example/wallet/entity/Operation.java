package com.example.wallet.entity;

import org.springframework.lang.Nullable;

public enum Operation {

    /**
     *  operationType: DEPOSIT or WITHDRAW
     */
    DEPOSIT,
    WITHDRAW;


    @Nullable
    public static Operation parse (String operation) {
        for (Operation o : values()) {
            if (o.name().equals(operation)) {
                return o;
            }
        }
        return null;
    }
}
