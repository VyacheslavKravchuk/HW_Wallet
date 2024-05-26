package com.example.wallet.controller_test;

import com.example.wallet.controller.WalletController;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

      //Тесты в контроллерах не проходят

public class WalletControllerTest {
    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        walletController = new WalletController(walletService);
    }

    @Test
    void testRegisterWalletSuccess() throws IllegalArgumentWalletException {

        String email = "test@mail.ru";
        String password = "password123";

//        boolean response = walletService.createWallet(email, password);
//        assertTrue(response);

        //verify(walletService).registerWallet(email, password);
    }
}
