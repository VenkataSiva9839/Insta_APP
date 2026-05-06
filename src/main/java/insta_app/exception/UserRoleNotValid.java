package insta_app.exception;

@SuppressWarnings("serial")
public class UserRoleNotValid extends RuntimeException {

	private String message;

	
	public UserRoleNotValid(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}
	
	
}
