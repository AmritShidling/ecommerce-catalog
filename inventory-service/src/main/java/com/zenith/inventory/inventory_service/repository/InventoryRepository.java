package com.zenith.inventory.inventory_service.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.zenith.inventory.inventory_service.entity.Inventory;
import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.config.validate.Validated;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(Collection<String> skuCodes);

    Optional<Inventory> getBySkuCode(String s);

    @Modifying
    @Transactional
    @Query("UPDATE Inventory i SET i.totalQuantity = i.totalQuantity - :requestedQuantity " +
            "WHERE i.skuCode = :skuCode AND i.totalQuantity >= :requestedQuantity")
    int decreaseStock(@Param("skuCode") String skuCode, @Param("requestedQuantity") Integer requestedQuantity);

    Optional<Inventory> findBySkuCode(String s);
}
