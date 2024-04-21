package ozdemir0ozdemir.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ozdemir0ozdemir.orderservice.dto.InventoryResponse;
import ozdemir0ozdemir.orderservice.dto.OrderLineItemsDto;
import ozdemir0ozdemir.orderservice.dto.OrderRequest;
import ozdemir0ozdemir.orderservice.dto.OrderResponse;
import ozdemir0ozdemir.orderservice.model.Order;
import ozdemir0ozdemir.orderservice.model.OrderLineItems;
import ozdemir0ozdemir.orderservice.repository.OrderRepository;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::toOrderLineItems)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        String[] skuCodesArray = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList()
                .toArray(String[]::new);


        // Call Inventory service, and place order if product is in stock
        InventoryResponse[] inventoriesArray = webClient.method(HttpMethod.GET)
                .uri("http://localhost:8082/api/inventory")
                .body(Mono.just(skuCodesArray), String[].class)
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream( Objects.requireNonNull(inventoriesArray) )
                .allMatch(InventoryResponse::getIsInStock);

        if(allProductsInStock) {
            orderRepository.save(order);
        }
        else {
            // TODO: change with custom exception
            throw new IllegalArgumentException("Product is not in stock");
        }


    }


    public List<OrderResponse> getAllOrders() {

        return this.orderRepository.findAll()
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }


    private OrderLineItems toOrderLineItems(OrderLineItemsDto dto) {

        return OrderLineItems.builder()
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .skuCode(dto.getSkuCode())
                .build();
    }

    private OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(order.getOrderLineItemsList())
                .build();
    }
}
