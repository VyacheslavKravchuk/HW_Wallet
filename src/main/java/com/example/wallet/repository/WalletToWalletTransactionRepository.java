package com.example.wallet.repository;

import com.example.wallet.entity.WalletToWalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletToWalletTransactionRepository
        extends JpaRepository<WalletToWalletTransaction, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletToWalletTransaction> findById(UUID uuid);
}
