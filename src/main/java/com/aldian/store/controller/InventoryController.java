package com.aldian.store.controller;

import com.aldian.store.dto.StockTransactionRequest;
import com.aldian.store.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/variants/{id}/stock/restock")
    @Operation(summary = "Restock a variant")
    public ResponseEntity<Void> restock(
            @PathVariable UUID id,
            @Valid @RequestBody StockTransactionRequest request) {
        inventoryService.restock(id, request.quantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/variants/{id}/stock/reserve")
    @Operation(summary = "Reserve stock(s)", description = "Reserve stocks for selling or other purposes")
    public ResponseEntity<Void> reserve(
            @PathVariable UUID id,
            @Valid @RequestBody StockTransactionRequest request) {
        try {
            inventoryService.reserveStock(id, request.quantity());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/variants/{id}/stock/remove")
    @Operation(summary = "Remove stock from inventory")
    public ResponseEntity<Void> remove(
            @PathVariable UUID id,
            @Valid @RequestBody StockTransactionRequest request) {
        try {
            inventoryService.removeStock(id, request.quantity());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}