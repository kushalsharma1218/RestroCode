package com.example.UserRegistrationService.model;

import org.springframework.http.HttpStatus;

public class CustomResponse {

	Object data;
	HttpStatus status;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public CustomResponse(Object data, HttpStatus ok) {
		super();
		this.data = data;
		this.status = ok;
	}
	
	
	
	
}
