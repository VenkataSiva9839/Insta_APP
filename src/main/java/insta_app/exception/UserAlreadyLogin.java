package insta_app.exception;

@SuppressWarnings("serial")
public class UserAlreadyLogin extends RuntimeException {

	private String message;

	
	public UserAlreadyLogin(String message) {
		this.message = message;
	}



	public String getMessage() {
		return message;
	}
	
	
}
