package com.example.wallet.entity;

public class WalletRegisteredRequest {
    private String email;
    private String password;
    private int balance;

    public WalletRegisteredRequest(String email, String password, int balance) {
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "WalletRegisteredRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
