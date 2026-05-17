package insta_app.exception;

@SuppressWarnings("serial")
public class RegistrationSessionExpired extends RuntimeException {

	private String message;

	public RegistrationSessionExpired(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
