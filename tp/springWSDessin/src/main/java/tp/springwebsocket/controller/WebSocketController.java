package tp.springwebsocket.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import tp.springwebsocket.model.ConnectedMessage;
import tp.springwebsocket.model.DessinMessage;
import tp.springwebsocket.model.Line;

@Controller
public class WebSocketController {

	private List<Line> allLines = new ArrayList<>();

	@MessageMapping("/dessin") //input message received from /app/dessin
	@SendTo({"/topic/allLines" } ) //output message pubish to /topic/allLines
	public List<Line> refreshAllLines(DessinMessage message) throws Exception {
		System.out.println("dessinMessage="+message);
		if(message.getType().equals("NEWLINE")){
			this.allLines.add(message.getLine());
		}else if(message.getType().equals("CLEAR")){
			this.allLines.clear();
		}else if(message.getType().equals("REFRESH")){
			//no modif , just resend allLines;
		}
		return this.allLines;
	}

	@MessageMapping("/connexion") //input message received from /app/connexion
	@SendTo("/topic/connexion") //output message pubish to /topic/connexion
	public ConnectedMessage notifyConnexion(ConnectedMessage message) throws Exception {
		System.out.println("connectedMessage="+message);
		return message;
	}

}