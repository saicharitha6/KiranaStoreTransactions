package com.example.kirana_store.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kirana_store.model.KiranaStore;

@Repository
public interface KiranaStoreRepository extends JpaRepository<KiranaStore, Long> {
    List<KiranaStore> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}

