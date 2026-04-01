package it.reply.springboot_microservice_starter_kit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.entity.NoteEntity;
import it.reply.springboot_microservice_starter_kit.exception.NoteNotFoundException;
import it.reply.springboot_microservice_starter_kit.mapper.NoteMapper;
import it.reply.springboot_microservice_starter_kit.repository.NoteRepository;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

	private static final Long ID = 1L;

	@Mock
	private NoteRepository repository;

	@Mock
	private NoteMapper mapper;

	@InjectMocks
	private NoteService service;

	@Test
	@DisplayName("getNotes -> returns mapped list of notes")
	void getNotes_shouldReturnMappedList() {
		NoteEntity note = this.buildNote(ID);
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.repository.findAll()).thenReturn(List.of(note));
		when(this.mapper.toResponse(note)).thenReturn(expectedResponse);

		Collection<NoteResponseDTO> result = this.service.getNotes();

		assertThat(result).containsExactly(expectedResponse);
		verify(this.repository, times(1)).findAll();
		verify(this.mapper, times(1)).toResponse(note);
	}

	@Test
	@DisplayName("getNote -> returns mapped note")
	void getNote_shouldReturnNote() {
		NoteEntity note = this.buildNote(ID);
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.repository.findById(ID)).thenReturn(Optional.of(note));
		when(this.mapper.toResponse(note)).thenReturn(expectedResponse);

		NoteResponseDTO result = this.service.getNote(ID);

		assertThat(result).isEqualTo(expectedResponse);
		verify(this.repository, times(1)).findById(ID);
	}

	@Test
	@DisplayName("getNote -> throws NoteNotFoundException when note is not found")
	void getNote_shouldThrowWhenNotFound() {
		when(this.repository.findById(ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> this.service.getNote(ID))
				.isInstanceOf(NoteNotFoundException.class);
	}

	@Test
	@DisplayName("createNote -> saves and returns mapped note")
	void createNote_shouldSaveAndReturn() {
		NoteRequestDTO request = this.buildRequest();
		NoteEntity entityToSave = this.buildNote(null);
		NoteEntity entitySaved = this.buildNote(ID);
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.mapper.toEntity(request)).thenReturn(entityToSave);
		when(this.repository.save(entityToSave)).thenReturn(entitySaved);
		when(this.mapper.toResponse(entitySaved)).thenReturn(expectedResponse);

		NoteResponseDTO result = this.service.createNote(request);

		assertThat(result).isEqualTo(expectedResponse);
		verify(this.mapper, times(1)).toEntity(request);
		verify(this.repository, times(1)).save(entityToSave);
		verify(this.mapper, times(1)).toResponse(entitySaved);
	}

	@Test
	@DisplayName("updateNote -> updates fields and returns mapped note")
	void updateNote_shouldUpdateAndReturn() {
		NoteRequestDTO request = this.buildRequest();
		NoteEntity entityToUpdate = this.buildNote(ID);
		NoteEntity entityUpdated = this.buildNote(ID);
		NoteResponseDTO expectedResponse = this.buildResponse(ID);

		when(this.repository.findById(ID)).thenReturn(Optional.of(entityToUpdate));
		when(this.repository.save(entityToUpdate)).thenReturn(entityUpdated);
		when(this.mapper.toResponse(entityUpdated)).thenReturn(expectedResponse);

		NoteResponseDTO result = this.service.updateNote(ID, request);

		assertThat(result).isEqualTo(expectedResponse);
		verify(this.repository, times(1)).findById(ID);
		verify(this.repository, times(1)).save(entityToUpdate);
		verify(this.mapper, times(1)).toResponse(entityUpdated);
	}

	@Test
	@DisplayName("updateNote -> throws NoteNotFoundException when not found")
	void updateNote_shouldThrowWhenNotFound() {
		NoteRequestDTO request = this.buildRequest();

		when(this.repository.findById(ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> this.service.updateNote(ID, request))
				.isInstanceOf(NoteNotFoundException.class);
	}

	@Test
	@DisplayName("deleteNote -> deletes when note exists")
	void deleteNote_shouldDelete() {
		when(this.repository.existsById(ID)).thenReturn(true);

		this.service.deleteNote(ID);

		verify(this.repository, times(1)).deleteById(ID);
	}

	@Test
	@DisplayName("deleteNote -> throws NoteNotFoundException when not found")
	void deleteNote_shouldThrowWhenNotFound() {
		when(this.repository.existsById(ID)).thenReturn(false);

		assertThatThrownBy(() -> this.service.deleteNote(ID))
				.isInstanceOf(NoteNotFoundException.class);
	}

	private NoteEntity buildNote(Long id) {
		return NoteEntity.builder().id(id).title("Test Title").content("Test Content").build();
	}

	private NoteRequestDTO buildRequest() {
		return NoteRequestDTO.builder().title("Test Title").content("Test Content").build();
	}

	private NoteResponseDTO buildResponse(Long id) {
		return NoteResponseDTO.builder().id(id).title("Test Title").content("Test Content").build();
	}
}
