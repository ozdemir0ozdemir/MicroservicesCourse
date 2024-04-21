package ozdemir0ozdemir.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ozdemir0ozdemir.inventoryservice.model.Inventory;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    private String skuCode;
    private Boolean isInStock;


    public static InventoryResponse fromInventory(Inventory inventory) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setSkuCode(inventory.getSkuCode());
        inventoryResponse.setIsInStock(inventory.getQuantity() > 0);
        return inventoryResponse;
    }
}
