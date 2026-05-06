package insta_app.exception;


@SuppressWarnings("serial")
public class UserNotExistByEmail extends RuntimeException {
	
	private String message;

	public UserNotExistByEmail(String message) {

		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	
	

}
