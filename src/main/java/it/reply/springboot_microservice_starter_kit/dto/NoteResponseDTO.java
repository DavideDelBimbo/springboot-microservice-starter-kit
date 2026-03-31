package it.reply.springboot_microservice_starter_kit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDTO {

	private Long id;
	private String title;
	private String content;

}
