package it.reply.springboot_microservice_starter_kit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Bean
	OpenAPI defineOpenApi() {
		Info information = new Info()
				.title("springboot-microservice-starter-kit")
				.description("Spring Boot Microservice Demo")
				.version("0.0.1");

		return new OpenAPI().info(information);
	}
}
