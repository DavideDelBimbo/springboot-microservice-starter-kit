package it.reply.springboot_microservice_starter_kit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDTO {

	@Schema(description = "Unique identifier of note", example = "1")
	private Long id;

	@Schema(description = "Title of note", example = "Note title")
	private String title;

	@Schema(description = "Content of note", example = "Note content")
	private String content;

}
