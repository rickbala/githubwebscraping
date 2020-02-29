package com.rickbala.api.entity;

public class Attribute {

	private String html;
	private String key;
	private String value;

	public Attribute(String html) {
		this.html = html;
		this.key = this.extractAttributeKey(html);
		this.value = this.extractAttributeValue(html);
	}

	public String extractAttributeKey(String html){
		return null;
	}

	public String extractAttributeValue(String html){
		return  null;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
