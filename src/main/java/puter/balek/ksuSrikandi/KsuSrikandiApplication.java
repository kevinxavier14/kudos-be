package puter.balek.ksuSrikandi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "puter.balek.ksuSrikandi")
public class KsuSrikandiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KsuSrikandiApplication.class, args);
		
	}

}
