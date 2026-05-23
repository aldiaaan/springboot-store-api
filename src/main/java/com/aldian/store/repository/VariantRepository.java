package com.aldian.store.repository;

import com.aldian.store.domain.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface VariantRepository extends JpaRepository<Variant, UUID> {
}