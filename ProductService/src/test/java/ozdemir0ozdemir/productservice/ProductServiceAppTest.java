package ozdemir0ozdemir.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ozdemir0ozdemir.productservice.dto.ProductRequest;
import ozdemir0ozdemir.productservice.model.Product;
import ozdemir0ozdemir.productservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceAppTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.8");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void emptyProductRepository() {
        productRepository.deleteAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.url", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);

    }

    @Test
    void itShouldCreateProduct() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getProductRequest()))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void itShouldGetAllProductsList() throws Exception {
        this.productRepository.saveAll(getProductsList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(getProductsList())));

    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("IPhone 6")
                .description("Apple IPhone 6")
                .price(BigDecimal.valueOf(66.99))
                .build();
    }

    private List<Product> getProductsList() {
        Product p1 = Product.builder()
                .id("1")
                .name("IPhone 6")
                .description("Apple IPhone 6")
                .price(BigDecimal.valueOf(66.99))
                .build();

        Product p2 = Product.builder()
                .id("2")
                .name("IPhone 7")
                .description("Apple IPhone 7")
                .price(BigDecimal.valueOf(77.99))
                .build();

        return List.of(p1, p2);
    }

}
