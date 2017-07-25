package org.twitter.messenger.exceptionhandling;

import javax.servlet.http.HttpServletRequest;

import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ExceptionController.class);

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleUnSupportedMediaException(final Exception exception,
			final HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("unsupported Media type. Unable to process request");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		// logger.error("Badrequest " + request.toString() + "With exception
		// message " + exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(JdbcSQLException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleServerException(final Exception exception,
			final HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Sorry unable to process request");
		errorMessage.setDeveloperMessage("Error in Sql");
		errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		// logger.error("Badrequest " + request.toString() + "With exception
		// message " + exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleUserNotFound(final Exception exception,
			final HttpServletRequest request) {

		System.out.println("User Not found");
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Invalid person id");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.NOT_FOUND);
		// logger.error("Badrequest " + request.toString() + "With exception
		// message " + exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}

}
