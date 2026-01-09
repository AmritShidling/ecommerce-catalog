package com.zenith.order.order_service.entity;

import com.zenith.order.order_service.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private Long customerId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected  void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        this.status = Status.PENDING;
    }
}
