package it.reply.springboot_microservice_starter_kit.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;

@RestController
@RequestMapping("/notes")
public class NoteController {
	private final List<NoteResponseDTO> notes = new ArrayList<>();
	private long nextId = 1;

	/**
	 * GET /notes
	 *
	 * @return list of {@link NoteResponseDTO} of all retrieved notes.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NoteResponseDTO>> getNotes() {
		return ResponseEntity.ok(this.notes);
	}

	/**
	 * GET /notes/{id}
	 *
	 * @param id unique identifier of note to be retrieved.
	 * @return {@link NoteResponseDTO} if found a note by its unique identifier or
	 *         HTTP 404 status if not found.
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> getNote(@PathVariable Long id) {
		return this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));
	}

	/**
	 * POST /notes
	 *
	 * @param request {@link NoteRequestDTO} with details of note to be created.
	 * @return {@link NoteResponseDTO} of created note (generated id)
	 *         with HTTP 201 status.
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO request) {
		NoteResponseDTO createdNote = new NoteResponseDTO(nextId++, request.getTitle(), request.getContent());
		this.notes.add(createdNote);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
	}

	/**
	 * PUT /notes/{id}
	 *
	 * @param id      unique identifier of note to be updated.
	 * @param request {@link NoteRequestDTO} with details of note to be updated.
	 * @return {@link NoteResponseDTO} of updated note or
	 *         HTTP 404 status if not found.
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable Long id, @RequestBody NoteRequestDTO request) {
		NoteResponseDTO retrivedNote = this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));

		retrivedNote.setTitle(request.getTitle());
		retrivedNote.setContent(request.getContent());

		return ResponseEntity.ok(retrivedNote);
	}

	/**
	 * DELETE /notes/{id}
	 *
	 * @param id unique identifier of note to be deleted.
	 * @return HTTP 204 No Content status or
	 *         HTTP 404 status if not found.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
		NoteResponseDTO retrivedNote = this.notes.stream()
				.filter(note -> note.getId().equals(id))
				.findFirst()
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + id + " not found"));

		this.notes.remove(retrivedNote);
		return ResponseEntity.noContent().build();
	}
}
