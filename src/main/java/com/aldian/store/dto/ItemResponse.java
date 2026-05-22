package com.aldian.store.dto;

import java.util.List;
import java.util.UUID;

public record ItemResponse(
        UUID id,
        String name,
        String description,
        List<VariantResponse> variants
) {}
