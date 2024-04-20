package ozdemir0ozdemir.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ozdemir0ozdemir.orderservice.dto.OrderLineItemsDto;
import ozdemir0ozdemir.orderservice.dto.OrderRequest;
import ozdemir0ozdemir.orderservice.dto.OrderResponse;
import ozdemir0ozdemir.orderservice.model.Order;
import ozdemir0ozdemir.orderservice.model.OrderLineItems;
import ozdemir0ozdemir.orderservice.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::toOrderLineItems)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);

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
