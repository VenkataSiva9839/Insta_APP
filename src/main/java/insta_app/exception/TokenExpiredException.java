package insta_app.exception;

@SuppressWarnings("serial")
public class TokenExpiredException extends RuntimeException {

	private String message;

	public TokenExpiredException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
