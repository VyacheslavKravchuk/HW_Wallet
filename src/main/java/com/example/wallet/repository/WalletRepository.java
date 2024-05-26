package com.example.wallet.repository;

import com.example.wallet.entity.WalletRegistered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository<WalletRegistered, UUID> {


}
