package haj.com.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import haj.com.bean.ActiveUsersBean;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.websocket.ChatMessage;


public class ActiveUsersService extends AbstractService {

	private  static ActiveUsersService activeUsersService = null;
	private Map<String,ActiveUsersBean>  activeUsers;
	private ChatMessageService chatMessageService = new ChatMessageService();
	private String JS_TO_STAFF = "alert('hello')";


	public static synchronized ActiveUsersService instance() {
		if (activeUsersService == null) {
			activeUsersService = new ActiveUsersService();
			activeUsersService.activeUsers = new HashMap<String,ActiveUsersBean>();
		}
		return activeUsersService;
	}

	public synchronized void addSession(String sessionId) {
		if (sessionId!=null) {
				this.activeUsers.put(sessionId, new ActiveUsersBean());
		}
	}

	public synchronized void addUser(String sessionId, Users user) {
		if (user!=null) {
			if (this.activeUsers.containsKey(sessionId)) {
				this.activeUsers.get(sessionId).setUser(user);
			}
			else {
				this.activeUsers.put(sessionId, new ActiveUsersBean(user));
			}

		}
	}

	public synchronized void removeUser(String sessionId) {
	 if (sessionId!=null) {
		if (this.activeUsers.containsKey(sessionId)) {
			this.activeUsers.get(sessionId).setUser(new Users(-1l));;
		}
	 }
	}

	public synchronized boolean isSessionActive(String sessionId) {
		 if (sessionId!=null) {
			if (this.activeUsers.containsKey(sessionId)) {
				return true;
			}
		 }
		   return false;
	}


	public synchronized boolean isUserActive(Users user) {
		 boolean active = false;
		 if (user!=null) {
			 for (ActiveUsersBean v : activeUsers.values()) {
				if (user.getId().equals(v.getUser().getId())) {
					active = true;
				}
			 }
		 }
		 return active;
	}

	public synchronized int numberOfActiveUsers() {
		return this.activeUsers.size();
	}

	public synchronized void addWsUser(String sessionId,Session webSocketSession) {
		if (sessionId!=null && webSocketSession !=null) {
			if (this.activeUsers.containsKey(sessionId)) {
				this.activeUsers.get(sessionId).setWebSocketSession(webSocketSession);
			}
			else {
				this.activeUsers.put(sessionId, new ActiveUsersBean(new Users(-1l),webSocketSession));
			}
		}
	}

	public synchronized Session getWebSocketSession(Users user) {
		 if (user!=null) {
		    for (ActiveUsersBean v : activeUsers.values()) {
				if (v.getUser() !=null && user.getId().equals(v.getUser().getId())) {
					if (v.getWebSocketSession().isOpen()) {
				        return v.getWebSocketSession();
					}
			}
		   }
		 }

		return null;
	}


	public synchronized Session getWebSocketSession(Long userId) {
		 if (userId!=null) {
		    for (ActiveUsersBean v : activeUsers.values()) {
				if (v.getUser() !=null && userId.equals(v.getUser().getId())) {
					if (v.getWebSocketSession().isOpen()) {
				        return v.getWebSocketSession();
					}
			}
		   }
		 }

		return null;
	}


	public synchronized void sendMessageFromEmployee(String id, ChatMessage chatMessage) {
		try {
			Session session = getWebSocketSession(chatMessage.getUserId());
			if (session!=null) {
				if (session.isOpen()) {
					session.getBasicRemote().sendText("refresh");
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	public synchronized void sendMessageFromExternalParty(String httpId, ChatMessage chatMessage) {
	try {
			List<Users> employees =  chatMessageService.findChatEmployeesForDepartment(chatMessage.getDepartmentId());
			for (Users employee : employees) {
				Session session = getWebSocketSession(employee);
				if (session!=null) {
					if (session.isOpen()) {
						try {
							long uid = 0;
							if (activeUsers.containsKey(httpId)) {
							  if (activeUsers.get(httpId).getUser()!=null)	{
								 uid = activeUsers.get(httpId).getUser().getId();
						      }
							}
							session.getBasicRemote().sendText(""+uid);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}


}
