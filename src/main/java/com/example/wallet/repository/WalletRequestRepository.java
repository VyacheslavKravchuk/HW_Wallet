package com.example.wallet.repository;

import com.example.wallet.entity.WalletRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRequestRepository extends JpaRepository<WalletRequest, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletRequest> findById(UUID uuid);
}
