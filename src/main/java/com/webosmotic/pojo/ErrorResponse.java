package com.webosmotic.pojo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * generic class to describe the Error response of an API.
 *
 */

@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;

	public ErrorResponse(LocalDateTime timeStamp, int status, String error, String message) {
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public ErrorResponse() {
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
