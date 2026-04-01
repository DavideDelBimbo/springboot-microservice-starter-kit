package it.reply.springboot_microservice_starter_kit.mapper;

import org.springframework.stereotype.Component;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.entity.NoteEntity;

@Component
public class NoteMapper {

	/**
	 * Converts an entity into a response DTO.
	 *
	 * @param note {@link NoteEntity} entity to convert.
	 * @return corresponding {@link NoteResponseDTO} or null.
	 */
	public NoteResponseDTO toResponse(NoteEntity note) {
		if (note == null)
			return null;
		return NoteResponseDTO.builder().id(note.getId()).title(note.getTitle()).content(note.getContent()).build();
	}

	/**
	 * Converts a request DTO into an entity.
	 *
	 * @param request {@link NoteRequestDTO} to convert.
	 * @return corresponding {@link NoteEntity} entity or null.
	 */
	public NoteEntity toEntity(NoteRequestDTO request) {
		if (request == null)
			return null;
		return NoteEntity.builder().title(request.getTitle()).content(request.getContent()).build();
	}
}
