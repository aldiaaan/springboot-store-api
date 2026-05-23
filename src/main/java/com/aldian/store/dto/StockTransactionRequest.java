package com.aldian.store.dto;

import jakarta.validation.constraints.Min;

public record StockTransactionRequest(
        @Min(1) int quantity
) {}
