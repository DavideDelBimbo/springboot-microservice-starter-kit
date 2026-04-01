package it.reply.springboot_microservice_starter_kit.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.entity.NoteEntity;
import it.reply.springboot_microservice_starter_kit.exception.NoteNotFoundException;
import it.reply.springboot_microservice_starter_kit.mapper.NoteMapper;
import it.reply.springboot_microservice_starter_kit.repository.NoteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

	private final NoteRepository repository;
	private final NoteMapper mapper;

	/**
	 * Retrieve all saved notes.
	 *
	 * @return corresponding list of {@link NoteResponseDTO} of all retrieved notes.
	 */
	public Collection<NoteResponseDTO> getNotes() {
		Collection<NoteEntity> retrievedNotes = this.repository.findAll();
		return retrievedNotes.stream().map(this.mapper::toResponse).toList();
	}

	/**
	 * Retrieve a specific saved note.
	 *
	 * @param id unique identifier of note to be retrieved.
	 * @return corresponding {@link NoteResponseDTO} of retrived note.
	 * @throws NoteNotFoundException if no note exists with specified id.
	 */
	public NoteResponseDTO getNote(Long id) {
		NoteEntity retrivedNote = this.repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
		return this.mapper.toResponse(retrivedNote);
	}

	/**
	 * Create and save a new note.
	 *
	 * @param request {@link NoteRequestDTO} with details of note to be created.
	 * @return corresponding {@link NoteResponseDTO} of created (generated id).
	 */
	public NoteResponseDTO createNote(NoteRequestDTO request) {
		NoteEntity noteEntity = this.mapper.toEntity(request);
		NoteEntity createdNote = this.repository.save(noteEntity);
		return this.mapper.toResponse(createdNote);
	}

	/**
	 * Update an existing note.
	 *
	 * @param id      unique identifier of note to be updated.
	 * @param request {@link NoteRequestDTO} with details of note to be updated.
	 * @return corresponding {@link NoteResponseDTO} of updated note.
	 * @throws NoteNotFoundException if no note exists with specified id.
	 */
	public NoteResponseDTO updateNote(Long id, NoteRequestDTO request) {
		NoteEntity retrivedNote = this.repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
		retrivedNote.setTitle(request.getTitle());
		retrivedNote.setContent(request.getContent());

		NoteEntity updatedNote = this.repository.save(retrivedNote);
		return this.mapper.toResponse(updatedNote);
	}

	/**
	 * Deletes an existing note.
	 *
	 * @param id unique identifier of note to be deleted.
	 * @throws NoteNotFoundException if no note exists with specified id.
	 */
	public void deleteNote(Long id) {
		if (!this.repository.existsById(id))
			throw new NoteNotFoundException(id);

		this.repository.deleteById(id);
	}
}
