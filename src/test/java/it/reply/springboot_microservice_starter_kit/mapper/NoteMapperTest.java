package it.reply.springboot_microservice_starter_kit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.dto.NoteResponseDTO;
import it.reply.springboot_microservice_starter_kit.entity.NoteEntity;

@ExtendWith(MockitoExtension.class)
class NoteMapperTest {

	@InjectMocks
	private NoteMapper mapper;

	@Test
	@DisplayName("toResponse -> maps entity to response DTO")
	void toResponse_shouldMapEntityToDto() {
		NoteEntity note = NoteEntity.builder().id(1L).title("Test Title").content("Test Content").build();

		NoteResponseDTO result = this.mapper.toResponse(note);

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTitle()).isEqualTo("Test Title");
		assertThat(result.getContent()).isEqualTo("Test Content");
	}

	@Test
	@DisplayName("toResponse -> returns null when entity is null")
	void toResponse_shouldReturnNullWhenNull() {
		assertThat(this.mapper.toResponse(null)).isNull();
	}

	@Test
	@DisplayName("toEntity -> maps request DTO to entity")
	void toEntity_shouldMapDtoToEntity() {
		NoteRequestDTO request = NoteRequestDTO.builder().title("Test Title").content("Test Content").build();

		NoteEntity result = this.mapper.toEntity(request);

		assertThat(result.getTitle()).isEqualTo("Test Title");
		assertThat(result.getContent()).isEqualTo("Test Content");
	}

	@Test
	@DisplayName("toEntity -> returns null when request DTO is null")
	void toEntity_shouldReturnNullWhenNull() {
		assertThat(this.mapper.toEntity(null)).isNull();
	}
}
