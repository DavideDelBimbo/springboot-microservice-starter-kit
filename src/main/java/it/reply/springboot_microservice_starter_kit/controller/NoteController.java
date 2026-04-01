package it.reply.springboot_microservice_starter_kit.controller;

import java.util.Collection;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.reply.springboot_microservice_starter_kit.dto.ErrorResponseDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@Tag(name = "Notes")
public class NoteController {

	private final NoteService service;

	/**
	 * GET /notes
	 *
	 * @return list of {@link NoteResponseDTO} of all retrieved notes.
	 */
	@Operation(summary = "Retrieve all notes", description = "Returns list of all saved notes.", responses = {
			@ApiResponse(responseCode = "200", description = "List of notes successfully retrieved.", content = @Content(schema = @Schema(implementation = NoteResponseDTO.class)))
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NoteResponseDTO>> getNotes() {
		Collection<NoteResponseDTO> retrievedNotes = this.service.getNotes();
		return ResponseEntity.ok(retrievedNotes);
	}

	/**
	 * GET /notes/{id}
	 *
	 * @param id unique identifier of note to be retrieved.
	 * @return {@link NoteResponseDTO} if found a note by its unique identifier or
	 *         HTTP 404 status if not found.
	 */
	@Operation(summary = "Retrieve a note by id", description = "Returns note corresponding to specified id.", responses = {
			@ApiResponse(responseCode = "200", description = "Note successfully retrieved.", content = @Content(schema = @Schema(implementation = NoteResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Note not found.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> getNote(
			@Parameter(description = "Id of note to be retrieved.", required = true) @PathVariable Long id) {
		NoteResponseDTO retrivedNote = this.service.getNote(id);
		return ResponseEntity.ok(retrivedNote);
	}

	/**
	 * POST /notes
	 *
	 * @param request {@link NoteRequestDTO} with details of note to be created.
	 * @return {@link NoteResponseDTO} of created note (generated id)
	 *         with HTTP 201 status.
	 */
	@Operation(summary = "Create a new note", description = "Creates new note with provided details.", responses = {
			@ApiResponse(responseCode = "201", description = "Note successfully created.", content = @Content(schema = @Schema(implementation = NoteResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Request validation error.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> createNote(
			@Parameter(description = "DTO with created note data.", required = true) @Valid @RequestBody NoteRequestDTO request) {
		NoteResponseDTO createdNote = this.service.createNote(request);
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
	@Operation(summary = "Update an existing note", description = "Updates note corresponding to specified id with provided details.", responses = {
			@ApiResponse(responseCode = "200", description = "Note successfully updated.", content = @Content(schema = @Schema(implementation = NoteResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Request validation error.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Note not found.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteResponseDTO> updateNote(
			@Parameter(description = "Id of note to be retrieved.", required = true) @PathVariable Long id,
			@Parameter(description = "DTO with updated note data.", required = true) @Valid @RequestBody NoteRequestDTO request) {
		NoteResponseDTO updatedNote = this.service.updateNote(id, request);
		return ResponseEntity.ok(updatedNote);
	}

	/**
	 * DELETE /notes/{id}
	 *
	 * @param id unique identifier of note to be deleted.
	 * @return HTTP 204 No Content status or
	 *         HTTP 404 status if not found.
	 */
	@Operation(summary = "Delete a note", description = "Deletes note corresponding to specified id", responses = {
			@ApiResponse(responseCode = "204", description = "Note successfully deleted."),
			@ApiResponse(responseCode = "404", description = "Note not found.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteNote(
			@Parameter(description = "Id of note to be deleted.", required = true) @PathVariable Long id) {
		this.service.deleteNote(id);
		return ResponseEntity.noContent().build();
	}
}
