package com.webosmotic.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.webosmotic.pojo.ErrorResponse;

@ControllerAdvice
public class RestGlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(BadRequestException exp) {
		final ErrorResponse response = createErrorResponse(exp, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exp) {
		final ErrorResponse response = createErrorResponse(exp, HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(AppException exp) {
		final ErrorResponse response = createErrorResponse(exp, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse createErrorResponse(Exception exp, int status) {
		final ErrorResponse response = new ErrorResponse();
		response.setStatus(status);
		response.setMessage(exp.getMessage());
		response.setTimeStamp(LocalDateTime.now());
		return response;
	}

}
