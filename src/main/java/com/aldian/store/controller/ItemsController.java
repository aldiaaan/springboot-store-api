package com.aldian.store.controller;

import com.aldian.store.dto.CreateItemRequest;
import com.aldian.store.dto.CreateVariantRequest;
import com.aldian.store.dto.ItemResponse;
import com.aldian.store.dto.VariantResponse;
import com.aldian.store.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Items")
public class ItemsController {

    private final ItemService itemService;

    @GetMapping("/items")
    @Operation(summary = "List all items")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PostMapping("/items")
    @Operation(summary = "Create new item")
    public ResponseEntity<ItemResponse> createItem(@Valid @RequestBody CreateItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(request));
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "Delete an item by given id (UUID)")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/items/{itemId}/variants")
    @Operation(summary = "Add a variant to an item")
    public ResponseEntity<VariantResponse> addVariant(
            @PathVariable UUID itemId,
            @Valid @RequestBody CreateVariantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addVariant(itemId, request));
    }
}
