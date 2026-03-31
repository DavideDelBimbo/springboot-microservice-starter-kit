package it.reply.springboot_microservice_starter_kit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDTO {

	private String title;
	private String content;

}
