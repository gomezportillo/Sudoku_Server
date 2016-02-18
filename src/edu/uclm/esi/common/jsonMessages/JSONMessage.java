package edu.uclm.esi.common.jsonMessages;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONMessage {
	public static final String USER_ANDROID = "USER_ANDROID";
	public static final String USER_GWT = "USER_GWT";
	
	private boolean isCommand;
	
	public JSONMessage(boolean isCommand) {
		this.isCommand=isCommand;
	}
	
	public final boolean isCommand() {
		return isCommand;
	}

	public final JSONObject toJSONObject() {
		Vector<Field> ff=getJSONableFields();
		JSONObject result=new JSONObject();
		String fn;
		Object fv;
		for (Field f : ff) {
			fn=f.getName();
			try {
				fv=f.get(this);
				result.put(fn, fv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			result.put("type", this.getClass().getSimpleName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String toString() {
		return this.toJSONObject().toString();
	}
	
	private Vector<Field> getJSONableFields() {
		Vector<Field> result=new Vector<Field>();
		Class<?> c=this.getClass();
		addJSONableFields(result, c);
		return result;
	}

	private void addJSONableFields(Vector<Field> result, Class<?> c) {
		for (Field f : c.getDeclaredFields()) {
			f.setAccessible(true);
			if (isJSONable(f)) {
				result.add(f);
			}
		}		
		if (c.getSuperclass()!=null) {
			addJSONableFields(result, c.getSuperclass());
		}
	}
	
	private boolean isJSONable(Field f) {
		Annotation[] aann = f.getAnnotations();
		for (int i=0; i<aann.length; i++) {
			Annotation an=aann[i];
			Class<?> fa = JSONable.class;
			if (an.annotationType()==fa)
				return true;
		}
		return false;
	}

	public final String getType() {
		return this.getClass().getSimpleName();
	}
}
