package insta_app.exception;


@SuppressWarnings("serial")
public class OtpExpiredException extends RuntimeException {

	private String messge;

	public OtpExpiredException(String messge) {
		this.messge = messge;
	}

	public String getMessge() {
		return messge;
	}
	
	
}
