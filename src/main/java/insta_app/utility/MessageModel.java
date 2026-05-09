package insta_app.utility;

public class MessageModel {

private String to;
	
	private String subject;
	
	private String text;

	public MessageModel(String to, String subject, String text) {
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
	
	
	
}
