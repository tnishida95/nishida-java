package nishida.grocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"nishida.grocery", "nishida.restclient"})
public class GroceryPriceReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryPriceReaderApplication.class, args);
	}
}