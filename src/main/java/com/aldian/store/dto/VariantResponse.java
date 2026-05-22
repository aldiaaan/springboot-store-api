package com.aldian.store.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VariantResponse(
        UUID id,
        String sku,
        String name,
        BigDecimal price,
        int stockOnHand,
        int stockAllocated,
        int availableStock
) {}
