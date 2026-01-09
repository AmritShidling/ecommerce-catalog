package com.zenith.inventory.inventory_service.repository;

import com.zenith.inventory.inventory_service.entity.Inventory;
import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.config.validate.Validated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(Collection<String> skuCodes);

    Optional<Inventory> getBySkuCode(String s);
}
