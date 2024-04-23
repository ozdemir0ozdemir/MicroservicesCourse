package ozdemir0ozdemir.orderservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.orderservice.dto.OrderRequest;
import ozdemir0ozdemir.orderservice.dto.OrderResponse;
import ozdemir0ozdemir.orderservice.service.OrderService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        return CompletableFuture.supplyAsync( () -> this.orderService.placeOrder(orderRequest) );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {

        return this.orderService.getAllOrders();
    }

    public CompletableFuture<String> inventoryFallBackMethod(OrderRequest orderRequest, RuntimeException ex) {

       return CompletableFuture.supplyAsync( ()->"Inventory service cannot response the request!" );
    }
}
