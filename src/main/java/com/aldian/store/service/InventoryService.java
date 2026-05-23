package com.aldian.store.service;

import com.aldian.store.domain.Variant;
import com.aldian.store.exception.InsufficientStockException;
import com.aldian.store.exception.VariantNotFoundException;
import com.aldian.store.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final VariantRepository variantRepository;

    @Transactional
    public void restock(UUID variantId, int quantity) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));

        variant.setStockOnHand(variant.getStockOnHand() + quantity);
        variantRepository.save(variant);
    }

    @Transactional
    public void reserveStock(UUID variantId, int quantity) {
        int updatedRows = variantRepository.reserveStock(variantId, quantity);

        if (updatedRows == 0) {
            throw new InsufficientStockException("Insufficient available stock to reserve " + quantity + " items");
        }
    }

    @Transactional
    public void removeStock(UUID variantId, int quantity) {
        int updatedRows = variantRepository.removeStock(variantId, quantity);

        if (updatedRows == 0) {
            throw new InsufficientStockException("Cannot remove " + quantity + " items. Not enough unallocated stock available");
        }
    }
}