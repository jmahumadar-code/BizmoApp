package com.bizmobiz.bizmoweb.util;

public class Validate {

	private Boolean valid;
	private String message;
	
	public Validate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Validate(Boolean valid, String message) {
		super();
		this.valid = valid;
		this.message = message;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Validate [valid=" + valid + ", message=" + message + "]";
	}
}
