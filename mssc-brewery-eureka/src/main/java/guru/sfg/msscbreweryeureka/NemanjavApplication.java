package guru.sfg.msscbreweryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NemanjavApplication {

	public static void main(String[] args) {
		SpringApplication.run(NemanjavApplication.class, args);
	}

}
