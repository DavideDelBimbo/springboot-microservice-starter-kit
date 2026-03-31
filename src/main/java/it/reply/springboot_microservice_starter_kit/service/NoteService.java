package it.reply.springboot_microservice_starter_kit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;

@Service
public class NoteService {
	private final List<NoteResponseDTO> notes = new ArrayList<>();
	private long nextId = 1;

	/**
	 * Retrieve all saved notes.
	 *
	 * @return corresponding list of {@link NoteResponseDTO} of all retrieved notes.
	 */
	public Collection<NoteResponseDTO> getNotes() {
		return this.notes;
	}

	/**
	 * Retrieve a specific saved note.
	 *
	 * @param id unique identifier of note to be retrieved.
	 * @return corresponding {@link NoteResponseDTO} of retrived note.
	 * @throws ResponseStatusException if no note exists with specified id.
	 */
	public NoteResponseDTO getNote(Long id) {
		return this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));
	}

	/**
	 * Create and save a new note.
	 *
	 * @param request {@link NoteRequestDTO} with details of note to be created.
	 * @return corresponding {@link NoteResponseDTO} of created (generated id).
	 */
	public NoteResponseDTO createNote(NoteRequestDTO request) {
		NoteResponseDTO createdNote = new NoteResponseDTO(nextId++, request.getTitle(), request.getContent());
		this.notes.add(createdNote);

		return createdNote;
	}

	/**
	 * Update an existing note.
	 *
	 * @param id      unique identifier of note to be updated.
	 * @param request {@link NoteRequestDTO} with details of note to be updated.
	 * @return corresponding {@link NoteResponseDTO} of updated note.
	 * @throws ResponseStatusException if no note exists with specified id.
	 */
	public NoteResponseDTO updateNote(Long id, NoteRequestDTO request) {
		NoteResponseDTO retrivedNote = this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));

		retrivedNote.setTitle(request.getTitle());
		retrivedNote.setContent(request.getContent());

		return retrivedNote;
	}

	/**
	 * Deletes an existing note.
	 *
	 * @param id unique identifier of note to be deleted.
	 * @throws ResponseStatusException if no note exists with specified id.
	 */
	public void deleteNote(Long id) {
		NoteResponseDTO retrivedNote = this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));

		this.notes.remove(retrivedNote);
	}
}
