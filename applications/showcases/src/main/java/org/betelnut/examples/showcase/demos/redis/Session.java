package org.betelnut.examples.showcase.demos.redis;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {

	private String id;

	private Map<String, Object> attributes = Maps.newHashMap();

	public Session() {
	}

	public Session(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Session setAttrbute(String key, Object value) {
		attributes.put(key, value);
		return this;
	}

	public Session removeAttrbute(String key) {
		attributes.remove(key);
		return this;
	}
}
