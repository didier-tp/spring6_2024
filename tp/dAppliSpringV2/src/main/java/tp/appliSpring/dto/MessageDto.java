package tp.appliSpring.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

//@Getter @Setter @ToString @NoArgsConstructor
public class MessageDto {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private String message;
	//private String details;

	public MessageDto(String message) {
		super();
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
	
	

	public MessageDto() {
		super();
		this.timestamp = LocalDateTime.now();
	}

	

	public LocalDateTime getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	@Override
	public String toString() {
		return "MessageDto [timestamp=" + timestamp + ", message=" + message + "]";
	}
	
	
	
}
