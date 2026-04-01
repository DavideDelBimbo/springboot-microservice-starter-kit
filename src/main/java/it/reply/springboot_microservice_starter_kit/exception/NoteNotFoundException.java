package it.reply.springboot_microservice_starter_kit.exception;

import jakarta.persistence.EntityNotFoundException;

public class NoteNotFoundException extends EntityNotFoundException {

	public NoteNotFoundException(Long id) {
		super("Cannot find note with id=" + id);
	}
}
