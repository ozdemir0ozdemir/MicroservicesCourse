package ozdemir0ozdemir.orderservice;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ozdemir0ozdemir.orderservice.dto.OrderLineItemsDto;
import ozdemir0ozdemir.orderservice.dto.OrderRequest;
import ozdemir0ozdemir.orderservice.model.OrderLineItems;
import ozdemir0ozdemir.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrderServiceAppTest {

    @Container
    static PostgreSQLContainer container =
            new PostgreSQLContainer("postgres:16.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void emptyDatabase() {

        this.orderRepository.deleteAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

    }

    @Test
    void itShouldCreateAnOrder() throws Exception {

        String request = objectMapper.writeValueAsString(createOrderRequest());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

        List<OrderLineItems> orderLineItems =
                this.orderRepository.findAll()
                        .stream()
                        .findFirst()
                        .get().getOrderLineItemsList();

        String itemsRequest = objectMapper.writeValueAsString(createOrderRequest().getOrderLineItemsDtoList());
        String itemsRepository = objectMapper.writeValueAsString(orderLineItems);

        System.out.println("ITEMS REQUEST");
        System.out.println(itemsRequest);

        System.out.println("ITEMS REPOSITORY");
        System.out.println(itemsRepository);

        Assertions.assertEquals(itemsRequest, itemsRepository);

    }


    private OrderRequest createOrderRequest() {

        return OrderRequest.builder()
                .orderLineItemsDtoList(createOrderLineItemsDtos())
                .build();
    }

    private List<OrderLineItemsDto> createOrderLineItemsDtos() {

        var o1 = OrderLineItemsDto.builder()
                .id(1L)
                .skuCode("code_1")
                .quantity(2)
                .price(BigDecimal.valueOf(2.99))
                .build();

        var o2 = OrderLineItemsDto.builder()
                .id(2L)
                .skuCode("code_2")
                .quantity(4)
                .price(BigDecimal.valueOf(4.99))
                .build();

        return List.of(o1, o2);
    }


}
