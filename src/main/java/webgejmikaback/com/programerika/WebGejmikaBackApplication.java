package webgejmikaback.com.programerika;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
                                    title = "Gejmika API",
                                    version = "1.1",
                                    description = "Backend service for players with multiple games"
                                ))
public class WebGejmikaBackApplication{
	public static void main(String[] args) {
		// SWAGGER LINK http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config
		SpringApplication.run(WebGejmikaBackApplication.class, args);
	}

}