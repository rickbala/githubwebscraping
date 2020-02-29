package com.rickbala.api.entity;

import java.util.List;

public class Element {

	private String html;
	private String tag;
	private String id;
	private List<Attribute> attributeList;

	public Element(String html) {
		this.html = html;
		this.tag = this.extractHtmlTag(html);
		this.id = this.extractElementId(html);
		this.attributeList = this.createListOfAttributes(html);
	}

	public String extractHtmlTag(String html){
		return null;
	}

	public String extractElementId(String html){
		return null;
	}

	public List<Attribute> createListOfAttributes(String html){
		return null;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<Attribute> attributeList) {
		this.attributeList = attributeList;
	}

}
