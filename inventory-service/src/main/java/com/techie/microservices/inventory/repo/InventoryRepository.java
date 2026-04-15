package com.techie.microservices.inventory.repo;

import com.techie.microservices.inventory.model.Inventory;
import com.techie.microservices.inventory.model.InventoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
           select new com.techie.microservices.inventory.model.InventoryResponse(
              i.skuCode,
              i.availableStock,
              i.reservedStock
           )
           from Inventory i
           where i.skuCode = :skuCode
           """)
    Optional<InventoryResponse> findBySkuCode(String skuCode);

    @Modifying
    @Query(value = """
                        UPDATE t_inventory
                        SET available_stock = available_stock - :qty
                        WHERE sku_code = :skuCode
                          AND available_stock >= :qty
                    """, nativeQuery = true)
    int deductStock(String skuCode, int qty);

}
