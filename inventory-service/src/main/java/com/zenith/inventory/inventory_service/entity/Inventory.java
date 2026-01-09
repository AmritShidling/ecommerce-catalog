package com.zenith.inventory.inventory_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer totalQuantity;
    private Integer reservedQuantity;

    @Version
    private Long version;
    public Integer getAvailableQuantity(){
        return totalQuantity - reservedQuantity;
    }
}
