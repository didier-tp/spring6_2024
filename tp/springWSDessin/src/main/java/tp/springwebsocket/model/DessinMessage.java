package tp.springwebsocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class DessinMessage {
	
	    public enum MessageType { NEWLINE, CLEAR }
	    
	    private String type; //MessageType as String
	    private Line line=null;

		public DessinMessage(String type, Line line) {
			super();
			this.type = type;
			this.line = line;
		}
}
