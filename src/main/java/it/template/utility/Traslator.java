package it.template.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;



public class Traslator {

	static Class noparams[] = {};
	
	
	
	public static Object convert(Object origine, Object destinazione, List<String> nameFields) {
		Class classD = destinazione.getClass();
		Class classO = origine.getClass();
		Field[] fields = classD.getDeclaredFields();
		for(Field field : fields) {
			if(nameFields.contains(field.getName())) {
				try {
					String name = StringUtils.capitalize(field.getName());
					Method mO = classO.getMethod("get"+name, noparams);
					Object value =  mO.invoke(origine,null);
					if(value != null) {
						Class[] params = new Class[1];
						Object valore;
						if(value instanceof Timestamp) {
							Timestamp temp = (Timestamp) value;
							valore = new Date(temp.getTime());
						}else {
							valore = value;
						}
						params[0] = valore.getClass();
						Method mD = classD.getMethod("set"+name, params);
						mD.invoke(destinazione,valore);
					}
				}catch(Exception ex) {
					;
				}
			}
		}
		return destinazione;
	}
	
	public static Object convertWithExcluding(Object origine, Object destinazione, List<String> excludingFields) {
		Class classD = destinazione.getClass();
		Class classO = origine.getClass();
		Field[] fields = classD.getDeclaredFields();
		for(Field field : fields) {
			if(!excludingFields.contains(field.getName())) {
				try {
					String name = StringUtils.capitalize(field.getName());
					Method mO = classO.getMethod("get"+name, noparams);
					Object value =  mO.invoke(origine,null);
					if(value != null) {
						Class[] params = new Class[1];
						Object valore;
						if(value instanceof Timestamp) {
							Timestamp temp = (Timestamp) value;
							valore = new Date(temp.getTime());
						}else {
							valore = value;
						}
						params[0] = valore.getClass();
						Method mD = classD.getMethod("set"+name, params);
						mD.invoke(destinazione,valore);
					}
				}catch(Exception ex) {
					;
				}
			}
		}
		return destinazione;
	}
	
	public static Object convert(Object origine, Object destinazione ) {
		Class classD = destinazione.getClass();
		Class classO = origine.getClass();
		Field[] fields = classD.getDeclaredFields();
		for(Field field : fields) {
			try {
				String name = StringUtils.capitalize(field.getName());
				Method mO = classO.getMethod("get"+name, noparams);
				if(mO != null ) {
					Object value =  mO.invoke(origine,null);
					if(value != null) {
						boolean isList = false;
						Class[] params = new Class[1];
						Object valore;
						if(value instanceof Timestamp) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							Timestamp temp = (Timestamp) value;
							valore = sdf.format(new Date(temp.getTime()));
							isList = false;
						}else if(value instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Date temp = (Date) value;
							valore = sdf.format(temp);
							isList = false;
						}else if(value instanceof List) {
							isList = true;
							valore = null;
						}else {
							valore = value;
							isList = false;
						}
						if(!isList) {
							params[0] = valore.getClass();
							Method mD = classD.getMethod("set"+name, params);
							mD.invoke(destinazione,valore);
						}
					}
				}
			}catch(Exception ex) {
				;
			}
		}
		return destinazione;
	}
	
	public static Object convert(Object origine, Object destinazione, Map<String, String> mappaDO ) {
		Class classD = destinazione.getClass();
		Class classO = origine.getClass();
		Field[] fields = classD.getDeclaredFields();
		for(Field field : fields) {
			try {
				if(mappaDO.containsKey(field.getName())) {
					String nameO = (String) mappaDO.get(field.getName());
					String nameForMethodO = StringUtils.capitalize(nameO);
					Method mO = classO.getMethod("get"+nameForMethodO, noparams);
					Object value =  mO.invoke(origine,null);
					if(value != null) {
						Object valore;
						if(value instanceof Timestamp) {
							Timestamp temp = (Timestamp) value;
							valore = new Date(temp.getTime());
						}else {
							valore = value;
						}
						String nameD = field.getName();
						String nameForMethodD = StringUtils.capitalize(nameD);
						Class[] params = new Class[1];
						params[0] = valore.getClass();
						Method mD = classD.getMethod("set"+nameForMethodD, params);
						mD.invoke(destinazione,valore);
					}
				}
			}catch(Exception ex) {
				;
			}
		}
		return destinazione;
	}
}
