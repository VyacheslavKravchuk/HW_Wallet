package com.example.wallet.service_test;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterAndLoginWallet_ValidEmailAndPassword() {
        String email = "test@example.com";
        String password = "password";

        boolean result = walletService.createWallet(email, password);
        boolean result2 = walletService.findByEmailWallet(email, password);

        assertTrue(result);
        assertTrue(result2);
    }

    @Test
    public void testRegisterWallet_NotValidEmail() {
        String email = "111";
        String password = "password";

        WalletRegistered walletRegistered = new WalletRegistered(email, password);

        IllegalArgumentWalletException exception =
                assertThrows(IllegalArgumentWalletException.class,
                        () -> {walletService.createWallet(walletRegistered.getEmail(),
                                walletRegistered.getPassword());
                });
    }

    @Test
    public void testRegisterWallet_NotValidPassWordNull() {
        String email = "test@example.com";
        String passwordNull = null;

        WalletRegistered walletRegisteredNull = new WalletRegistered(email, passwordNull);

        IllegalArgumentWalletException exceptionNull =
                assertThrows(IllegalArgumentWalletException.class,
                        () -> {walletService.createWallet(walletRegisteredNull.getEmail(),
                                walletRegisteredNull.getPassword());
                        });
    }

    @Test
    public void testRegisterWallet_NotValidPassWordEmpty() {
        String email = "test@example.com";
        String passwordEmpty = "";

        WalletRegistered walletRegisteredEmpty = new WalletRegistered(email, passwordEmpty);

        IllegalArgumentWalletException exceptionNull =
                assertThrows(IllegalArgumentWalletException.class,
                        () -> {walletService.createWallet(walletRegisteredEmpty.getEmail(),
                                walletRegisteredEmpty.getPassword());
                        });
    }

    @Test
    public void testGetWalletById_Exists() {
        WalletRegistered walletRegistered = new WalletRegistered("test@example.com", "password");

        when(walletRepository.findById(walletRegistered.getWalletId())).thenReturn(Optional.of(walletRegistered));

        Optional<WalletRegistered> resultNew = walletRepository.findById(walletRegistered.getWalletId());
        String id = walletRegistered.getWalletId().toString();

        assertTrue(resultNew.isPresent());
        assertEquals(walletRegistered, resultNew.get());
        verify(walletRepository, times(1)).findById(UUID.fromString(id));
    }


}
