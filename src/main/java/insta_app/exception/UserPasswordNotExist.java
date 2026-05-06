package insta_app.exception;

@SuppressWarnings("serial")
public class UserPasswordNotExist extends RuntimeException {

	private String message;

	public UserPasswordNotExist(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
