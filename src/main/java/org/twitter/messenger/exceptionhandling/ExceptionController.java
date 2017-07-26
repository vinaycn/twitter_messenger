package org.twitter.messenger.exceptionhandling;

import javax.servlet.http.HttpServletRequest;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleNotFoundException(final HttpServletRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Resource Not found");
		errorMessage.setDeveloperMessage(request.getContextPath() + "Not found");
		errorMessage.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}

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

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleMethodNotAllowed(final Exception exception,
			final HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Method not allowed");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
		// logger.error("Badrequest " + request.toString() + "With exception
		// message " + exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
	}

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleBadRequest(final Exception exception,
			final HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Bad Request");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.BAD_REQUEST);
		// logger.error("Badrequest " + request.toString() + "With exception
		// message " + exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
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
