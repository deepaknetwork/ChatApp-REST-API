package com.ChatApplication.ChatApp.Exception;

public class PasswordNotMatchedException extends RuntimeException {

	public PasswordNotMatchedException(String message) {
		super(message);
	}
}
