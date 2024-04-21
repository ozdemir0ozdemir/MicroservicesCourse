package ozdemir0ozdemir.orderservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApp {
    public static void main( String[] args ) {
        SpringApplication.run(OrderServiceApp.class, args);
    }
}
