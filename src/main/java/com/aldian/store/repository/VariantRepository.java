package com.aldian.store.repository;

import com.aldian.store.domain.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface VariantRepository extends JpaRepository<Variant, UUID> {
    @Modifying
    @Query("UPDATE Variant v SET v.stockAllocated = v.stockAllocated + :quantity WHERE v.id = :id AND (v.stockOnHand - v.stockAllocated) >= :quantity")
    int reserveStock(@Param("id") UUID id, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE Variant v SET v.stockOnHand = v.stockOnHand - :quantity WHERE v.id = :id AND (v.stockOnHand - v.stockAllocated) >= :quantity")
    int removeStock(@Param("id") UUID id, @Param("quantity") int quantity);
}