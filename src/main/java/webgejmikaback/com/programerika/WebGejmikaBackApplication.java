package webgejmikaback.com.programerika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebGejmikaBackApplication{
	public static void main(String[] args) {
		// SWAGGER LINK http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config
		SpringApplication.run(WebGejmikaBackApplication.class, args);
	}

}