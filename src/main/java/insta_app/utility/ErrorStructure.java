package insta_app.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure {

	private int statuscode;
	
	private String message;

	public int getStatuscode() {
		return statuscode;
	}

	public ErrorStructure setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ErrorStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	
	
}
