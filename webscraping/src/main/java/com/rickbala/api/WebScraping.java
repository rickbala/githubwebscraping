package com.rickbala.api;

import com.rickbala.api.entity.Body;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;

public interface WebScraping {

	/**
	** @param url http address of an html file
	** @param outputFile of file to save to
	** @return a String containing the html body
	**/
	void downloadFile(String url, String outputFile) throws IOException;

	/**
	 ** @param urlString http address of an html file
	 ** @return a String containing the html body
	 **/
	Body getHtmlBody(String urlString) throws IOException;

// 	/**
//	 ** @param htmlBody an object that contains a html body
//	 ** @param element an html element to be found in this htmlBody
//	 ** @return a String containing the html body
//	 **/
// 	Element findElement(Body htmlBody, Element element);

	/**
	 ** @param htmlBody an object containing the html body
	 ** @param tagName the name of the element tag ie. <table>
	 ** @return a String with the first occurrence of the tag
	 **/
	String findElementByTag(String htmlBody, String tagName);

	/**
	** @param urlString valid url address
	** @return a SBigInteger value in bytes
	**/
	BigInteger getFileSize(String urlString) throws MalformedURLException;

	/**
	** @param url valid url address
	** @return the name of the file
	**/
	String getFileName(String url);

	/**
	** @param url valid url address
	** @return the extension of the file
	**/
	String getFileExtension(String url);

	/**
	** @param url valid url address
	** @return true if is a link for a page with more content
	**/
	boolean getIsLink(String url);

	/**
	** @param htmlBody String containing the html body or an element
	** @return a list of String all the tags on that page
	**/
	List<String> getAllOccurrencesOfTag(String htmlBody);

	/**
	** @param element String containing the element html
	** @param startPos start position for the search
	** @return a String with the attribute
	**/
	String getAttributeFromElement(String element, int startPos);

}
