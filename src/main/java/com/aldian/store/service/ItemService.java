package com.aldian.store.service;

import com.aldian.store.domain.Item;
import com.aldian.store.domain.Variant;
import com.aldian.store.dto.CreateItemRequest;
import com.aldian.store.dto.CreateVariantRequest;
import com.aldian.store.dto.ItemResponse;
import com.aldian.store.dto.VariantResponse;
import com.aldian.store.exception.ItemNotFoundException;
import com.aldian.store.repository.ItemRepository;
import com.aldian.store.repository.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final VariantRepository variantRepository;

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    private ItemResponse mapToItemResponse(Item item) {
        List<VariantResponse> variants = item.getVariants().stream()
                .map(this::mapToVariantResponse)
                .collect(Collectors.toList());
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), variants);
    }

    private VariantResponse mapToVariantResponse(Variant variant) {
        return new VariantResponse(
                variant.getId(), variant.getSku(), variant.getName(),
                variant.getPrice(), variant.getStockOnHand(), variant.getStockAllocated(),
                variant.getAvailableStock()
        );
    }

    @Transactional
    public ItemResponse createItem(CreateItemRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setDescription(request.description());

        Item savedItem = itemRepository.save(item);
        return mapToItemResponse(savedItem);
    }

    @Transactional
    public void deleteItem(UUID itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException(itemId);
        }
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public VariantResponse addVariant(UUID itemId, CreateVariantRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        Variant variant = new Variant();
        variant.setSku(request.sku());
        variant.setName(request.name());
        variant.setPrice(request.price());
        variant.setStockOnHand(request.initialStock());
        variant.setStockAllocated(0);
        variant.setItem(item);

        Variant savedVariant = variantRepository.save(variant);
        return mapToVariantResponse(savedVariant);
    }
}
