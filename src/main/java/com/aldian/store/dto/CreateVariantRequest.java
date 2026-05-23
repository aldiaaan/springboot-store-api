package com.aldian.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateVariantRequest(
    @NotBlank String sku,
    @NotBlank String name,
    @NotNull @Min(0) BigDecimal price,
    @Min(0) int initialStock
) {}
