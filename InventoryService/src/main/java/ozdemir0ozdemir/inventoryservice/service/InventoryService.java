package ozdemir0ozdemir.inventoryservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ozdemir0ozdemir.inventoryservice.dto.InventoryResponse;
import ozdemir0ozdemir.inventoryservice.model.Inventory;
import ozdemir0ozdemir.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return this.inventoryRepository.existsBySkuCode(skuCode);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> areInStock(List<String> skuCodes) {
        log.info("Wait started");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Wait finished");
        return this.inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(InventoryResponse::fromInventory)
                .toList();
    }



}
