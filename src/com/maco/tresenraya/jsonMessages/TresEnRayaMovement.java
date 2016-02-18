package com.maco.tresenraya.jsonMessages;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class TresEnRayaMovement extends JSONMessage {
	@JSONable
	private int row, col;
	
	public TresEnRayaMovement(int row, int col) {
		super(true);
		this.row=row;
		this.col=col;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
