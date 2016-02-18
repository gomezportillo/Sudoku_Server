package edu.uclm.esi.common.jsonMessages;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class TresEnRayaWaitingMessage extends JSONMessage {
	@JSONable
	private String text;
	
	public TresEnRayaWaitingMessage(String text) {
		super(false);
		this.text=text;
	}

	public String getText() {
		return this.text;
	}

}
