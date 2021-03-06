package com.rickbala.api;

import java.io.IOException;

/**
 * This interface specifies some basic methods for Web Scraping
 */
public interface WebScraping {

	/**
	 * @param urlString the url which you wish to gather from the web
	 * @return the html body of the webpage as a String value
	 * @throws IOException in case the url does not exists
	 */
	String getHtmlBody(String urlString) throws IOException;

	/**
	 * @param htmlBody a String with html body
	 * @param tagName a tage to search for inside the html body
	 * @return a String containing the entire tag
	 */
	String findElementByTag(String htmlBody, String tagName);

	/**
	 * @param href html body of the href anchor attribute
	 * @return returns the extension of the linked file
	 */
	String extractHrefExtension(String href);

	/**
	 * @param urlString a String with url of the file
	 * @return the file size in bytes
	 */
	Long getFileSize(String urlString);

	/**
	 * @param urlString the html body of a given file
	 * @return the number of lines of the file based on the github page header
	 */
	Long getNumberOfLines(String urlString);

	/**
	 * @param url the url of the file you wish to download
	 * @param outputFile the path to the output file on the filesystem
	 * @throws IOException in case the file is unreachable
	 */
	void downloadFile(String url, String outputFile) throws IOException;

}
