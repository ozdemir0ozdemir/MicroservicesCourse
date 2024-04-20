package ozdemir0ozdemir.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ozdemir0ozdemir.inventoryservice.model.Inventory;
import ozdemir0ozdemir.inventoryservice.repository.InventoryRepository;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApp {
    public static void main( String[] args ) {
        SpringApplication.run(InventoryServiceApp.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iphone_6_code");
            inventory1.setQuantity(264);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iphone_7_code");
            inventory2.setQuantity(0);

            repository.saveAll(List.of(inventory1, inventory2));
        };
    }


}
