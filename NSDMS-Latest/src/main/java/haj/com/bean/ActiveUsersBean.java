package haj.com.bean;

import java.io.Serializable;
import javax.websocket.Session;

import haj.com.entity.Users;

public class ActiveUsersBean implements Serializable {

	private Users user;
    private Session webSocketSession;


	public ActiveUsersBean() {
		super();
	}


	public ActiveUsersBean(Users user) {
		super();
		this.user = user;
	}


	public ActiveUsersBean(Users user, Session webSocketSession) {
		super();
		this.user = user;
		this.webSocketSession = webSocketSession;
	}


	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Session getWebSocketSession() {
		return webSocketSession;
	}
	public void setWebSocketSession(Session webSocketSession) {
		this.webSocketSession = webSocketSession;
	}


}
