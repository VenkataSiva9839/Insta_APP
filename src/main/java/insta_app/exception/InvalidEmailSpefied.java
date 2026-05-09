package insta_app.exception;

@SuppressWarnings("serial")
public class InvalidEmailSpefied extends RuntimeException {

	private String message;

	public InvalidEmailSpefied(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
		
	
	
}
