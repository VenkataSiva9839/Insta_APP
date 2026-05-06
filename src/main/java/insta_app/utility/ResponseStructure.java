package insta_app.utility;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure<T> {

	private int statuscode;

	private String message;

	private T body;

	public int getStatuscode() {
		return statuscode;
	}

	public ResponseStructure<T> setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getBody() {
		return body;
	}

	public ResponseStructure<T> setBody(T body) {
		this.body = body;
		return this;
	}


}
