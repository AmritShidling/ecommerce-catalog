package com.zenith.order.order_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long OrderId;
    private Integer quantity;
    private Double price;
}
