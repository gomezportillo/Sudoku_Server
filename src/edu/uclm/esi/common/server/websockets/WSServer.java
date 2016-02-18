package edu.uclm.esi.common.server.websockets;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.inject.Singleton;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@ServerEndpoint(value = "/notificador/notificador", configurator = GetHttpSessionConfigurator.class)
@Singleton
public class WSServer {
	
	private static Hashtable<String, Client> clientesBySessionId=new Hashtable<String, Client>();
	private static Hashtable<String, Client> clientesByEmail=new Hashtable<String, Client>();

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		String sessionId=session.getId();
		System.out.println("New request received. Id: " + session.getId());
		Client client = clientesBySessionId.get(sessionId);
		if (client==null) {
			client=new Client(session);
			clientesBySessionId.put(sessionId, client);
		}
	}

	@OnClose
	public void onClose(Session session) {
		String sessionId=session.getId();
		System.out.println("Connection closed. Id: " + sessionId);
		Client client = clientesBySessionId.get(sessionId);
		if (client!=null) {
			clientesBySessionId.remove(sessionId);
			clientesByEmail.remove(client.getUser().getEmail());
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		String sessionId=session.getId();
		try {
			JSONObject jso=new JSONObject(message);
			if (jso.getString("type").contains("LoginMessage")) {
				User user=Manager.get().findUserByEmail(jso.getString("email"));
				Client client=clientesBySessionId.get(sessionId);
				client.setUser(user);
				clientesByEmail.put(user.getEmail(), client);
				return;
			} 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized void broadcast(final String message) {
		final Enumeration<Client> e=clientesBySessionId.elements();
		while (e.hasMoreElements()) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Client client=e.nextElement();
					client.getSession().getAsyncRemote().sendText(message);
				}
			}).start();
		}
	}

	public static Client findClientByEmail(String email) {
		return clientesByEmail.get(email);
	}
}
