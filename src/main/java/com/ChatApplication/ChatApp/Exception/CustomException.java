package com.ChatApplication.ChatApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler{
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleALLException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails error=new ErrorDetails(ex.getMessage());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserAlreadyExists.class)
	public final ResponseEntity<ErrorDetails> UserAlreadyExistsHandler(Exception ex, WebRequest request) throws Exception {
		ErrorDetails error=new ErrorDetails(ex.getMessage());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.IM_USED);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> UserNotFoundExceptionHandler(Exception ex, WebRequest request) throws Exception {
		ErrorDetails error=new ErrorDetails(ex.getMessage());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PasswordNotMatchedException.class)
	public final ResponseEntity<ErrorDetails> PasswordNotMatchedExceptionHandler(Exception ex, WebRequest request) throws Exception {
		ErrorDetails error=new ErrorDetails(ex.getMessage());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.FAILED_DEPENDENCY);
	}
}
