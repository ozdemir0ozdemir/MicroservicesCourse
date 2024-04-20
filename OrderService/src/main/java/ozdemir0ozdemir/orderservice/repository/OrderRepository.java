package ozdemir0ozdemir.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
