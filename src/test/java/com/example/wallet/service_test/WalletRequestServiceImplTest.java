package com.example.wallet.service_test;

import com.example.wallet.entity.WalletRegistered;
import com.example.wallet.entity.WalletRequest;
import com.example.wallet.excaption.IllegalArgumentWalletException;
import com.example.wallet.excaption.WalletRegisteredNotFoundException;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletRequestRepository;
import com.example.wallet.service.impl.WalletRequestServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletRequestServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletRequestRepository walletRequestRepository;

    @InjectMocks
    private WalletRequestServiceImpl walletRequestService;
    @InjectMocks
    private WalletServiceImpl walletService;
    private WalletRequest walletRequest;
    private WalletRegistered walletRegistered;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        walletRegistered = new WalletRegistered("test@example.com", "password");
        walletRegistered.setWalletId(UUID.randomUUID());
        walletRegistered.setBalance(100);
    }

    @Test
    public void testDepositOperation() {

        when(walletRepository.findById(walletRegistered.getWalletId()))
                .thenReturn(Optional.of(walletRegistered));

                WalletRequest result = walletRequestService
                        .operationInputAndOutput(walletRegistered
                .getWalletId().toString(), "DEPOSIT", 50);

        assertEquals(150, walletRegistered.getBalance());
        verify(walletRepository).save(walletRegistered);
        verify(walletRequestRepository).save(any(WalletRequest.class));
    }

    @Test
    public void testWithdrawOperation() {
        when(walletRepository.findById(walletRegistered.getWalletId()))
                .thenReturn(Optional.of(walletRegistered));

        WalletRequest result = walletRequestService
                .operationInputAndOutput(walletRegistered
                        .getWalletId().toString(), "WITHDRAW", 50);

        assertEquals(50, walletRegistered.getBalance());
        verify(walletRepository).save(walletRegistered);
        verify(walletRequestRepository).save(any(WalletRequest.class));
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        when(walletRepository.findById(walletRegistered.getWalletId()))
                .thenReturn(Optional.of(walletRegistered));

        IllegalArgumentWalletException exception =
                assertThrows(IllegalArgumentWalletException.class, () -> {
                    walletRequestService.operationInputAndOutput(walletRegistered
                            .getWalletId().toString(), "WITHDRAW", 200);
                });

    }

    @Test
    public void testInvalidOperationType() {
        when(walletRepository.findById(walletRegistered.getWalletId())).thenReturn(Optional.of(walletRegistered));

        IllegalArgumentWalletException exception = assertThrows(IllegalArgumentWalletException.class, () -> {
            walletRequestService.operationInputAndOutput(walletRegistered.getWalletId().toString(), "INVALID_OP", 50);
        });

        assertEquals("Неверно указан тип операции", exception.getMessage());
        verify(walletRepository, never()).save(any(WalletRegistered.class));
    }

    @Test
    public void testWalletNotFound() {
        when(walletRepository.findById(walletRegistered.getWalletId())).thenReturn(Optional.empty());

        WalletRegisteredNotFoundException exception = assertThrows(WalletRegisteredNotFoundException.class, () -> {
            walletRequestService.operationInputAndOutput(walletRegistered.getWalletId().toString(), "DEPOSIT", 50);
        });

        verify(walletRepository, never()).save(any(WalletRegistered.class));
    }

    @Test
    public void operationGetBalanceTest() {
        when(walletRepository.findById(UUID.fromString(walletRegistered
                .getWalletId().toString()))).thenReturn(Optional.of(walletRegistered));

        int balance = walletRequestService.operationGetBalance(walletRegistered
                .getWalletId().toString());

        assertEquals(100, balance);
    }

    @Test
    public void testOperationGetBalance_WalletNotFound() {
        when(walletRepository.findById(walletRegistered.getWalletId())).thenReturn(Optional.empty());

        assertThrows(WalletRegisteredNotFoundException.class, () -> walletRequestService
                .operationGetBalance(walletRegistered
                .getWalletId().toString()));
    }

    @Test
    public void testOperationGetBalance_InvalidUUID() {

        String invalidId = "invalid-uuid";

        assertThrows(IllegalArgumentWalletException.class, () -> walletRequestService
                .operationGetBalance(invalidId));
    }


}
