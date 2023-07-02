package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TempTransactionRepository extends JpaRepository<TempTransactionEntity, UUID> {
    @Query("SELECT t FROM TempTransactionEntity t WHERE t.uuid_transaction =:uuid_transaction")
    List<TempTransactionEntity> findByUUIDTransaction(@Param("uuid_transaction") UUID uuid_transaction);
}
