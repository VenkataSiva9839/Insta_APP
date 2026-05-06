package insta_app.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistByEmail extends RuntimeException {


	private String message;

	public UserAlreadyExistByEmail(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}




}
