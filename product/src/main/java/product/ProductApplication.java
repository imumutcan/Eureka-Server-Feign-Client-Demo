package product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import product.model.Product;
import product.repository.ProductRepository;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductApplication implements CommandLineRunner {
	private final ProductRepository repository;

    public ProductApplication(ProductRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(repository.saveAll(
				List.of(
						Product.builder().productName("Fırın Tepsi").price(1250).build(),
						Product.builder().productName("Kabbak Tepsi").price(12330).build(),
						Product.builder().productName("Patttlak Tepsi").price(250).build()
				)
		));
	}
}
