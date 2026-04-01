package it.reply.springboot_microservice_starter_kit.dto;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "HTTP status code", example = "400 BAD_REQUEST")
	private HttpStatusCode status;

	@Schema(description = "Error message", example = "Validation failed")
	private String message;

	@Builder.Default
	@Schema(description = "Timestamp of the error", example = "2026-03-17T10:00:00")
	private LocalDateTime timestamp = LocalDateTime.now();

	@Schema(description = "Validation errors per field", example = "{\"title\": \"Title is mandatory\"}", nullable = true)
	private Map<String, String> validationErrors;

}
