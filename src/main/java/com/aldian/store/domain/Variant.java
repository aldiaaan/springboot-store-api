package com.aldian.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "variants")
@Getter
@Setter
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_on_hand", nullable = false)
    private int stockOnHand = 0;

    @Column(name = "stock_allocated", nullable = false)
    private int stockAllocated = 0;

    @Transient
    public int getAvailableStock() {
        return this.stockOnHand - this.stockAllocated;
    }
}