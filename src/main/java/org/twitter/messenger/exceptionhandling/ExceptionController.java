package org.twitter.messenger.exceptionhandling;

import javax.servlet.http.HttpServletRequest;

import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleNotFoundException(final HttpServletRequest request) {
		
		logger.error("404 for the request " + request);
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

		logger.error("UNSupported Media type for the request " + request );
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("unsupported Media type. Unable to process request");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(JdbcSQLException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleServerException(final Exception exception,
			final HttpServletRequest request) {
		
		logger.error("internal Server Error Due to Error in Sql " + exception.getMessage());
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Sorry unable to process request");
		errorMessage.setDeveloperMessage("Error in Sql");
		errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleMethodNotAllowed(final Exception exception,
			final HttpServletRequest request) {

		logger.error("Handler method not found for the request   " + request);
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Method not allowed");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleBadRequest(final Exception exception, final HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Bad Request");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleUserNotFound(final Exception exception,
			final HttpServletRequest request) {

		logger.error("Person with the id is Does n't exists" + request);
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Invalid person id");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleInternalServerException(final Exception exception,
			final HttpServletRequest request) {

		logger.error("Internal server error for request " + request + "with error message " + exception.getMessage());
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage("Sorry unable to process request");
		errorMessage.setDeveloperMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
