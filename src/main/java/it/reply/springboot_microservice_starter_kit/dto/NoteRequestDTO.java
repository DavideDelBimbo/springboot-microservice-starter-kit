package it.reply.springboot_microservice_starter_kit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "Title of the note", example = "Note title", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255)
	private String title;

	@NotNull(message = "Content must be provided")
	@Size(max = 2000, message = "Content cannot exceed 2000 characters")
	@Schema(description = "Content of the note", example = "Note content", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 2000)
	private String content;

}
