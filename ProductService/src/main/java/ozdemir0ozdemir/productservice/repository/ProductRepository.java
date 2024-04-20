package ozdemir0ozdemir.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ozdemir0ozdemir.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
