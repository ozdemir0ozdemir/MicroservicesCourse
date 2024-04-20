package ozdemir0ozdemir.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.orderservice.dto.OrderRequest;
import ozdemir0ozdemir.orderservice.dto.OrderResponse;
import ozdemir0ozdemir.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {

        this.orderService.placeOrder(orderRequest);
        return "Order Placed Succesfully.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return this.orderService.getAllOrders();
    }
}
