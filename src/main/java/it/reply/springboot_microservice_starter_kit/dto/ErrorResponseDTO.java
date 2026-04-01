package it.reply.springboot_microservice_starter_kit.dto;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseDTO {

	private HttpStatusCode status;

	private String message;

	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();

	private Map<String, String> validationErrors;

}
