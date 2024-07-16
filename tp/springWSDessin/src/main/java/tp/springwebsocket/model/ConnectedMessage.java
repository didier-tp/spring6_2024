package tp.springwebsocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class ConnectedMessage {

	public enum MessageType {JOIN, LEAVE}

	private String type; //MessageType as String
	private String sender;
	private String content = null;

	public ConnectedMessage(String type, String sender, String content) {
		super();
		this.type = type;
		this.sender = sender;
		this.content = content;
	}

}
