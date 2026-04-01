package it.reply.springboot_microservice_starter_kit.handler;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.reply.springboot_microservice_starter_kit.dto.ErrorResponseDTO;
import it.reply.springboot_microservice_starter_kit.exception.NoteNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles {@link NoteNotFoundException} exceptions.
	 *
	 * @param exception raised when a note is not found.
	 * @return HTTP 404 Not Found response with an {@link ErrorResponseDTO}
	 *         containing the exception message.
	 */
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleNotFound(NoteNotFoundException exception) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		String message = exception.getMessage();

		ErrorResponseDTO response = ErrorResponseDTO.builder().status(status).message(message).build();

		return ResponseEntity.status(status).body(response);
	}

	/**
	 * Handles {@link MethodArgumentNotValidException} exceptions.
	 *
	 * @param exception raised when DTO validation fails.
	 * @return HTTP 400 Bad Request response with an {@link ErrorResponseDTO}
	 *         containing validation messages for each invalid field.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException exception) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "Validation failed";
		Map<String, String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
						(existing, replacement) -> existing));

		ErrorResponseDTO response = ErrorResponseDTO.builder()
				.status(status)
				.message(message)
				.validationErrors(validationErrors)
				.build();

		return ResponseEntity.badRequest().body(response);
	}
}
