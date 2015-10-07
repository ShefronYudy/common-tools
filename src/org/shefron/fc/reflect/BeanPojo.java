package org.shefron.fc.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanPojo {

	private int id = 0;
	private String name = "";
	private List<String> places = new ArrayList<String>();
	private Map<String, String> values = new HashMap<String, String>();
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<String> getPlaces() {
		return places;
	}
	public Map<String, String> getValues() {
		return values;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPlaces(List<String> places) {
		this.places = places;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}



}
