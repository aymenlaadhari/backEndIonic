package de.fiduciagad.sharea.server.rest;

import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains some common methods required by the controllers.
 *
 * It will find the token and find the user for the token if required. It also
 * prevents some exceptions to reach the Frontend or at least change their HTTP
 * Code.
 *
 *
 * @author xck1064
 *
 */
@ControllerAdvice(annotations = RestController.class)
public class AbstractController {

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(DocumentNotFoundException.class)
	public void catchDocumentNotFoundException() {
	}
	
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(UpdateConflictException.class)
	public void catchUpdateConflictException() {
	}

}
