package com.rickbala.api.entity;

import java.util.List;

public class Body {

	private String html;
	private List<Element> elementList;

	public Body(String html) {
		this.html = html;
		this.elementList = this.createElementList(html);
	}

	public List<Element> createElementList(String html){
		return null;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}

}
