package ozdemir0ozdemir.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ozdemir0ozdemir.productservice.dto.ProductRequest;
import ozdemir0ozdemir.productservice.dto.ProductResponse;
import ozdemir0ozdemir.productservice.model.Product;
import ozdemir0ozdemir.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved.", product.getId());
    }

    public List<ProductResponse> getAllProducts() {

        return this.productRepository.findAll().stream()
                .map(this::mapProductToProductResponse)
                .toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
