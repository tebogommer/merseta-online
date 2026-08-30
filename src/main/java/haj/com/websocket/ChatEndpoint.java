package haj.com.websocket;



import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import haj.com.service.ActiveUsersService;

//@ServerEndpoint(value = "/chat")
@ServerEndpoint(value = "/chat" , configurator = HttpSessionConfigurator.class)
public class ChatEndpoint {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	 private HttpSession httpSession;

/*	@OnOpen
	public void open(final Session session, EndpointConfig config) {
		System.out.println("ChatEndpoint: open !!!");
	}
*/

	    @OnOpen
	    public void opened( Session session, EndpointConfig config) {
	        this.httpSession = (HttpSession) config.getUserProperties().get("httpSession");
	        ActiveUsersService.instance().addWsUser( httpSession.getId(),session);
	        logger.info("ChatEndpoint - httpSession : " + httpSession.getId());
	    }

	@OnMessage
	public void onMessage(final Session session, final String message) {
		if (!StringUtils.isEmpty(message)) {

			ObjectMapper mapper = new ObjectMapper();
			ChatMessage chatMessage = null;
			try {
				chatMessage = mapper.readValue(message, ChatMessage.class);
				if (chatMessage.getEmployee()) {
					logger.info("Employee: "+httpSession.getId());
					ActiveUsersService.instance().sendMessageFromEmployee(httpSession.getId(),chatMessage);
				}
				else {
					logger.info("Not Employee: "+httpSession.getId());
					ActiveUsersService.instance().sendMessageFromExternalParty(httpSession.getId(),chatMessage);
				}
			} catch (Exception e) {
				logger.fatal(e);
			}


		}
		logger.info("ChatEndpoint - message: " + message);
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
	//	room.leave(session);
	//	room.sendMessage((String) session.getUserProperties().get("name") + " - Left the room");
	}

	@OnError
	public void onError(Session session, Throwable ex) {
	   //	logger.info("Error: " + ex.getMessage());
	}
}