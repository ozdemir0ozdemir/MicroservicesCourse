package ozdemir0ozdemir.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.productservice.dto.ProductRequest;
import ozdemir0ozdemir.productservice.dto.ProductResponse;
import ozdemir0ozdemir.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {

        this.productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {

        return this.productService.getAllProducts();
    }

}
