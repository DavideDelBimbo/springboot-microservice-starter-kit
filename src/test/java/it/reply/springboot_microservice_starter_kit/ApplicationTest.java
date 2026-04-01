package it.reply.springboot_microservice_starter_kit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import it.reply.springboot_microservice_starter_kit.dto.NoteRequestDTO;
import it.reply.springboot_microservice_starter_kit.entity.NoteEntity;
import it.reply.springboot_microservice_starter_kit.repository.NoteRepository;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationTest {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NoteRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.repository.deleteAll();
	}

	@Test
	@DisplayName("Application context loads successfully")
	void contextLoads() {
		assertThat(context).isNotNull();
	}

	@Test
	@DisplayName("GET /notes -> 200 OK with empty list")
	void getNotes_shouldReturnEmptyList() throws Exception {
		this.mockMvc.perform(get("/notes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	@DisplayName("GET /notes -> 200 OK with list of notes")
	void getNotes_shouldReturnNotes() throws Exception {
		this.saveNote("Test Title 1", "Test Content 1");
		this.saveNote("Test Title 2", "Test Content 2");

		this.mockMvc.perform(get("/notes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", is("Test Title 1")))
				.andExpect(jsonPath("$[0].content", is("Test Content 1")))
				.andExpect(jsonPath("$[1].title", is("Test Title 2")))
				.andExpect(jsonPath("$[1].content", is("Test Content 2")));
	}

	@Test
	@DisplayName("GET /notes/{id} -> 200 OK with note")
	void getNote_shouldReturnNote() throws Exception {
		NoteEntity noteSaved = this.saveNote("Test Title", "Test Content");

		this.mockMvc.perform(get("/notes/{id}", noteSaved.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(noteSaved.getId().intValue())))
				.andExpect(jsonPath("$.title", is("Test Title")))
				.andExpect(jsonPath("$.content", is("Test Content")));
	}

	@Test
	@DisplayName("GET /notes/{id} -> 404 NOT FOUND when note does not exist")
	void getNote_shouldReturn404WhenNotFound() throws Exception {
		this.mockMvc.perform(get("/notes/{id}", 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("POST /notes -> 201 CREATED with created note")
	void createNote_shouldReturnCreatedNote() throws Exception {
		NoteRequestDTO request = NoteRequestDTO.builder().title("New Test Title").content("New Test Content").build();

		this.mockMvc.perform(post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.toJson(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.title", is("New Test Title")))
				.andExpect(jsonPath("$.content", is("New Test Content")));
	}

	@Test
	@DisplayName("POST /notes -> 400 BAD REQUEST when title is blank")
	void createNote_shouldReturn400WhenInvalid() throws Exception {
		NoteRequestDTO request = NoteRequestDTO.builder().title("").content("Test Content").build();

		this.mockMvc.perform(post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.toJson(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("PUT /notes/{id} -> 200 OK with updated note")
	void updateNote_shouldReturnUpdatedNote() throws Exception {
		NoteEntity noteSaved = this.saveNote("Old Test Title", "Old Test Content");
		NoteRequestDTO request = NoteRequestDTO.builder().title("Updated Test Title").content("Updated Test Content")
				.build();

		this.mockMvc.perform(put("/notes/{id}", noteSaved.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.toJson(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(noteSaved.getId().intValue())))
				.andExpect(jsonPath("$.title", is("Updated Test Title")))
				.andExpect(jsonPath("$.content", is("Updated Test Content")));
	}

	@Test
	@DisplayName("PUT /notes/{id} -> 404 NOT FOUND when note does not exist")
	void updateNote_shouldReturn404WhenNotFound() throws Exception {
		NoteRequestDTO request = NoteRequestDTO.builder().title("Updated Test Title").content("Updated Test Content")
				.build();

		this.mockMvc.perform(put("/notes/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.toJson(request)))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("PUT /notes/{id} -> 400 BAD REQUEST when body is invalid")
	void updateNote_shouldReturn400WhenInvalid() throws Exception {
		NoteEntity noteSaved = this.saveNote("Old Test Title", "Old Test Content");
		NoteRequestDTO request = NoteRequestDTO.builder().title("").content("Updated Test Content").build();

		this.mockMvc.perform(put("/notes/{id}", noteSaved.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.toJson(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("DELETE /notes/{id} -> 204 NO CONTENT")
	void deleteNote_shouldReturn204() throws Exception {
		NoteEntity noteSaved = this.saveNote("Test Title", "Test Content");

		this.mockMvc.perform(delete("/notes/{id}", noteSaved.getId()))
				.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("DELETE /notes/{id} -> 404 NOT FOUND when note does not exist")
	void deleteNote_shouldReturn404WhenNotFound() throws Exception {
		this.mockMvc.perform(delete("/notes/{id}", 1L))
				.andExpect(status().isNotFound());
	}

	private NoteEntity saveNote(String title, String content) {
		NoteEntity note = NoteEntity.builder().title(title).content(content).build();
		return this.repository.save(note);
	}

	private String toJson(Object obj) throws Exception {
		return this.objectMapper.writeValueAsString(obj);
	}
}
