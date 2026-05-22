package com.aldian.store.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateItemRequest(
        @NotBlank String name,
        String description
) {}
