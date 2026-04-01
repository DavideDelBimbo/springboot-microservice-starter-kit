package it.reply.springboot_microservice_starter_kit.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfiguration {

	@Value("${app.title:springboot-microservice-starter-kit}")
	private String title;

	@Value("${app.description:Spring Boot Microservice Demo}")
	private String description;

	@Value("${app.version:1.0.0}")
	private String version;

	@Value("${app.developer.name:Jane Doe}")
	private String contactName;

	@Value("${app.developer.url:www.john-doe.com}")
	private String contactUrl;

	@Value("${app.developer.email:j.doe@reply.it}")
	private String contactEmail;

	@Value("${server.port:8080}")
	private Integer port;

	@Bean
	OpenAPI defineOpenApi() {
		Contact contact = new Contact().name(contactName).url(contactUrl).email(contactEmail);

		Info information = new Info()
				.title(title)
				.description(description)
				.version(version)
				.contact(contact);

		Server server = new Server().url(String.format("http://localhost:%d", port)).description("Development");

		return new OpenAPI().info(information).servers(List.of(server));
	}
}
