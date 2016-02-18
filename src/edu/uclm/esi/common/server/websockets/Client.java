package edu.uclm.esi.common.server.websockets;

import javax.websocket.Session;

import edu.uclm.esi.common.server.domain.User;

public class Client {
	private Session session;
	private User user;
	
	public Client(Session session) {
		super();
		this.session = session;
		this.user = null;
	}

	public void setUser(User user) {
		this.user=user;
	}

	public User getUser() {
		return user;
	}
	
	public Session getSession() {
		return session;
	}

	public void send(final String msg) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				session.getAsyncRemote().sendText(msg);
			}
		}).start();
	}
}
