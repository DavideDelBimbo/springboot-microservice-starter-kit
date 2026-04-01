package it.reply.springboot_microservice_starter_kit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDTO {

	@NotBlank(message = "Title is mandatory")
	@Size(max = 255, message = "Title cannot exceed 255 characters")
	private String title;

	@NotNull(message = "Content must be provided")
	@Size(max = 2000, message = "Content cannot exceed 2000 characters")
	private String content;

}
