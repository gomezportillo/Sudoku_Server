package edu.uclm.esi.common.server.domain;

import edu.uclm.esi.common.jsonMessages.JSONMessage;

public class MensajePendiente {
	private User destinatario;
	private JSONMessage message;
	
	public MensajePendiente(User destinatario, JSONMessage message) {
		super();
		this.destinatario = destinatario;
		this.message = message;
	}

	public User getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(User destinatario) {
		this.destinatario = destinatario;
	}

	public JSONMessage getMessage() {
		return message;
	}

	public void setMessage(JSONMessage message) {
		this.message = message;
	}
	
}
