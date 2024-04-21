package ozdemir0ozdemir.inventoryservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ozdemir0ozdemir.inventoryservice.dto.InventoryResponse;
import ozdemir0ozdemir.inventoryservice.model.Inventory;
import ozdemir0ozdemir.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return this.inventoryRepository.existsBySkuCode(skuCode);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> areInStock(List<String> skuCodes) {
        return this.inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(InventoryResponse::fromInventory)
                .toList();
    }



}
