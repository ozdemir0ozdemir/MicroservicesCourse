package ozdemir0ozdemir.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.inventoryservice.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsBySkuCode(String skuCode);

    Optional<Inventory> findBySkuCode(String skuCode);
    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
