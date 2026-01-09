package com.zenith.order.order_service.repository;

import com.zenith.order.order_service.entity.OrderEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <OrderEntity, Long>{
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
