package it.reply.springboot_microservice_starter_kit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.service.NoteService;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

	private static final Long ID = 1L;

	@Mock
	private NoteService service;

	@InjectMocks
	private NoteController controller;

	@Test
	@DisplayName("GET /notes -> 200 OK with list of notes")
	void getNotes_shouldReturnOkWithList() {
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.service.getNotes()).thenReturn(List.of(expectedResponse));

		ResponseEntity<Collection<NoteResponseDTO>> response = this.controller.getNotes();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).containsExactly(expectedResponse);
		verify(this.service, times(1)).getNotes();
	}

	@Test
	@DisplayName("GET /notes/{id} -> 200 OK with note")
	void getNote_shouldReturnOkWithNote() {
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.service.getNote(ID)).thenReturn(expectedResponse);

		ResponseEntity<NoteResponseDTO> response = this.controller.getNote(ID);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expectedResponse);
		verify(this.service, times(1)).getNote(ID);
	}

	@Test
	@DisplayName("POST /notes -> 201 CREATED with created note")
	void createNote_shouldReturnCreatedWithNote() {
		NoteRequestDTO request = this.buildRequest();
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.service.createNote(request)).thenReturn(expectedResponse);

		ResponseEntity<NoteResponseDTO> response = this.controller.createNote(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(expectedResponse);
		verify(this.service, times(1)).createNote(request);
	}

	@Test
	@DisplayName("PUT /notes/{id} -> 200 OK with updated note")
	void updateNote_shouldReturnOkWithUpdatedNote() {
		NoteRequestDTO request = this.buildRequest();
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.service.updateNote(ID, request)).thenReturn(expectedResponse);

		ResponseEntity<NoteResponseDTO> response = this.controller.updateNote(ID, request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expectedResponse);
		verify(this.service, times(1)).updateNote(ID, request);
	}

	@Test
	@DisplayName("DELETE /notes/{id} -> 204 NO CONTENT")
	void deleteNote_shouldReturnNoContent() {
		doNothing().when(this.service).deleteNote(ID);

		ResponseEntity<Void> response = this.controller.deleteNote(ID);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(response.getBody()).isNull();
		verify(this.service, times(1)).deleteNote(ID);
	}

	private NoteRequestDTO buildRequest() {
		return NoteRequestDTO.builder().title("Test Title").content("Test Content").build();
	}

	private NoteResponseDTO buildResponse(Long id) {
		return NoteResponseDTO.builder().id(id).title("Test Title").content("Test Content").build();
	}
}
