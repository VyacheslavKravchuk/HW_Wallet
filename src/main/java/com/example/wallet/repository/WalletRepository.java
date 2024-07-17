package com.example.wallet.repository;

import com.example.wallet.entity.WalletRegistered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository<WalletRegistered, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletRegistered> findByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletRegistered> findById(UUID uuid);
}
