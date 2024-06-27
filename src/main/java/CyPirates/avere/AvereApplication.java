package CyPirates.avere;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
		@io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local Server"),
		@io.swagger.v3.oas.annotations.servers.Server(url = "https://avere.cypirates.xyz", description = "Production Server")
})
@SpringBootApplication
public class AvereApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvereApplication.class, args);
	}

}
